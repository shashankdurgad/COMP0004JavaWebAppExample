package uk.ac.ucl.model;

import java.util.ArrayList;

// Stores patient data loaded from a CSV file and provides query methods
// used by the servlets.
public class Model
{
  // The DataFrame holding all patient data once the file has been loaded.
  private DataFrame dataFrame;

  // Loads the CSV file at fileName into the internal DataFrame.
  // Called once by ModelFactory during initialisation.
  public void readFile(String fileName)
  {
    DataLoader loader = new DataLoader(fileName);
    dataFrame = loader.load();
  }

  // Returns a list of formatted patient names (PREFIX FIRST LAST) for all rows.
  public ArrayList<String> getPatientNames()
  {
    ArrayList<String> names = new ArrayList<>();
    int rowCount = dataFrame.getRowCount();

    for (int row = 0; row < rowCount; row = row + 1)
    {
      names.add(buildName(row));
    }

    return names;
  }

  // Searches all columns of every patient row for the given search string.
  // Multiple keywords can be entered separated by spaces (e.g. "Alice Massachusetts").
  // A row matches only if ALL keywords are found somewhere in that row's data
  // (case-insensitive). Returns a list of matching formatted patient names.
  public ArrayList<String> searchFor(String searchString)
  {
    ArrayList<String> results = new ArrayList<>();
    String[] keywords = searchString.trim().toLowerCase().split("\\s+");
    ArrayList<String> columns = dataFrame.getColumnNames();
    int rowCount = dataFrame.getRowCount();

    for (int row = 0; row < rowCount; row = row + 1)
    {
      if (rowMatchesAllKeywords(row, keywords, columns))
      {
        results.add(buildName(row));
      }
    }

    return results;
  }

  // Returns true if every keyword in the array is found in at least one column
  // of the given row (case-insensitive).
  private boolean rowMatchesAllKeywords(int row, String[] keywords, ArrayList<String> columns)
  {
    for (int k = 0; k < keywords.length; k = k + 1)
    {
      String keyword = keywords[k];
      boolean keywordFound = false;

      for (int c = 0; c < columns.size(); c = c + 1)
      {
        String cellValue = dataFrame.getValue(columns.get(c), row).toLowerCase();
        if (cellValue.contains(keyword))
        {
          keywordFound = true;
          break;
        }
      }

      if (!keywordFound)
      {
        return false;
      }
    }
    return true;
  }

  // Returns all field values for a single patient row as "COLUMN: value" strings.
  // Used by the patient detail page.
  public ArrayList<String> getPatientData(int row)
  {
    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> columns = dataFrame.getColumnNames();
    for (int i = 0; i < columns.size(); i = i + 1)
    {
      String col = columns.get(i);
      String val = dataFrame.getValue(col, row);
      data.add(col + ": " + val);
    }
    return data;
  }

  // Returns formatted names of patients matching the given gender ("M" or "F").
  public ArrayList<String> getPatientsByGender(String gender)
  {
    ArrayList<String> results = new ArrayList<>();
    int rowCount = dataFrame.getRowCount();
    for (int row = 0; row < rowCount; row = row + 1)
    {
      if (dataFrame.getValue("GENDER", row).equals(gender))
      {
        results.add(buildName(row));
      }
    }
    return results;
  }

  // Returns summary statistics as formatted strings:
  // total patients, male/female counts, deceased and living counts.
  public ArrayList<String> getStatistics()
  {
    int total = dataFrame.getRowCount();
    int male = 0;
    int female = 0;
    int deceased = 0;
    for (int row = 0; row < total; row = row + 1)
    {
      String gender = dataFrame.getValue("GENDER", row);
      String deathDate = dataFrame.getValue("DEATHDATE", row).trim();
      if (gender.equals("M")) { male = male + 1; }
      if (gender.equals("F")) { female = female + 1; }
      if (!deathDate.isEmpty()) { deceased = deceased + 1; }
    }
    ArrayList<String> stats = new ArrayList<>();
    stats.add("Total patients: " + total);
    stats.add("Male: " + male);
    stats.add("Female: " + female);
    stats.add("Deceased: " + deceased);
    stats.add("Living: " + (total - deceased));
    return stats;
  }

  // Formats a display name for the given row.
  // Returns "PREFIX FIRST LAST" if prefix is present, or "FIRST LAST" if blank.
  private String buildName(int row)
  {
    String prefix = dataFrame.getValue("PREFIX", row).trim();
    String first  = dataFrame.getValue("FIRST",  row).trim();
    String last   = dataFrame.getValue("LAST",   row).trim();

    if (prefix.isEmpty())
    {
      return first + " " + last;
    }
    else
    {
      return prefix + " " + first + " " + last;
    }
  }
}

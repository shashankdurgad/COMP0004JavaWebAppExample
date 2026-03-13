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

  // Returns a list of patient names where FIRST or LAST contains the keyword
  // (case-insensitive).
  public ArrayList<String> searchFor(String keyword)
  {
    ArrayList<String> results = new ArrayList<>();
    String lowerKeyword = keyword.toLowerCase();
    int rowCount = dataFrame.getRowCount();

    for (int row = 0; row < rowCount; row = row + 1)
    {
      String firstName = dataFrame.getValue("FIRST", row).toLowerCase();
      String lastName  = dataFrame.getValue("LAST",  row).toLowerCase();

      if (firstName.contains(lowerKeyword) || lastName.contains(lowerKeyword))
      {
        results.add(buildName(row));
      }
    }

    return results;
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

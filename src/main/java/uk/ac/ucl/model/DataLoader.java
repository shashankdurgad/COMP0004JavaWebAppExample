package uk.ac.ucl.model;

import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

// Reads a CSV file and loads it into a DataFrame.
// The first row of the CSV is treated as column headers.
// Subsequent rows are treated as data.
public class DataLoader
{
  private final String filePath;

  // Creates a DataLoader for the given file path.
  // The file is not opened until load() is called.
  public DataLoader(String filePath)
  {
    this.filePath = filePath;
  }

  // Reads the CSV file and returns a DataFrame containing all the data.
  // If the file cannot be read, prints an error and returns an empty DataFrame.
  public DataFrame load()
  {
    DataFrame dataFrame = new DataFrame();
    boolean headerRead = false;

    try (Reader reader = new FileReader(filePath);
         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
    {
      for (CSVRecord csvRecord : csvParser)
      {
        if (!headerRead)
        {
          // First row: create one Column per header name.
          for (int i = 0; i < csvRecord.size(); i++)
          {
            dataFrame.addColumn(new Column(csvRecord.get(i)));
          }
          headerRead = true;
        }
        else
        {
          // Data row: append each cell value to the matching column.
          for (int i = 0; i < csvRecord.size(); i++)
          {
            String columnName = dataFrame.getColumnNames().get(i);
            dataFrame.addValue(columnName, csvRecord.get(i));
          }
        }
      }
    }
    catch (IOException e)
    {
      System.err.println("Error reading file: " + filePath);
      e.printStackTrace();
    }

    return dataFrame;
  }
}

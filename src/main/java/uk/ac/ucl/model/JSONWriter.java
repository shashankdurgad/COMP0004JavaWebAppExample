package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Writes a DataFrame to a file in JSON format.
// The output is a JSON array where each element is an object
// representing one patient row (column name → value).
public class JSONWriter
{
  private final DataFrame dataFrame;
  private final String filePath;

  public JSONWriter(DataFrame dataFrame, String filePath)
  {
    this.dataFrame = dataFrame;
    this.filePath  = filePath;
  }

  // Writes the DataFrame to the file as a JSON array of objects.
  // Each array element is one patient row; keys are column names, values are cell values.
  public void write() throws IOException
  {
    ArrayList<String> columns = dataFrame.getColumnNames();
    int rowCount = dataFrame.getRowCount();

    StringBuilder sb = new StringBuilder();
    sb.append("[\n");

    for (int row = 0; row < rowCount; row = row + 1)
    {
      sb.append("  {\n");

      for (int col = 0; col < columns.size(); col = col + 1)
      {
        String key   = escape(columns.get(col));
        String value = escape(dataFrame.getValue(columns.get(col), row));
        sb.append("    \"").append(key).append("\": \"").append(value).append("\"");
        if (col < columns.size() - 1)
        {
          sb.append(",");
        }
        sb.append("\n");
      }

      sb.append("  }");
      if (row < rowCount - 1)
      {
        sb.append(",");
      }
      sb.append("\n");
    }

    sb.append("]\n");

    try (FileWriter writer = new FileWriter(filePath))
    {
      writer.write(sb.toString());
    }
  }

  // Escapes characters that would break a JSON quoted string value.
  // Must escape backslash first, before any other replacement adds new backslashes.
  private String escape(String s)
  {
    return s.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
  }
}

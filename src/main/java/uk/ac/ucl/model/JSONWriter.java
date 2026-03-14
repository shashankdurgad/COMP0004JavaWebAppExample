package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// Writes a DataFrame to a file in JSON format using Jackson.
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
    ObjectMapper mapper = new ObjectMapper();
    ArrayNode array = mapper.createArrayNode();

    ArrayList<String> columns = dataFrame.getColumnNames();
    int rowCount = dataFrame.getRowCount();

    for (int row = 0; row < rowCount; row = row + 1)
    {
      ObjectNode node = mapper.createObjectNode();
      for (String column : columns)
      {
        node.put(column, dataFrame.getValue(column, row));
      }
      array.add(node);
    }

    mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), array);
  }
}

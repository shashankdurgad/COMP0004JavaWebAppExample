package uk.ac.ucl.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

// Holds a collection of Column objects, accessible by name.
// All columns must have the same number of rows when fully loaded.
public class DataFrame
{
  private final LinkedHashMap<String, Column> columns;

  public DataFrame()
  {
    columns = new LinkedHashMap<>();
  }

  // Returns the named column, or throws IllegalArgumentException if not found.
  private Column getColumn(String name)
  {
    Column column = columns.get(name);
    if (column == null)
    {
      throw new IllegalArgumentException("No column named: " + name);
    }
    return column;
  }

  // Adds a column to the frame. Replaces any existing column with the same name.
  public void addColumn(Column column)
  {
    columns.put(column.getName(), column);
  }

  // Returns the column names in the order they were added.
  public ArrayList<String> getColumnNames()
  {
    return new ArrayList<>(columns.keySet());
  }

  // Returns the number of rows. Returns 0 if there are no columns.
  // Assumes all columns have the same number of rows.
  public int getRowCount()
  {
    if (columns.isEmpty())
    {
      return 0;
    }
    return columns.values().iterator().next().getSize();
  }

  // Returns the value in the named column at the given row index (0-based).
  public String getValue(String columnName, int row)
  {
    return getColumn(columnName).getRowValue(row);
  }

  // Overwrites the value in the named column at the given row index (0-based).
  public void putValue(String columnName, int row, String value)
  {
    getColumn(columnName).setRowValue(row, value);
  }

  // Appends a value to the end of the named column.
  public void addValue(String columnName, String value)
  {
    getColumn(columnName).addRowValue(value);
  }

  // Removes the row at the given index from every column.
  public void removeRow(int row)
  {
    for (String name : columns.keySet())
    {
      columns.get(name).removeRowValue(row);
    }
  }
}

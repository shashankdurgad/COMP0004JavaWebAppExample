package uk.ac.ucl.model;

import java.util.ArrayList;

// Represents a single column from the CSV data.
// A column has a name (the header, e.g. "FIRST") and a list of row values (e.g. "Alice", "Bob", ...).
// Row indexing is 0-based: the first data row is at index 0.
public class Column
{
  private final String name;          // The column header name
  private final ArrayList<String> rows; // The data values, one per row

  // Creates a new column with the given name and an empty list of rows.
  public Column(String name)
  {
    this.name = name;
    this.rows = new ArrayList<>();
  }

  // Returns the name of this column (e.g. "FIRST", "LAST").
  public String getName()
  {
    return name;
  }

  // Returns the number of data rows in this column.
  public int getSize()
  {
    return rows.size();
  }

  // Returns the value at the given row index (0-based).
  // Throws IndexOutOfBoundsException if rowNumber is out of range.
  public String getRowValue(int rowNumber)
  {
    return rows.get(rowNumber);
  }

  // Overwrites the value at the given row index (0-based).
  // Throws IndexOutOfBoundsException if rowNumber is out of range.
  public void setRowValue(int rowNumber, String value)
  {
    rows.set(rowNumber, value);
  }

  // Appends a new value to the end of this column's row list.
  public void addRowValue(String value)
  {
    rows.add(value);
  }

  // Removes the value at the given row index, shifting later rows up.
  public void removeRowValue(int rowNumber)
  {
    rows.remove(rowNumber);
  }
}

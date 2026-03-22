package uk.ac.ucl.servlets;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

// Handles editing an existing patient row.
// GET  /editpatient?row=N — shows a pre-filled form for the given row
// POST /editpatient       — saves the updated row and redirects to the patient detail page
@WebServlet(urlPatterns = {"/editpatient"})
public class EditPatientServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      int row = Integer.parseInt(request.getParameter("row"));
      Model model = ModelFactory.getModel();

      ArrayList<String> columns = model.getColumnNames();

      // getPatientData returns "COL: value" strings — extract just the values.
      ArrayList<String> patientData = model.getPatientData(row);
      ArrayList<String> values = new ArrayList<>();
      for (String entry : patientData)
      {
        int colonIndex = entry.indexOf(":");
        values.add(entry.substring(colonIndex + 1).trim());
      }

      request.setAttribute("columns",    columns);
      request.setAttribute("values",     values);
      request.setAttribute("row",        row);
      request.setAttribute("formAction", "editpatient");
      request.setAttribute("pageTitle",  "Edit Patient");

      RequestDispatcher dispatcher = request.getRequestDispatcher("/patientForm.jsp");
      dispatcher.forward(request, response);
    }
    catch (ServletException | IOException | NumberFormatException e)
    {
      request.setAttribute("errorMessage", e.getMessage());
      RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
      dispatcher.forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      int row = Integer.parseInt(request.getParameter("row"));
      Model model = ModelFactory.getModel();
      ArrayList<String> columns = model.getColumnNames();

      // Build updated values in column order from submitted form fields.
      ArrayList<String> values = new ArrayList<>();
      for (String col : columns)
      {
        String val = request.getParameter(col);
        values.add(val != null ? val : "");
      }

      model.updatePatient(row, values);
      response.sendRedirect(request.getContextPath() + "/patient?row=" + row);
    }
    catch (IOException | NumberFormatException e)
    {
      request.setAttribute("errorMessage", e.getMessage());
      RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
      dispatcher.forward(request, response);
    }
  }
}

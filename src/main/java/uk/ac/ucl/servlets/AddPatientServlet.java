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

// Handles adding a new patient row.
// GET  /addpatient        — shows a blank form
// POST /addpatient        — saves the new row and redirects to the patient list
@WebServlet(urlPatterns = {"/addpatient"})
public class AddPatientServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      Model model = ModelFactory.getModel();
      request.setAttribute("columns",    model.getColumnNames());
      request.setAttribute("values",     new ArrayList<String>());
      request.setAttribute("row",        -1);
      request.setAttribute("formAction", "addpatient");
      request.setAttribute("pageTitle",  "Add New Patient");

      RequestDispatcher dispatcher = request.getRequestDispatcher("/patientForm.jsp");
      dispatcher.forward(request, response);
    }
    catch (ServletException | IOException e)
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
      Model model = ModelFactory.getModel();
      ArrayList<String> columns = model.getColumnNames();

      // Build the list of values in column order from the submitted form fields.
      ArrayList<String> values = new ArrayList<>();
      for (String col : columns)
      {
        String val = request.getParameter(col);
        values.add(val != null ? val : "");
      }

      model.addPatient(values);
      response.sendRedirect(request.getContextPath() + "/patientList");
    }
    catch (IOException e)
    {
      request.setAttribute("errorMessage", e.getMessage());
      RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
      dispatcher.forward(request, response);
    }
  }
}

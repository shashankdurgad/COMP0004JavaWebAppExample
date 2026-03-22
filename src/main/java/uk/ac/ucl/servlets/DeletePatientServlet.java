package uk.ac.ucl.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

import java.io.IOException;

// Handles deleting a patient row.
// POST /deletepatient (with hidden field "row") — deletes the row and redirects to the patient list.
// Only POST is supported — deletion should never happen via a GET request (e.g. a clicked link).
@WebServlet(urlPatterns = {"/deletepatient"})
public class DeletePatientServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      int row = Integer.parseInt(request.getParameter("row"));
      Model model = ModelFactory.getModel();
      model.deletePatient(row);
      response.sendRedirect(request.getContextPath() + "/patientList");
    }
    catch (Exception e)
    {
      request.setAttribute("errorMessage", e.getMessage());
      RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
      dispatcher.forward(request, response);
    }
  }
}

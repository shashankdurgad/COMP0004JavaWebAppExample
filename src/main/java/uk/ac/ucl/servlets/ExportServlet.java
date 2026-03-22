package uk.ac.ucl.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

// Handles exporting all patient data to a JSON file.
// GET /exportjson — writes data/patients.json and shows a confirmation page.
@WebServlet(urlPatterns = {"/exportjson"})
public class ExportServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      Model model = ModelFactory.getModel();
      model.saveToJSON();
      request.setAttribute("message", "Data exported to data/patients.json");
      request.getRequestDispatcher("/exportSuccess.jsp").forward(request, response);
    }
    catch (ServletException | IOException e)
    {
      request.setAttribute("errorMessage", e.getMessage());
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}

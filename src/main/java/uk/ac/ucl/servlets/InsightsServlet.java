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

// Handles requests for the insights page, which shows various data analyses:
// oldest patients, patient counts by city, and marital status breakdown.
// URL: /insights
@WebServlet(urlPatterns = {"/insights"})
public class InsightsServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      Model model = ModelFactory.getModel();

      request.setAttribute("oldestLiving",        model.getOldestLivingPatient());
      request.setAttribute("oldestOverall",        model.getOldestPatientOverall());
      request.setAttribute("countByCity",          model.getPatientCountByCity());
      request.setAttribute("countByMaritalStatus", model.getPatientCountByMaritalStatus());

      RequestDispatcher dispatcher = request.getRequestDispatcher("/insights.jsp");
      dispatcher.forward(request, response);
    }
    catch (Exception e)
    {
      request.setAttribute("errorMessage", e.getMessage());
      RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
      dispatcher.forward(request, response);
    }
  }
}

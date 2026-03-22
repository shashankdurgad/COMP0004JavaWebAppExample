package uk.ac.ucl.servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

// Handles the combined analytics page (statistics + insights).
// URL: /analytics
@WebServlet(urlPatterns = {"/analytics"})
public class AnalyticsServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      Model model = ModelFactory.getModel();

      request.setAttribute("stats",               model.getStatistics());
      request.setAttribute("oldestLiving",         model.getOldestLivingPatient());
      request.setAttribute("oldestOverall",         model.getOldestPatientOverall());
      request.setAttribute("countByCity",           model.getPatientCountByCity());
      request.setAttribute("countByMaritalStatus",  model.getPatientCountByMaritalStatus());
      request.setAttribute("malePatients",          model.getPatientsByGender("M"));
      request.setAttribute("femalePatients",        model.getPatientsByGender("F"));
      request.setAttribute("ageDistribution",       model.getAgeDistribution());

      RequestDispatcher dispatcher = request.getRequestDispatcher("/analytics.jsp");
      dispatcher.forward(request, response);
    }
    catch (ServletException | IOException e)
    {
      request.setAttribute("errorMessage", e.getMessage());
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}

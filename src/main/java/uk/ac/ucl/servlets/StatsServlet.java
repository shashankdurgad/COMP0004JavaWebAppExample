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
import java.util.ArrayList;

// Handles requests for the statistics summary page.
// URL: /stats
@WebServlet(urlPatterns = {"/stats"})
public class StatsServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      Model model = ModelFactory.getModel();
      ArrayList<String> stats = model.getStatistics();

      request.setAttribute("stats", stats);

      RequestDispatcher dispatcher = request.getRequestDispatcher("/stats.jsp");
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

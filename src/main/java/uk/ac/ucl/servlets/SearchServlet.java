package uk.ac.ucl.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;

// Handles patient search requests.
// GET/POST /runsearch — searches for patients matching the given keywords.
@WebServlet("/runsearch")
public class SearchServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    doPost(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String searchString = request.getParameter("searchstring");
    try
    {
      Model model = ModelFactory.getModel();
      if (searchString == null || searchString.trim().isEmpty())
      {
        request.setAttribute("errorMessage", "Please enter a search term.");
      }
      else
      {
        request.setAttribute("result", model.searchFor(searchString));
      }
      request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
    }
    catch (IOException e)
    {
      request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
      request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
  }
}

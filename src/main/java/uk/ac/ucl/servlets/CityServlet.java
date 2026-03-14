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

// Handles requests to view all patients living in a given city.
// URL: /city?name=CityName
@WebServlet(urlPatterns = {"/city"})
public class CityServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      String cityName = request.getParameter("name");
      if (cityName == null || cityName.trim().isEmpty())
      {
        request.setAttribute("errorMessage", "No city name provided.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
        return;
      }

      Model model = ModelFactory.getModel();
      ArrayList<String> patients = model.getPatientsInCity(cityName);

      request.setAttribute("cityName",    cityName);
      request.setAttribute("patientNames", patients);

      RequestDispatcher dispatcher = request.getRequestDispatcher("/cityPatients.jsp");
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

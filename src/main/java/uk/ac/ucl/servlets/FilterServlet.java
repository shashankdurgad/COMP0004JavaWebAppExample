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

// Handles requests to filter patients by gender.
// URL: /filter?gender=M or /filter?gender=F
@WebServlet(urlPatterns = {"/filter"})
public class FilterServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      String gender = request.getParameter("gender");
      if (gender == null || (!gender.equals("M") && !gender.equals("F")))
      {
        request.setAttribute("errorMessage", "Invalid gender filter. Use M or F.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
        return;
      }

      Model model = ModelFactory.getModel();
      ArrayList<String> patientNames = model.getPatientsByGender(gender);

      request.setAttribute("patientNames", patientNames);
      request.setAttribute("gender", gender);

      RequestDispatcher dispatcher = request.getRequestDispatcher("/filterResult.jsp");
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

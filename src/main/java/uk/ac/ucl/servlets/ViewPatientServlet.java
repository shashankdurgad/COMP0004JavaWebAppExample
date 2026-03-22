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

// Handles requests for a single patient's detail page.
// URL: /patient?row=N where N is the 0-based row index in the DataFrame.
@WebServlet(urlPatterns = {"/patient"})
public class ViewPatientServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      String rowParam = request.getParameter("row");
      if (rowParam == null || rowParam.isEmpty())
      {
        request.setAttribute("errorMessage", "No patient row specified.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
        dispatcher.forward(request, response);
        return;
      }

      int row = Integer.parseInt(rowParam);
      Model model = ModelFactory.getModel();
      ArrayList<String> patientData = model.getPatientData(row);
      ArrayList<String> allNames = model.getPatientNames();
      String patientName = allNames.get(row);

      request.setAttribute("patientData", patientData);
      request.setAttribute("patientName", patientName);
      request.setAttribute("row", row);

      RequestDispatcher dispatcher = request.getRequestDispatcher("/patientDetail.jsp");
      dispatcher.forward(request, response);
    }
    catch (NumberFormatException e)
    {
      request.setAttribute("errorMessage", "Invalid patient row: " + request.getParameter("row"));
      RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
      dispatcher.forward(request, response);
    }
    catch (ServletException | IOException e)
    {
      request.setAttribute("errorMessage", e.getMessage());
      RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
      dispatcher.forward(request, response);
    }
  }
}

<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Insights - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Data Insights</h2>

  <h3>Oldest Patients</h3>
  <table class="stats-table">
    <tr><th>Category</th><th>Patient</th></tr>
    <tr>
      <td>Oldest living</td>
      <td><%= request.getAttribute("oldestLiving") %></td>
    </tr>
    <tr>
      <td>Oldest overall</td>
      <td><%= request.getAttribute("oldestOverall") %></td>
    </tr>
  </table>

  <h3>Patients by City</h3>
  <p>Click a city to see its patients.</p>
  <table class="stats-table">
    <tr><th>City</th><th>Count</th></tr>
    <%
      ArrayList<String> countByCity = (ArrayList<String>) request.getAttribute("countByCity");
      if (countByCity != null)
      {
        for (String entry : countByCity)
        {
          int colonIndex = entry.indexOf(":");
          String city  = entry.substring(0, colonIndex).trim();
          String count = entry.substring(colonIndex + 1).trim();
    %>
          <tr>
            <td><a href="city?name=<%= city %>"><%= city %></a></td>
            <td><%= count %></td>
          </tr>
    <%
        }
      }
    %>
  </table>

  <h3>Patients by Marital Status</h3>
  <table class="stats-table">
    <tr><th>Status</th><th>Count</th></tr>
    <%
      ArrayList<String> countByMarital = (ArrayList<String>) request.getAttribute("countByMaritalStatus");
      if (countByMarital != null)
      {
        for (String entry : countByMarital)
        {
          int colonIndex = entry.indexOf(":");
          String status = entry.substring(0, colonIndex).trim();
          String count  = entry.substring(colonIndex + 1).trim();
    %>
          <tr><td><%= status %></td><td><%= count %></td></tr>
    <%
        }
      }
    %>
  </table>

  <p><a href="index.html">Back to home</a></p>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

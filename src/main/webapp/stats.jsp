<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Statistics - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patient Statistics</h2>
  <table class="stats-table">
    <tr><th>Metric</th><th>Value</th></tr>
    <%
      ArrayList<String> stats = (ArrayList<String>) request.getAttribute("stats");
      if (stats != null)
      {
        for (String stat : stats)
        {
          int colonIndex = stat.indexOf(":");
          String label = stat.substring(0, colonIndex).trim();
          String value = stat.substring(colonIndex + 1).trim();
    %>
          <tr><td><%= label %></td><td><%= value %></td></tr>
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

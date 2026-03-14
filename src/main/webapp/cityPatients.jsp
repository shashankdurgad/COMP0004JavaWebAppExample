<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patients in City - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <%
    String cityName = (String) request.getAttribute("cityName");
    ArrayList<String> patients = (ArrayList<String>) request.getAttribute("patientNames");
    int count = (patients != null) ? patients.size() : 0;
  %>
  <h2>Patients in <%= cityName %></h2>
  <p><%= count %> patient(s) found.</p>
  <ul>
    <%
      if (patients != null)
      {
        for (String name : patients)
        {
    %>
          <li><%= name %></li>
    <%
        }
      }
    %>
  </ul>
  <p><a href="insights">&#8592; Back to Insights</a></p>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient List - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>All Patients</h2>
  <p><a href="addpatient">+ Add New Patient</a></p>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <div class="error-box"><p><%= errorMessage %></p></div>
  <%
    }
  %>
  <ul>
    <%
      ArrayList<String> patients = (ArrayList<String>) request.getAttribute("patientNames");
      if (patients != null)
      {
        int i = 0;
        for (String patient : patients)
        {
    %>
          <li><a href="patient?row=<%= i %>"><%= patient %></a></li>
    <%
          i = i + 1;
        }
      }
    %>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

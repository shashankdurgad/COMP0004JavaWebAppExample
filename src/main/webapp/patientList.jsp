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
  <div class="section-header">
    <h2>All Patients</h2>
    <a href="addpatient" class="btn btn-primary">+ Add Patient</a>
  </div>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <div class="error-box"><p><%= errorMessage %></p></div>
  <%
    }
  %>
  <ul class="patient-list">
    <%
      ArrayList<String> patients = (ArrayList<String>) request.getAttribute("patientNames");
      if (patients != null)
      {
        <%-- i tracks the row index for the /patient?row=N URL; for-each does not expose an index. --%>
        int i = 0;
        for (String patient : patients)
        {
    %>
          <li><a href="patient?row=<%= i %>"><%= patient %></a></li>
    <%
          i++;
        }
      }
    %>
  </ul>
  <a href="index.html" class="back-link">&#8592; Back to home</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

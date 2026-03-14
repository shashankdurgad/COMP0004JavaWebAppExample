<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Detail - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patient Detail</h2>
  <h3><%= request.getAttribute("patientName") %></h3>
  <dl class="patient-detail">
    <%
      ArrayList<String> patientData = (ArrayList<String>) request.getAttribute("patientData");
      if (patientData != null)
      {
        for (String field : patientData)
        {
          int colonIndex = field.indexOf(":");
          String label = field.substring(0, colonIndex).trim();
          String value = field.substring(colonIndex + 1).trim();
          if (value.isEmpty()) { value = "—"; }
    %>
          <dt><%= label %></dt>
          <dd><%= value %></dd>
    <%
        }
      }
    %>
  </dl>
  <p><a href="patientList">&#8592; Back to patient list</a></p>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

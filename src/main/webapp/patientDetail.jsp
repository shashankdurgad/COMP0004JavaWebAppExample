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
  <div class="action-bar">
    <a href="editpatient?row=<%= request.getAttribute("row") %>" class="btn btn-secondary">Edit</a>
    <form method="POST" action="deletepatient" style="display:inline;"
          onsubmit="return confirm('Delete this patient? This cannot be undone.');">
      <input type="hidden" name="row" value="<%= request.getAttribute("row") %>">
      <button type="submit" class="btn-delete">Delete</button>
    </form>
  </div>
  <a href="patientList" class="back-link">&#8592; Back to patient list</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

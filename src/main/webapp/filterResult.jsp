<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Filter Results - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <%
    String gender = (String) request.getAttribute("gender");
    String genderLabel = "M".equals(gender) ? "Male" : "Female";
    ArrayList<String> patientNames = (ArrayList<String>) request.getAttribute("patientNames");
    int count = (patientNames != null) ? patientNames.size() : 0;
  %>
  <div class="section-header">
    <h2><%= genderLabel %> Patients</h2>
    <span class="badge"><%= count %> patient(s)</span>
  </div>
  <ul class="patient-list">
    <%
      if (patientNames != null)
      {
        for (String name : patientNames)
        {
    %>
          <li><span style="display:block; padding: 12px 18px; font-weight:500;"><%= name %></span></li>
    <%
        }
      }
    %>
  </ul>
  <div class="action-bar" style="margin-top: 20px;">
    <a href="filter?gender=M" class="btn btn-secondary">Male</a>
    <a href="filter?gender=F" class="btn btn-secondary">Female</a>
  </div>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

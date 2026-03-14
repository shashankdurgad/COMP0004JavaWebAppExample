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
  <h2>Filter: <%= genderLabel %> Patients</h2>
  <p><%= count %> patient(s) found.</p>
  <ul>
    <%
      if (patientNames != null)
      {
        for (String name : patientNames)
        {
    %>
          <li><%= name %></li>
    <%
        }
      }
    %>
  </ul>
  <p>
    <a href="filter?gender=M">Show Male</a> |
    <a href="filter?gender=F">Show Female</a> |
    <a href="index.html">Home</a>
  </p>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

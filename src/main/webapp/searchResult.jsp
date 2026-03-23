<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Search Results - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Search Results</h2>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <div class="error-box"><p><%= errorMessage %></p></div>
  <%
    }
    List<String> patients = (List<String>) request.getAttribute("result");
    if (patients != null && !patients.isEmpty())
    {
  %>
    <p class="text-muted"><span class="badge"><%= patients.size() %></span> patient(s) matched.</p>
    <ul class="patient-list">
      <%
        for (String patient : patients)
        {
      %>
        <li><span style="display:block; padding: 12px 18px; font-weight:500;"><%= patient %></span></li>
      <%
        }
      %>
    </ul>
  <%
    }
    // Only show "no results" when there is no error message already displayed.
    else if (errorMessage == null)
    {
  %>
    <p class="text-muted">No patients matched your search.</p>
  <%
    }
  %>
  <a href="search.html" class="back-link">&#8592; New search</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

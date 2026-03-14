<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Error - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Something went wrong</h2>
  <div class="error-box">
    <p><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "An unexpected error occurred." %></p>
  </div>
  <p><a href="index.html">Back to home</a></p>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

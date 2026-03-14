<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Export Successful - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Export Successful</h2>
  <div class="success-box">
    <p><%= request.getAttribute("message") %></p>
  </div>
  <a href="index.html" class="back-link">&#8592; Back to home</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

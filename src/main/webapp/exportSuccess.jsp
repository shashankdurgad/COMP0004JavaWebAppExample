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
  <p><%= request.getAttribute("message") %></p>
  <p><a href="index.html">&#8592; Back to home</a></p>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

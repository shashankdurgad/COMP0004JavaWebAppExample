<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title><%= request.getAttribute("pageTitle") %> - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2><%= request.getAttribute("pageTitle") %></h2>
  <%
    ArrayList<String> columns   = (ArrayList<String>) request.getAttribute("columns");
    ArrayList<String> values    = (ArrayList<String>) request.getAttribute("values");
    String formAction           = (String) request.getAttribute("formAction");
    int row                     = (Integer) request.getAttribute("row");
  %>
  <form method="POST" action="<%= formAction %>">
    <input type="hidden" name="row" value="<%= row %>">
    <table class="stats-table">
      <tr><th>Field</th><th>Value</th></tr>
      <%
        for (int i = 0; i < columns.size(); i = i + 1)
        {
          String col = columns.get(i);
          String val = (values != null && i < values.size()) ? values.get(i) : "";
      %>
          <tr>
            <td><label for="field_<%= i %>"><%= col %></label></td>
            <td>
              <input type="text"
                     id="field_<%= i %>"
                     name="<%= col %>"
                     value="<%= val %>"
                     style="width: 320px;">
            </td>
          </tr>
      <%
        }
      %>
    </table>
    <br>
    <button type="submit">Save</button>
    <a href="patientList" style="margin-left: 16px;">Cancel</a>
  </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

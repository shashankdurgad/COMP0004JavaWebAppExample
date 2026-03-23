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
  <%-- row is -1 for a new patient (add) or the 0-based index for an existing one (edit).
       The hidden field passes it back to the servlet so it knows which row to update. --%>
  <form method="POST" action="<%= formAction %>">
    <input type="hidden" name="row" value="<%= row %>">
    <div class="form-stack" style="max-width: 520px;">
      <%
        for (int i = 0; i < columns.size(); i++)
        {
          String col = columns.get(i);
          String val = (values != null && i < values.size()) ? values.get(i) : "";
      %>
          <div class="form-group">
            <label for="field_<%= i %>"><%= col %></label>
            <input type="text"
                   class="form-input"
                   id="field_<%= i %>"
                   name="<%= col %>"
                   value="<%= val %>">
          </div>
      <%
        }
      %>
      <div class="action-bar">
        <button type="submit" class="btn btn-primary">Save</button>
        <a href="patientList" class="btn btn-secondary">Cancel</a>
      </div>
    </div>
  </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

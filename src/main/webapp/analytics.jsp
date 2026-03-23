<%@ page import="java.util.ArrayList,java.util.LinkedHashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Analytics - Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Analytics</h2>

  <%-- ===== CHARTS ===== --%>
  <%
    LinkedHashMap<String, Integer> ageDist =
      (LinkedHashMap<String, Integer>) request.getAttribute("ageDistribution");

    // Compute max age bucket value for scaling bars
    int ageMax = 1;
    if (ageDist != null)
    {
      for (int v : ageDist.values()) { if (v > ageMax) ageMax = v; }
    }

    // Parse gender counts from stats for the gender bar
    int statMale = 0, statFemale = 0, statTotal = 1;
    ArrayList<String> statsForChart = (ArrayList<String>) request.getAttribute("stats");
    if (statsForChart != null)
    {
      for (String s : statsForChart)
      {
        if (s.startsWith("Total patients:"))
          statTotal = Math.max(1, Integer.parseInt(s.substring(s.indexOf(":") + 1).trim()));
        else if (s.startsWith("Male:"))
          statMale = Integer.parseInt(s.substring(s.indexOf(":") + 1).trim());
        else if (s.startsWith("Female:"))
          statFemale = Integer.parseInt(s.substring(s.indexOf(":") + 1).trim());
      }
    }
    int malePct   = (int) Math.round(statMale   * 100.0 / statTotal);
    int femalePct = 100 - malePct;

    // Compute max marital count for scaling
    ArrayList<String> maritalForChart = (ArrayList<String>) request.getAttribute("countByMaritalStatus");
    int maritalMax = 1;
    if (maritalForChart != null)
    {
      for (String e : maritalForChart)
      {
        int ci = e.indexOf(":");
        String raw = e.substring(ci + 1).trim().replace(" patient(s)", "").trim();
        try { int n = Integer.parseInt(raw); if (n > maritalMax) maritalMax = n; } catch (NumberFormatException ex) {}
      }
    }
  %>

  <div class="chart-grid">

    <%-- Age distribution: vertical bar chart --%>
    <div class="chart-card">
      <h3>Age Distribution</h3>
      <div class="bar-chart">
        <%
          if (ageDist != null)
          {
            for (java.util.Map.Entry<String, Integer> e : ageDist.entrySet())
            {
              int px = (int) Math.round(e.getValue() * 110.0 / ageMax);
              if (px < 2) px = 2;
        %>
              <div class="col">
                <span class="bar-val"><%= e.getValue() %></span>
                <div class="bar" style="--bar-height:<%= px %>px"></div>
                <span class="bar-label"><%= e.getKey() %></span>
              </div>
        <%
            }
          }
        %>
      </div>
    </div>

    <%-- Gender split: segmented bar --%>
    <div class="chart-card">
      <h3>Gender Split</h3>
      <div class="gender-bar">
        <div class="gender-bar__seg gender-bar__seg--male" style="--seg-width:<%= malePct %>%">
          <% if (malePct >= 12) { %>Male <%= malePct %>%<% } %>
        </div>
        <div class="gender-bar__seg gender-bar__seg--female" style="--seg-width:<%= femalePct %>%">
          <% if (femalePct >= 12) { %>Female <%= femalePct %>%<% } %>
        </div>
      </div>
      <div class="gender-legend">
        <span class="gender-legend__dot gender-legend__dot--male"></span> Male (<%= statMale %>)
        &nbsp;&nbsp;
        <span class="gender-legend__dot gender-legend__dot--female"></span> Female (<%= statFemale %>)
      </div>
    </div>

    <%-- Marital status: horizontal bar chart --%>
    <div class="chart-card">
      <h3>Marital Status</h3>
      <div class="hbar-chart">
        <%
          if (maritalForChart != null)
          {
            for (String e : maritalForChart)
            {
              int ci = e.indexOf(":");
              String rawLabel = e.substring(0, ci).trim();
              String statusLabel = rawLabel.equals("M") ? "Married" : rawLabel.equals("S") ? "Single" : rawLabel.isEmpty() ? "Unknown" : rawLabel;
              String raw = e.substring(ci + 1).trim().replace(" patient(s)", "").trim();
              int n = 0;
              try { n = Integer.parseInt(raw); } catch (NumberFormatException ex) {}
              int hpct = (int) Math.round(n * 100.0 / maritalMax);
        %>
              <div class="hbar-row">
                <span class="hbar-label"><%= statusLabel %></span>
                <div class="hbar-track">
                  <div class="hbar-fill" style="--fill-width:<%= Math.max(hpct, 2) %>%"></div>
                </div>
                <span class="hbar-val"><%= n %></span>
              </div>
        <%
            }
          }
        %>
      </div>
    </div>

  </div>
  <%-- ===== END CHARTS ===== --%>

  <h3>Summary Statistics</h3>
  <table class="stats-table">
    <tr><th>Metric</th><th>Value</th></tr>
    <%
      ArrayList<String> stats = (ArrayList<String>) request.getAttribute("stats");
      if (stats != null)
      {
        for (String stat : stats)
        {
          int colonIndex = stat.indexOf(":");
          String label = stat.substring(0, colonIndex).trim();
          String value = stat.substring(colonIndex + 1).trim();
    %>
          <tr><td><%= label %></td><td><%= value %></td></tr>
    <%
        }
      }
    %>
  </table>

  <h3>Oldest Patients</h3>
  <table class="stats-table">
    <tr><th>Category</th><th>Patient</th></tr>
    <tr>
      <td>Oldest living</td>
      <td><%= request.getAttribute("oldestLiving") %></td>
    </tr>
    <tr>
      <td>Oldest overall</td>
      <td><%= request.getAttribute("oldestOverall") %></td>
    </tr>
  </table>

  <h3>Patients by City</h3>
  <p class="text-muted">Click a city to see its patients.</p>
  <table class="stats-table">
    <tr><th>City</th><th>Count</th></tr>
    <%
      ArrayList<String> countByCity = (ArrayList<String>) request.getAttribute("countByCity");
      if (countByCity != null)
      {
        for (String entry : countByCity)
        {
          int colonIndex = entry.indexOf(":");
          String city  = entry.substring(0, colonIndex).trim();
          String count = entry.substring(colonIndex + 1).trim();
    %>
          <tr>
            <td><a href="city?name=<%= city %>"><%= city %></a></td>
            <td><%= count %></td>
          </tr>
    <%
        }
      }
    %>
  </table>

  <h3>Patients by Marital Status</h3>
  <table class="stats-table">
    <tr><th>Status</th><th>Count</th></tr>
    <%
      if (maritalForChart != null)
      {
        for (String entry : maritalForChart)
        {
          int colonIndex = entry.indexOf(":");
          String rawStatus = entry.substring(0, colonIndex).trim();
          String status = rawStatus.equals("M") ? "Married" : rawStatus.equals("S") ? "Single" : rawStatus.isEmpty() ? "Unknown" : rawStatus;
          String count  = entry.substring(colonIndex + 1).trim();
    %>
          <tr><td><%= status %></td><td><%= count %></td></tr>
    <%
        }
      }
    %>
  </table>

  <%
    ArrayList<String> malePatients = (ArrayList<String>) request.getAttribute("malePatients");
    int maleCount = (malePatients != null) ? malePatients.size() : 0;
  %>
  <div class="section-header">
    <h3>Male Patients</h3>
    <span class="badge"><%= maleCount %> patient(s)</span>
  </div>
  <ul class="patient-list">
    <%
      if (malePatients != null)
      {
        for (String name : malePatients)
        {
    %>
          <li><span style="display:block; padding: 12px 18px; font-weight:500;"><%= name %></span></li>
    <%
        }
      }
    %>
  </ul>

  <%
    ArrayList<String> femalePatients = (ArrayList<String>) request.getAttribute("femalePatients");
    int femaleCount = (femalePatients != null) ? femalePatients.size() : 0;
  %>
  <div class="section-header">
    <h3>Female Patients</h3>
    <span class="badge"><%= femaleCount %> patient(s)</span>
  </div>
  <ul class="patient-list">
    <%
      if (femalePatients != null)
      {
        for (String name : femalePatients)
        {
    %>
          <li><span style="display:block; padding: 12px 18px; font-weight:500;"><%= name %></span></li>
    <%
        }
      }
    %>
  </ul>

  <a href="index.html" class="back-link">&#8592; Back to home</a>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>

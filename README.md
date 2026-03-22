# Patient Data App

A Java web application for browsing and managing patient records. Built with embedded Tomcat, Jakarta Servlets, and JSPs. Data is loaded from a CSV file at startup.

## Features

- **All Patients** — browse the full patient list; add, edit, or delete records
- **Search** — multi-keyword search across all fields (all keywords must match)
- **Analytics** — age distribution chart, gender split, marital status breakdown, oldest patients, and per-city counts
- **Export JSON** — export the full dataset to `data/patients.json`

## Prerequisites

- Java 25
- Maven 3.9+

## Run

```bash
mvn clean compile exec:exec
```

Open `http://localhost:8080`. Set `SERVER_PORT=9090` to use a different port.

## Build WAR

```bash
mvn clean package
```

Output is written to `war-file/`.

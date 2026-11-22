# CSA09-192511104B-Assignment4

## Student Details

- **Name:** S. Lekha
- **Register Number:** 192511104B
- **Course:** Programming in Java (CSA09)
- **Course Outcome:** CO4 – Event-Driven GUI Design
- **Java Version:** JDK ___ (run `java -version` and fill this in)
- **Environment:** Windows, Visual Studio Code

## Assignment Tasks

| Task | Description | Main class |
| ---- | ----------- | ---------- |
| Task 01 | GUI Design Review (fee payment, FeeService separation) | `FeeWindow` |
| Task 02 | Hospital Outpatient Appointment Desk | `AppointmentDesk` |
| Task 03 | Campus Library Search Screen (layout managers) | `LibrarySearch` |
| Task 04 | Event-Handling Forensics (mouse events, repaint) | `DrawPad` |
| Task 05 | Currency Converter using four event-handling styles | `Converter` and three variants |
| Task 06 | Campus Event Registration (Swing + MySQL + JDBC) | `EventRegistrationFrame` |

Each task folder holds its source files and a `Screenshots/` folder with the execution evidence.

## Compile and Run (Tasks 01–05)

Open PowerShell in the project root, then for each task:

```
cd Task01
javac *.java
java FeeWindow
```

| Folder | Run command |
| ------ | ----------- |
| Task01 | `java FeeWindow` |
| Task02 | `java AppointmentDesk` |
| Task03 | `java LibrarySearch` |
| Task04 | `java DrawPad` |
| Task05 | `java Converter` |

Task 05 has four versions of the same converter, one per event-handling style:

1. `Converter.java` – lambda expression
2. `ConverterFrameListener.java` – frame implements ActionListener
3. `ConverterNamedInner.java` – named inner class
4. `ConverterAnonymous.java` – anonymous inner class

All four convert with the Convert button or the Enter key and validate the amount. The conversion rates are illustrative fixed values, not live exchange rates.

## Task 06 – Campus Event Registration

### Features

- Student registration with input validation (required fields, email format)
- Duplicate registration prevention using a database unique constraint
- Paginated viewing of registrations (`LIMIT` / `OFFSET`)
- Export of registrations to CSV
- Database work done in the background with `SwingWorker`

### Requirements

- Java JDK
- MySQL Server
- MySQL Connector/J (place the `.jar` in `Task06/lib/`)

### Database setup

```
cd Task06
mysql -u root -p < schema.sql
```

Database: `campus_events`, table: `registrations`, with `UNIQUE (student_reg_no, event_name)`.

### Set connection details (PowerShell)

The password is read from an environment variable and is never stored in the code.

```
$env:CAMPUS_DB_URL = "jdbc:mysql://localhost:3306/campus_events"
$env:CAMPUS_DB_USER = "root"
$secure = Read-Host "Enter MySQL password" -AsSecureString
$env:CAMPUS_DB_PASSWORD = [System.Net.NetworkCredential]::new("", $secure).Password
```

### Compile and run

Replace `X.X.X` with your Connector/J version. Run from the project root:

```
javac -cp "Task06\lib\mysql-connector-j-X.X.X.jar" -d Task06\out Task06\*.java
java -cp "Task06\out;Task06\lib\mysql-connector-j-X.X.X.jar" EventRegistrationFrame
```

### Task 06 files

- `DBConnection.java` – creates the MySQL connection
- `Registration.java` – one registration record
- `RegistrationRepository.java` – JDBC operations with `PreparedStatement`
- `RegistrationService.java` – validation rules
- `EventRegistrationFrame.java` – Swing user interface
- `schema.sql` – database and table creation script

## Project Structure

```
CSA09-192511104B-Assignment4/
├── Task01/   (source + Screenshots/)
├── Task02/
├── Task03/
├── Task04/
├── Task05/
├── Task06/   (source + schema.sql + lib/ + Screenshots/)
├── Assignment_4.docx
├── README.md
└── .gitignore
```

## General Notes

- Generated `.class` files are excluded from Git by `.gitignore`.
- Task 05 uses illustrative fixed conversion rates.
- Task 06 needs MySQL Server and the MySQL Connector/J library.
- Screenshots of the programs running are stored in each task's `Screenshots/` folder.

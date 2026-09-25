# Lab Equipment Management

A Java Swing desktop application for managing laboratory equipment using JDBC and Apache Derby. This is a learning project for practising Java GUI development, SQL and database operations.

## Features

- Connect to a local Apache Derby database.
- Add equipment with a name, department, price and in-use status.
- View equipment records in a table.
- Load unique departments in alphabetical order and filter records by department.
- Search equipment names within the existing result set after clicking **View All**.
- Display total records, total expenditure, number of departments, and the cheapest and most expensive equipment.
- Mark Multimedia equipment purchased in a selected month as damaged and not in use.
- Delete damaged equipment that is not in use after confirmation.
- Clear the displayed results without deleting database records.

## Technologies

- Java
- Java Swing
- JDBC
- Apache Derby
- Apache NetBeans
- SQL

## Requirements

- A Java Development Kit (JDK) compatible with your NetBeans and Derby installations.
- Apache NetBeans with Java project support.
- Apache Derby Network Server and its compatible JDBC client driver libraries.

## Database setup

1. Start the Derby Network Server on `localhost`, port `1527`.
2. Create a database named `labDB` with the practice credentials `app` / `app`.
3. Connect to that database as `app` and execute the supplied `labdata (1)(1).sql` script. This creates `APP.LAB_EQUIPMENT` and inserts the sample data. Run the setup script once against a fresh database.
4. Check that the connection settings in `BtnConnectListener` match your local database:

```text
URL: jdbc:derby://localhost:1527/labDB
Username: app
Password: app
```

The supplied connection listener uses values written directly in the code. If your local settings differ, update that listener; changing only the login text fields will not change those hard-coded values. These credentials are for local practice.

The sample script contains 20 equipment records across five departments: Computer Science, Computer Systems Engineering, Informatics, Information Technology and Multimedia.

## Running the application

1. Download or clone this repository. Extract it first if downloaded as a ZIP.
2. Open the project folder in NetBeans.
3. Add the Derby JDBC client libraries to the project if they are missing, and resolve any library paths that refer to another computer.
4. Complete the database setup above.
5. Run `za.ac.o4.m5.EquipmentOperationGUI`.
6. Click **Connect**, then **View All** to display the equipment.

### Filtering by department

1. Click **Load Departments**.
2. Select a department from the dropdown.
3. Click **Filter Department**.

Loading departments changes the dropdown. Filtering changes the visible table. Neither action changes the database records.

## Main project files

| File or folder | Purpose |
| --- | --- |
| `src/` | Java source code |
| `nbproject/` | Shared NetBeans project configuration |
| `build.xml` | Ant build configuration |
| `manifest.mf` | Application manifest, if included |
| `labdata (1)(1).sql` | Database table definition and sample data |

## What I am practising

- Building interfaces with Java Swing.
- Handling button clicks with ActionListener classes.
- Connecting Java applications to a database using JDBC.
- Using SELECT, INSERT, UPDATE and DELETE statements.
- Using DISTINCT and ORDER BY to load unique departments.
- Using parameterized queries to filter records.
- Displaying query results in a JTable.
- Publishing and documenting a project on GitHub.

## Project status

This is a student practice project. Database setup is manual, and the application requires a running local Derby server.

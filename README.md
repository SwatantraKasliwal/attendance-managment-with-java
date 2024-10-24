# attendance-managment-with-java

Created the attendace with the java and SQL to manage

⛏️Tool Required - 
Intellij
PostgreSQL
PostgresJDBC Driver (link: 'https://jdbc.postgresql.org/')

📝Your Project File should be like this -
YourProjectName/
└── src/
    └── com/
        └── company/
            └── Main.java

⚙️Required Set up - 
Download the PostegresJDBC Driver and follow this steps:
**Add the JDBC Driver to your NetBeans Project in Intellij:
  1.Right-click on your project and select "Properties".
  2.Go to the "Libraries" category.
  3.Click "Add JAR/Folder".
  4.Browse to the downloaded postgresql-<version>.jar file and select it.
  5.Click OK to add the driver.

📅Database Setup -
-- Create database
CREATE DATABASE attendance_db;

-- Create attendance table
CREATE TABLE attendance (
    id SERIAL PRIMARY KEY,
    NAME VARCHAR(100),
    SUBJECT VARCHAR(50),
    TOTAL_CLASSES INT,
    CLASSES_ATTENDED INT,
    TOTAL_ATTENDANCE VARCHAR(10)
);

The Final step is to run the Main.java file from intellij

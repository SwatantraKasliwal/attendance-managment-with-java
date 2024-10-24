# attendance-managment-with-java

Attendace Managment with the java and SQL

⛏️Tool Required - 
Intellij (Community Edition),
PostgreSQL,
PostgresJDBC Driver (link: 'https://jdbc.postgresql.org/')

📝Your Project File should be like this -
YourProjectName/
└── src/
    └── com/
        └── company/
            └── Main.java, Attendence.java

⚙️Required Set up - 
Download the PostegresJDBC Driver and follow this steps:
**Add the JDBC Driver to your NetBeans Project in Intellij:
  1.Right-click on your project and select "Properties".
  2.Go to the "Libraries" category.
  3.Click "Add JAR/Folder".
  4.Browse to the downloaded postgresql-<version>.jar file and select it.
  5.Click OK to add the driver.

📅Database Setup - (Run this commands in your PostgreSQL)
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

Note - Don't forget to make changes in 'Postgres Connection Details' section in  the attendace.java to run the project. Dont make any changes in the code
The Final step is to run the Main.java file from intellij

# MyStore - Java JDBC Program

This project involves developing a Java program using JDBC with PostgreSQL to interact with a database
containing a single table for books. The program is a standalone Java application featuring a menu-driven 
user interface, primarily for bookstore employees.

## Features
- Console-based menu for user interaction
- `listAllBooks`: List all books in the inventory
- `searchBooksByGenre`: Search for books by genre
- `addBook`: Add new books to the inventory
- `deleteBook`: Delete books from the inventory

## Requirements
- **Java Development Kit (JDK)**: Ensure that Java is installed on your machine.
- **PostgreSQL Database**: The database must be running and contain the required table structure as per `table.sql`.
- **PostgreSQL JDBC Driver**: The driver must be included in the classpath.

## Setup Instructions

### Database Setup
1. Create a PostgreSQL database with a name of your choice.
2. Create username and password for your database.
3. Run the SQL script `table.sql` to create the `book` table and insert sample data.

### Development Environment Setup
1. **Install Java Development Kit (JDK)**:
   - Ensure that JDK is installed on your system.
2. **Install PostgreSQL**:
   - Download and install PostgreSQL.

3. **Download PostgreSQL JDBC Driver**:
   - Download the JDBC driver from [PostgreSQL JDBC Download Page](https://jdbc.postgresql.org/download/.).
   - Make sure to place the downloaded JAR file in the same directory as your `MyStore.java` file or note its path for the classpath.

4. **Set Environment Variables** (optional):
   - If you want to run Java commands from any directory, ensure that the JDK `bin` directory is added to your system's PATH environment variable.

5. **Verify Installation**:
   - Check if Java and PostgreSQL are correctly installed by running the following commands in your terminal:
     ```bash
     java -version
     ```
     ```bash
     psql --version
     ```

   
## How to Execute the Program

1. Update the database name, username, and password in the `MyStore.java` file.
2. Compile: `javac MyStore.java`
3. Run: 
- **For Mac/Unix**:
  ```
  java -cp postgresql-<version>.jar:. MyStore
  ```
- **For Windows**:
  ```
  java -cp postgresql-<version>.jar;. MyStore
  ```

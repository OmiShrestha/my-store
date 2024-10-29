/*
 Author: Omi Shrestha

 Program name: MyStore.java
 Compile: javac MyStore.java
 Run: - For Mac/Unix: java -cp postgresql-<version>.jar:. MyStore
      - For Windows: java -cp postgresql-<version>.jar;. MyStore

 Description: This program implements a simple bookstore management system using JDBC to interact
              with a PostgreSQL database. It offers functionalities to:
              - List all books in the inventory
              - Search for books by genre
              - Add new books to the inventory
              - Delete books from the inventory
              The program provides a console-based menu to guide the user through these options
              and executes SQL queries to perform each operation on the database.

 Requirements:
              - PostgreSQL JDBC driver must be in the classpath.
              - Ensure the PostgreSQL database is running and contains the required table structure
                as per 'table.sql'.
 */

import java.sql.*;
import java.util.Scanner;

public class MyStore {
    // Database connection parameters
    private static final String URL = "jdbc:postgresql://localhost:5432/mydb";
    private static final String USER = "omi";
    private static final String PASS = "shrestha";

    public static void main(String[] args) {

        // Establishes a connection to the database and creates a scanner for user input
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Scanner scan = new Scanner(System.in)) {

            int choice;
            do {
                // Menu options for the user
                System.out.println("1. Listing All Books");
                System.out.println("2. Search Books by Genre");
                System.out.println("3. Add a Book");
                System.out.println("4. Delete a Book");
                System.out.println("5. Quit");
                System.out.print("Choose an option: ");
                choice = scan.nextInt();
                scan.nextLine();

                // Different user choices
                switch (choice) {
                    case 1:
                        listAllBooks(conn);
                        break;
                    case 2:
                        searchBooksByGenre(conn, scan);
                        break;
                    case 3:
                        addBook(conn, scan);
                        break;
                    case 4:
                        deleteBook(conn, scan);
                        break;
                    case 5:
                        System.out.println("Quitting the program...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again...");
                }
            } while (choice != 5); // Repeats until the user chooses to quit

        } catch (SQLException e) {
            System.err.println("Database connection failed. Error details:");
            e.printStackTrace();
        }
    }

    // Lists all the books from the database
    private static void listAllBooks(Connection conn) throws SQLException {
        String sql = "SELECT * FROM book ORDER BY title ASC";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getString("isbn") + " | " +
                                   rs.getString("title") + " | " +
                                   rs.getString("publisher") + " | " +
                                   rs.getString("genre") + " | " +
                                   rs.getDouble("unit_price"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving book list: " + e.getMessage());
        }
        System.out.println();
    }

    // Searches for books by genre
    private static void searchBooksByGenre(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter genre to search: ");
        String genre = scanner.nextLine();
        String sql = "SELECT * FROM book WHERE genre = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, genre);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("Genre of book not found.");
                } else {
                    while (rs.next()) {
                        System.out.println(rs.getString("isbn") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("publisher") + " | " +
                                rs.getString("genre") + " | " +
                                rs.getDouble("unit_price"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching for books by genre: " + e.getMessage());
        }
        System.out.println();
    }

    // To add a new book to the database
    private static void addBook(Connection conn, Scanner scanner) throws SQLException {

        String isbn = "";  // Initializes isbn as an empty string
        boolean validIsbn = false;
        while (!validIsbn) { // Loops until a valid ISBN is entered
            System.out.print("Enter ISBN: ");
            isbn = scanner.nextLine();

            // Checks if the input is a valid integer and has exactly 13 digits
            if (isbn.matches("\\d{13}")) {
                validIsbn = true; // Sets the flag to true if valid
            } else {
                System.out.println("Invalid ISBN format. Please enter exactly 13 digits.");
            }
        }

        // Making sure that at least the book title entered cannot be null or empty.
        String title = "";  // Initializes title as an empty string
        boolean validTitle = false;
        // Loops until a valid title is entered
        while (!validTitle) {
            System.out.print("Enter Title: ");
            title = scanner.nextLine();

            // Checks if the title is not null or empty
            if (title != null && !title.trim().isEmpty()) {
                validTitle = true;
            } else {
                System.out.println("Title cannot be null or empty. Please enter a valid title.");
            }
        }

        System.out.print("Enter Publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter Unit Price: ");
        double unitPrice = scanner.nextDouble();
        scanner.nextLine();

        String sql = "INSERT INTO book (isbn, title, publisher, genre, unit_price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.setString(2, title);
            ps.setString(3, publisher);
            ps.setString(4, genre);
            ps.setDouble(5, unitPrice);
            ps.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
        System.out.println();
    }

    // Deletes a book from the database
    private static void deleteBook(Connection conn, Scanner scanner) throws SQLException {
        listAllBooks(conn);

        String isbn = "";  // Initializes isbn as an empty string
        boolean validIsbn = false;
        while (!validIsbn) { // Loops until a valid ISBN is entered
            System.out.print("Enter ISBN of the book to delete: ");
            isbn = scanner.nextLine();
            if (isbn.matches("\\d{13}")) {
                validIsbn = true;
            } else {
                System.out.println("Invalid ISBN format. Please enter exactly 13 digits.");
            }
        }

        String sql = "DELETE FROM book WHERE isbn = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully.");
                listAllBooks(conn);
            } else {
                System.out.println("No book found with that ISBN.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }
}
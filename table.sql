/*
 Author: Omi Shrestha
 File name: table.sql
 Description: This SQL script is used for the creation and management of the "book" table 
 in a PostgreSQL database for a bookstore management system. It creates a new "book" table with 
 appropriate columns and data types. The script also includes sample data insertion for testing 
 purposes, adding several books with attributes such as ISBN, title, publisher, genre, and unit price.
*/

-- Creates a table called "book"
CREATE TABLE book (
    isbn VARCHAR(20) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    unit_price NUMERIC(10, 2)
);

-- Adds some books to that table for testing purposes
INSERT INTO book (isbn, title, publisher, genre, unit_price) VALUES
		('9780743273565', 'Mockingbird', 'J.B. Lippincott & Co.', 'Fiction', 12.99),
		('9780452284234', '1984', 'Secker & Warburg', 'Dystopian', 14.99),
		('9780743273572', 'Catcher', 'Little, Brown and Company', 'Fiction', 10.49),
		('9780670813024', 'Pride', 'T. Egerton', 'Romance', 11.50),
		('9780061120084', 'Alchemist', 'HarperOne', 'Adventure', 15.00),
		('9780452284258', 'Rings', 'George Allen & Unwin', 'Fantasy', 29.99),
		('9781501102932', 'Diary', 'Contact Publishing', 'Biography', 9.99);

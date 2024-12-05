-- Create the database
DROP DATABASE IF EXISTS library_management;
CREATE DATABASE library_management;
USE library_management;

-- Create Genre table
DROP TABLE IF EXISTS Genre;
CREATE TABLE Genre (
    genre_name VARCHAR(50) PRIMARY KEY
);

-- Create Media table (parent table)
DROP TABLE IF EXISTS Media;
CREATE TABLE Media (
    mid INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    publisher VARCHAR(100),
    reference_number VARCHAR(50) UNIQUE,
    location VARCHAR(100),
    publication_date DATE,
    is_checked_out BOOLEAN DEFAULT FALSE
);

-- Create Book table
DROP TABLE IF EXISTS Book;
CREATE TABLE Book (
    mid INT PRIMARY KEY,
    isbn VARCHAR(13) UNIQUE,
    author VARCHAR(255),
    hardcover BOOLEAN,
    pages INT,
    FOREIGN KEY (mid) REFERENCES Media(mid)
);

-- Create CD table
DROP TABLE IF EXISTS CD;
CREATE TABLE CD (
    mid INT PRIMARY KEY,
    artist VARCHAR(255),
    number_cds_in_set INT,
    type ENUM('audiobook', 'music'),
    FOREIGN KEY (mid) REFERENCES Media(mid)
);

-- Create Record table
DROP TABLE IF EXISTS Record;
CREATE TABLE Record (
    mid INT PRIMARY KEY,
    artist VARCHAR(255),
    label VARCHAR(100),
    num_lps INT,
    FOREIGN KEY (mid) REFERENCES Media(mid)
);

-- Create Magazine table
DROP TABLE IF EXISTS Magazine;
CREATE TABLE Magazine (
    mid INT PRIMARY KEY,
    issue_num VARCHAR(50),
    pages INT,
    FOREIGN KEY (mid) REFERENCES Media(mid)
);

-- Create Media_Genre junction table
DROP TABLE IF EXISTS Media_Genre;
CREATE TABLE Media_Genre (
    mid INT,
    genre_name VARCHAR(50),
    PRIMARY KEY (mid, genre_name),
    FOREIGN KEY (mid) REFERENCES Media(mid),
    FOREIGN KEY (genre_name) REFERENCES Genre(genre_name)
);

-- Create Library table
DROP TABLE IF EXISTS Library;
CREATE TABLE Library (
    library_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    hours TEXT,
    website VARCHAR(255),
    address_street_num VARCHAR(20),
    address_street_name VARCHAR(255),
    address_city VARCHAR(100),
    address_state VARCHAR(50),
    address_zip_code VARCHAR(10)
);

-- Create Employee table (parent table)
DROP TABLE IF EXISTS Employee;
CREATE TABLE Employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    library_id INT,
    FOREIGN KEY (library_id) REFERENCES Library(library_id)
);

-- Create Librarian table
DROP TABLE IF EXISTS Librarian;
CREATE TABLE Librarian (
    employee_id INT PRIMARY KEY,
    department ENUM('Childrens', 'Adult'),
    head BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
);

-- Create Maintenance table
DROP TABLE IF EXISTS Maitenance;
CREATE TABLE Maintenance (
    employee_id INT PRIMARY KEY,
    specialty VARCHAR(100),
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id)
);

-- Create Patron table
DROP TABLE IF EXISTS Patron;
CREATE TABLE Patron (
    phone_number VARCHAR(20) PRIMARY KEY,
    card_number VARCHAR(50),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE,
    address_street_num VARCHAR(20),
    address_street_name VARCHAR(255),
    address_city VARCHAR(100),
    address_state VARCHAR(50),
    address_zip_code VARCHAR(10),
    UNIQUE (card_number)
);

-- Create Library_Patron junction table
DROP TABLE IF EXISTS Library_Patron;
CREATE TABLE Library_Patron (
    library_id INT,
    phone_number VARCHAR(20),
    PRIMARY KEY (library_id, phone_number),
    FOREIGN KEY (library_id) REFERENCES Library(library_id),
    FOREIGN KEY (phone_number) REFERENCES Patron(phone_number)
);

-- Create Borrowing table
DROP TABLE IF EXISTS Borrowing;
CREATE TABLE Borrowing (
    phone_number VARCHAR(20),
    mid INT,
    librarian_id INT,
    checkout_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    PRIMARY KEY (phone_number, mid, checkout_date),
    FOREIGN KEY (phone_number) REFERENCES Patron(phone_number),
    FOREIGN KEY (mid) REFERENCES Media(mid),
    FOREIGN KEY (librarian_id) REFERENCES Librarian(employee_id),
    CHECK (return_date IS NULL OR return_date >= checkout_date)
);

-- Create Event table
DROP TABLE IF EXISTS Event;
CREATE TABLE Event (
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    event_name VARCHAR(255) NOT NULL,
    date DATETIME NOT NULL,
    capacity INT,
    children BOOLEAN DEFAULT FALSE,
    library_id INT,
    FOREIGN KEY (library_id) REFERENCES Library(library_id)
);

-- Create Event_Patron junction table
DROP TABLE IF EXISTS Event_Patron;
CREATE TABLE Event_Patron (
    event_id INT,
    phone_number VARCHAR(20),
    PRIMARY KEY (event_id, phone_number),
    FOREIGN KEY (event_id) REFERENCES Event(event_id),
    FOREIGN KEY (phone_number) REFERENCES Patron(phone_number)
);

-- Create Seller table
DROP TABLE IF EXISTS Seller;
CREATE TABLE Seller (
    seller_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    address_street_num VARCHAR(20),
    address_street_name VARCHAR(255),
    address_city VARCHAR(100),
    address_state VARCHAR(50),
    address_zip_code VARCHAR(10)
);

-- Create Library_Seller junction table
DROP TABLE IF EXISTS Library_Seller;
CREATE TABLE Library_Seller (
    library_id INT,
    seller_id INT,
    PRIMARY KEY (library_id, seller_id),
    FOREIGN KEY (library_id) REFERENCES Library(library_id),
    FOREIGN KEY (seller_id) REFERENCES Seller(seller_id)
);
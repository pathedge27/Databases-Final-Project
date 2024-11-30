	CREATE DATABASE registrar;

	DROP TABLE IF EXISTS library;
	CREATE TABLE library (
		library_name VARCHAR(64) NOT NULL,
		phone_number VARCHAR(11) NOT NULL,
		hours VARCHAR(20),
		website VARCHAR(64),
		library_address VARCHAR (255) PRIMARY KEY
	);

	DROP TABLE IF EXISTS employee;
	CREATE TABLE employee (
		employee_id INT PRIMARY KEY,
		first_name VARCHAR(32),
		last_name VARCHAR(32),
		library_address VARCHAR (255),
		
		FOREIGN KEY(library_address) REFERENCES library(library_address) ON UPDATE RESTRICT ON DELETE RESTRICT
	);

	DROP TABLE IF EXISTS librarian;
	CREATE TABLE librarian (
		employee_id INT PRIMARY KEY,
		department VARCHAR(255),
		department_head BOOLEAN,
		FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON UPDATE CASCADE ON DELETE CASCADE
	);

	DROP TABLE IF EXISTS maintenance;
	CREATE TABLE maintenance (
		employee_id INT PRIMARY KEY,
		specialty VARCHAR(255),
		FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON UPDATE CASCADE ON DELETE CASCADE
	);

	DROP TABLE IF EXISTS patron;
	CREATE TABLE patron (
		`phone_number` VARCHAR(11) PRIMARY KEY,
		card_number INT UNIQUE NOT NULL,
		first_name VARCHAR(32) NOT NULL,
		last_name VARCHAR(32) NOT NULL,
		dob DATE NOT NULL,
		address_street_num INT,
		address_street_name VARCHAR(255),
		address_city VARCHAR(255),
		address_state VARCHAR(50),
		address_zip_code VARCHAR(9),
		library_address VARCHAR (255),
		
		FOREIGN KEY (library_address) REFERENCES library(library_address) ON UPDATE RESTRICT ON DELETE RESTRICT
	);

	DROP TABLE IF EXISTS library_event;
	CREATE TABLE library_event (
		event_name VARCHAR(255) PRIMARY KEY,
		event_date DATE NOT NULL,
		event_capacity INT NOT NULL,
		event_for_children BOOLEAN NOT NULL,
		library_address VARCHAR (255),
		
		FOREIGN KEY(library_address) REFERENCES library(library_address) ON UPDATE RESTRICT ON DELETE RESTRICT
	);

	DROP TABLE IF EXISTS event_patron;
	CREATE TABLE event_patron (
		patron_phone INT NOT NULL,
		event_name VARCHAR(255) NOT NULL,
		
		PRIMARY KEY (patron_phone, event_name),
		FOREIGN KEY (patron_phone) REFERENCES patron(phone_number) ON UPDATE RESTRICT ON DELETE RESTRICT,
		FOREIGN KEY (event_name) REFERENCES library_event(event_name) ON UPDATE RESTRICT ON DELETE RESTRICT
	);

	DROP TABLE IF EXISTS seller;
	CREATE TABLE seller (
		seller_name VARCHAR(64) PRIMARY KEY,
		seller_address VARCHAR(255) NOT NULL
	);

	DROP TABLE IF EXISTS seller_library;
	CREATE TABLE seller_library (
		seller_name VARCHAR(64) NOT NULL,
		library_address VARCHAR(255) NOT NULL,
		PRIMARY KEY (seller_name, library_address),
		FOREIGN KEY (seller_name) REFERENCES seller(seller_name) ON UPDATE RESTRICT ON DELETE RESTRICT,
		FOREIGN KEY (library_address) REFERENCES library(library_address) ON UPDATE RESTRICT ON DELETE RESTRICT
	);

	DROP TABLE IF EXISTS media;
	CREATE TABLE media (
		mid INT PRIMARY KEY,
		title VARCHAR(255) NOT NULL,
		publisher VARCHAR(255) NOT NULL,
		reference_number INT UNIQUE,
		location VARCHAR(64) NOT NULL,
		publication_date DATE NOT NULL,
		library_address VARCHAR (255),
		
		FOREIGN KEY (library_address) REFERENCES library(library_address) ON UPDATE RESTRICT ON DELETE RESTRICT
	);

	DROP TABLE IF EXISTS book;
	CREATE TABLE book (
		mid INT PRIMARY KEY,
		isbn INT UNIQUE,
		author VARCHAR(255) NOT NULL,
		hardcover BOOLEAN NOT NULL,
		pages INT NOT NULL,
		
		FOREIGN KEY (mid) REFERENCES media(mid) ON UPDATE CASCADE ON DELETE CASCADE
	);

	DROP TABLE IF EXISTS cd;
	CREATE TABLE cd (
		mid INT PRIMARY KEY,
		artist VARCHAR(255) NOT NULL,
		number_cds_in_set INT NOT NULL,
		cd_type VARCHAR(255) NOT NULL,
		
		FOREIGN KEY (mid) REFERENCES media(mid) ON UPDATE CASCADE ON DELETE CASCADE
	);

	DROP TABLE IF EXISTS record;
	CREATE TABLE record (
		mid INT PRIMARY KEY,
		artist VARCHAR(255) NOT NULL,
		label VARCHAR(255) NOT NULL,
		num_lps INT NOT NULL,
		
		FOREIGN KEY (mid) REFERENCES media(mid) ON UPDATE CASCADE ON DELETE CASCADE
	);

	DROP TABLE IF EXISTS magazine;
	CREATE TABLE magazine (
		mid INT PRIMARY KEY,
		magazine_name VARCHAR(255) NOT NULL,
		issue_num INT NOT NULL,
        pages INT NOT NULL,
		UNIQUE(magazine_name, issue_num),
		
		FOREIGN KEY (mid) REFERENCES media(mid) ON UPDATE CASCADE ON DELETE CASCADE
	);

	DROP TABLE IF EXISTS genre;
	CREATE TABLE genre (
		genre_name VARCHAR(255) PRIMARY KEY
	);

	DROP TABLE IF EXISTS media_genre;
	CREATE TABLE media_genre (
		mid INT NOT NULL,
		genre_name VARCHAR(255) NOT NULL,
		PRIMARY KEY (mid, genre_name),
		
		FOREIGN KEY (mid) REFERENCES media(mid) ON UPDATE RESTRICT ON DELETE RESTRICT,
		FOREIGN KEY (genre_name) REFERENCES genre(genre_name) ON UPDATE RESTRICT ON DELETE RESTRICT
	);
    
    DROP TABLE IF EXISTS borrows;
	CREATE TABLE borrows (
		phone_number VARCHAR(11) NOT NULL,
		mid INT NOT NULL,
		borrow_date DATE NOT NULL,
		return_date DATE,
		PRIMARY KEY (phone_number, mid),
        
		FOREIGN KEY (phone_number) REFERENCES patron(phone_number) ON UPDATE RESTRICT ON DELETE RESTRICT,
		FOREIGN KEY (mid) REFERENCES media(mid) ON UPDATE RESTRICT ON DELETE RESTRICT
	);

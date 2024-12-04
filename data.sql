-- Populate Data
USE library_management;

-- Insert Genres
INSERT INTO Genre (genre_name) VALUES
('Fiction'),
('Non-Fiction'),
('Science Fiction'),
('Mystery'),
('Biography'),
('Children''s'),
('Reference'),
('History'),
('Science'),
('Music');

-- Insert Libraries
INSERT INTO Library (name, phone_number, hours, website, address_street_num, address_street_name, address_city, address_state, address_zip_code) VALUES
('Central Library', '555-0100', 'Mon-Fri 9am-9pm, Sat-Sun 10am-6pm', 'www.centrallibrary.org', '123', 'Main Street', 'Springfield', 'IL', '62701'),
('West Branch Library', '555-0101', 'Mon-Sat 10am-8pm, Sun 12pm-5pm', 'www.westbranchlibrary.org', '456', 'Oak Avenue', 'Springfield', 'IL', '62702'),
('East Side Library', '555-0102', 'Mon-Fri 9am-8pm, Sat 10am-5pm', 'www.eastsidelibrary.org', '789', 'Maple Drive', 'Springfield', 'IL', '62703');

-- Insert Employees
INSERT INTO Employee (first_name, last_name, library_id) VALUES
('John', 'Smith', 1),
('Mary', 'Johnson', 1),
('Robert', 'Williams', 1),
('Patricia', 'Brown', 2),
('Michael', 'Davis', 2),
('Jennifer', 'Miller', 3),
('William', 'Wilson', 3),
('Elizabeth', 'Moore', 1),
('David', 'Taylor', 2),
('Susan', 'Anderson', 3);

-- Insert Librarians
INSERT INTO Librarian (employee_id, department, head) VALUES
(1, 'Adult', TRUE),
(2, 'Childrens', TRUE),
(4, 'Adult', FALSE),
(6, 'Childrens', FALSE),
(8, 'Adult', FALSE),
(9, 'Childrens', FALSE);

-- Insert Maintenance Staff
INSERT INTO Maintenance (employee_id, specialty) VALUES
(3, 'General Maintenance'),
(5, 'HVAC'),
(7, 'Electronics'),
(10, 'Plumbing');

-- Insert Media
INSERT INTO Media (title, publisher, reference_number, location, publication_date, is_checked_out) VALUES
('The Great Gatsby', 'Scribner', 'BOOK-001', 'Fiction Section A1', '2020-01-15', FALSE),
('To Kill a Mockingbird', 'Grand Central', 'BOOK-002', 'Fiction Section A2', '2019-06-20', TRUE),
('1984', 'Penguin', 'BOOK-003', 'Fiction Section A3', '2021-03-10', FALSE),
('Beatles Greatest Hits', 'Apple Records', 'CD-001', 'Music Section B1', '2018-12-01', FALSE),
('Mozart Symphony No. 40', 'Deutsche Grammophon', 'CD-002', 'Music Section B2', '2017-09-15', FALSE),
('National Geographic', 'NG Press', 'MAG-001', 'Magazine Section C1', '2023-11-01', FALSE),
('Scientific American', 'Springer Nature', 'MAG-002', 'Magazine Section C2', '2023-12-01', TRUE),
('Dark Side of the Moon', 'Harvest', 'REC-001', 'Music Section D1', '1973-03-01', FALSE),
('Thriller', 'Epic', 'REC-002', 'Music Section D2', '1982-11-30', FALSE);

-- Insert Books
INSERT INTO Book (mid, isbn, author, hardcover, pages) VALUES
(1, '9780743273565', 'F. Scott Fitzgerald', TRUE, 180),
(2, '9780446310789', 'Harper Lee', FALSE, 281),
(3, '9780451524935', 'George Orwell', TRUE, 328);

-- Insert CDs
INSERT INTO CD (mid, artist, number_cds_in_set, type) VALUES
(4, 'The Beatles', 2, 'music'),
(5, 'Various Artists', 1, 'music');

-- Insert Records
INSERT INTO Record (mid, artist, label, num_lps) VALUES
(8, 'Pink Floyd', 'Harvest Records', 1),
(9, 'Michael Jackson', 'Epic Records', 1);

-- Insert Magazines
INSERT INTO Magazine (mid, issue_num, pages) VALUES
(6, 'Vol. 244 No. 5', 98),
(7, 'Vol. 329 No. 6', 84);

-- Insert Media_Genre relationships
INSERT INTO Media_Genre (mid, genre_name) VALUES
(1, 'Fiction'),
(2, 'Fiction'),
(3, 'Fiction'),
(3, 'Science Fiction'),
(4, 'Music'),
(5, 'Music'),
(6, 'Science'),
(7, 'Science'),
(8, 'Music'),
(9, 'Music');

-- Insert Patrons
INSERT INTO Patron (phone_number, card_number, first_name, last_name, dob, address_street_num, address_street_name, address_city, address_state, address_zip_code) VALUES
('555-0001', 'P001', 'James', 'Wilson', '1980-05-15', '101', 'Pine Street', 'Springfield', 'IL', '62701'),
('555-0002', 'P002', 'Emma', 'Martinez', '1992-09-23', '202', 'Cedar Lane', 'Springfield', 'IL', '62702'),
('555-0003', 'P003', 'Sarah', 'Thompson', '1975-03-30', '303', 'Birch Road', 'Springfield', 'IL', '62701'),
('555-0004', 'P004', 'Daniel', 'Anderson', '1988-11-12', '404', 'Elm Street', 'Springfield', 'IL', '62703'),
('555-0005', 'P005', 'Lisa', 'Garcia', '2000-07-19', '505', 'Oak Drive', 'Springfield', 'IL', '62702');

-- Insert Library_Patron relationships
INSERT INTO Library_Patron (library_id, phone_number) VALUES
(1, '555-0001'),
(1, '555-0002'),
(2, '555-0002'),
(2, '555-0003'),
(3, '555-0004'),
(3, '555-0005');

-- Insert Events
INSERT INTO Event (event_name, date, capacity, children, library_id) VALUES
('Summer Reading Kickoff', '2024-06-01 14:00:00', 100, TRUE, 1),
('Author Meet & Greet', '2024-06-15 18:30:00', 50, FALSE, 2),
('Story Time', '2024-06-08 10:00:00', 30, TRUE, 1),
('Book Club Meeting', '2024-06-20 19:00:00', 20, FALSE, 3),
('Computer Workshop', '2024-06-10 15:00:00', 15, FALSE, 2);

-- Insert Event_Patron relationships
INSERT INTO Event_Patron (event_id, phone_number) VALUES
(1, '555-0001'),
(1, '555-0002'),
(2, '555-0003'),
(3, '555-0004'),
(4, '555-0005');

-- Insert Sellers
INSERT INTO Seller (name, address_street_num, address_street_name, address_city, address_state, address_zip_code) VALUES
('BookWorld Distributors', '789', 'Commerce Ave', 'Chicago', 'IL', '60601'),
('Media Supply Co', '456', 'Industrial Pkwy', 'St. Louis', 'MO', '63101'),
('Library Resources Inc', '123', 'Business Blvd', 'Indianapolis', 'IN', '46201');

-- Insert Library_Seller relationships
INSERT INTO Library_Seller (library_id, seller_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 1),
(3, 3);

-- Insert Borrowing records
INSERT INTO Borrowing (phone_number, mid, librarian_id, checkout_date, due_date, return_date) VALUES
('555-0001', 2, 1, '2023-12-01', '2023-12-15', NULL),
('555-0002', 7, 4, '2023-12-10', '2023-12-24', NULL),
('555-0003', 3, 6, '2023-12-05', '2023-12-19', '2023-12-18'),
('555-0004', 1, 8, '2023-12-08', '2023-12-22', '2023-12-20'),
('555-0005', 4, 9, '2023-12-12', '2023-12-26', '2023-12-24');



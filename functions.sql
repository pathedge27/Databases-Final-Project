-- Functions
USE library_management;

-- Media Operations --

DELIMITER //

-- Add new media

CREATE PROCEDURE AddBook(
    IN p_title VARCHAR(255),
    IN p_publisher VARCHAR(100),
    IN p_reference_number VARCHAR(50),
    IN p_location VARCHAR(100),
    IN p_publication_date DATE,
    IN p_isbn VARCHAR(13),
    IN p_author VARCHAR(255),
    IN p_hardcover BOOLEAN,
    IN p_pages INT
)
BEGIN
    DECLARE new_mid INT;
    
    INSERT INTO Media (title, publisher, reference_number, location, publication_date)
    VALUES (p_title, p_publisher, p_reference_number, p_location, p_publication_date);
    
    SET new_mid = LAST_INSERT_ID();
    
    INSERT INTO Book (mid, isbn, author, hardcover, pages)
    VALUES (new_mid, p_isbn, p_author, p_hardcover, p_pages);
END //

CREATE PROCEDURE AddCD(
    IN p_title VARCHAR(255),
    IN p_publisher VARCHAR(100),
    IN p_reference_number VARCHAR(50),
    IN p_location VARCHAR(100),
    IN p_publication_date DATE,
    IN p_artist VARCHAR(255),
    IN p_number_cds INT,
    IN p_cd_type ENUM('audiobook', 'music')
)
BEGIN
    DECLARE new_mid INT;
    
    INSERT INTO Media (title, publisher, reference_number, location, publication_date)
    VALUES (p_title, p_publisher, p_reference_number, p_location, p_publication_date);
    
    SET new_mid = LAST_INSERT_ID();
    
    INSERT INTO CD (mid, artist, number_cds_in_set, type)
    VALUES (new_mid, p_artist, p_number_cds, p_cd_type);
END //

CREATE PROCEDURE AddRecord(
    IN p_title VARCHAR(255),
    IN p_publisher VARCHAR(100),
    IN p_reference_number VARCHAR(50),
    IN p_location VARCHAR(100),
    IN p_publication_date DATE,
    IN p_artist VARCHAR(255),
    IN p_label VARCHAR(100),
    IN p_num_lps INT
)
BEGIN
    DECLARE new_mid INT;
    
    INSERT INTO Media (title, publisher, reference_number, location, publication_date)
    VALUES (p_title, p_publisher, p_reference_number, p_location, p_publication_date);
    
    SET new_mid = LAST_INSERT_ID();
    
    INSERT INTO Record (mid, artist, label, num_lps)
    VALUES (new_mid, p_artist, p_label, p_num_lps);
END //

CREATE PROCEDURE AddMagazine(
    IN p_title VARCHAR(255),
    IN p_publisher VARCHAR(100),
    IN p_reference_number VARCHAR(50),
    IN p_location VARCHAR(100),
    IN p_publication_date DATE,
    IN p_issue_num VARCHAR(50),
    IN p_pages INT
)
BEGIN
    DECLARE new_mid INT;
    
    INSERT INTO Media (title, publisher, reference_number, location, publication_date)
    VALUES (p_title, p_publisher, p_reference_number, p_location, p_publication_date);
    
    SET new_mid = LAST_INSERT_ID();
    
    INSERT INTO Magazine (mid, issue_num, pages)
    VALUES (new_mid, p_issue_num, p_pages);
END //

-- Remove media
CREATE PROCEDURE RemoveMedia(
    IN p_mid INT
)
BEGIN
    -- First delete from specific media type tables
    DELETE FROM Book WHERE mid = p_mid;
    DELETE FROM CD WHERE mid = p_mid;
    DELETE FROM Record WHERE mid = p_mid;
    DELETE FROM Magazine WHERE mid = p_mid;
    
    -- Then delete from Media_Genre
    DELETE FROM Media_Genre WHERE mid = p_mid;
    
    -- Finally delete from Media
    DELETE FROM Media WHERE mid = p_mid;
END //

-- Get checked out media
CREATE PROCEDURE GetCheckedOutMedia()
BEGIN
    SELECT m.*, b.phone_number, p.first_name, p.last_name, b.due_date
    FROM Media m
    JOIN Borrowing b ON m.mid = b.mid
    JOIN Patron p ON b.phone_number = p.phone_number
    WHERE m.is_checked_out = TRUE AND b.return_date IS NULL;
END //

-- Edit media location
CREATE PROCEDURE UpdateMediaLocation(
    IN p_mid INT,
    IN p_new_location VARCHAR(100)
)
BEGIN
    UPDATE Media 
    SET location = p_new_location
    WHERE mid = p_mid;
END //

-- Event Operations --

-- Get event info with attendees
CREATE PROCEDURE GetEventInfo(
    IN p_event_id INT
)
BEGIN
    SELECT 
        e.event_name,
        e.date,
        e.capacity,
        COUNT(ep.phone_number) as current_attendees,
        GROUP_CONCAT(CONCAT(p.first_name, ' ', p.last_name, ' (', p.phone_number, ')') SEPARATOR '; ') as attendees
    FROM Event e
    LEFT JOIN Event_Patron ep ON e.event_id = ep.event_id
    LEFT JOIN Patron p ON ep.phone_number = p.phone_number
    WHERE e.event_id = p_event_id
    GROUP BY e.event_id;
END //

-- Update event date
CREATE PROCEDURE UpdateEventDate(
    IN p_event_id INT,
    IN p_new_date DATETIME
)
BEGIN
    UPDATE Event 
    SET date = p_new_date
    WHERE event_id = p_event_id;
END //

-- Add new event
CREATE PROCEDURE AddEvent(
    IN p_event_name VARCHAR(255),
    IN p_date DATETIME,
    IN p_capacity INT,
    IN p_children BOOLEAN,
    IN p_library_id INT
)
BEGIN
    INSERT INTO Event (event_name, date, capacity, children, library_id)
    VALUES (p_event_name, p_date, p_capacity, p_children, p_library_id);
END //

-- Remove event
CREATE PROCEDURE RemoveEvent(
    IN p_event_id INT
)
BEGIN
    DELETE FROM Event_Patron WHERE event_id = p_event_id;
    DELETE FROM Event WHERE event_id = p_event_id;
END //

-- Patron Operations --

-- Get all patrons
CREATE PROCEDURE GetPatrons()
BEGIN
    SELECT * FROM Patron;
END //

-- Register new patron
CREATE PROCEDURE RegisterPatron(
    IN p_phone_number VARCHAR(20),
    IN p_card_number VARCHAR(50),
    IN p_first_name VARCHAR(50),
    IN p_last_name VARCHAR(50),
    IN p_dob DATE,
    IN p_street_num VARCHAR(20),
    IN p_street_name VARCHAR(255),
    IN p_city VARCHAR(100),
    IN p_state VARCHAR(50),
    IN p_zip_code VARCHAR(10)
)
BEGIN
    INSERT INTO Patron (
        phone_number, card_number, first_name, last_name, dob,
        address_street_num, address_street_name, address_city, address_state, address_zip_code
    )
    VALUES (
        p_phone_number, p_card_number, p_first_name, p_last_name, p_dob,
        p_street_num, p_street_name, p_city, p_state, p_zip_code
    );
END //

-- Update patron

-- Separated Patron Update functions --

CREATE PROCEDURE UpdatePatronName(
    IN p_phone_number VARCHAR(20),
    IN p_first_name VARCHAR(50),
    IN p_last_name VARCHAR(50)
)
BEGIN
    UPDATE Patron
    SET 
        first_name = p_first_name,
        last_name = p_last_name
    WHERE phone_number = p_phone_number;
END //

CREATE PROCEDURE UpdatePatronPhone(
    IN p_old_phone_number VARCHAR(20),
    IN p_new_phone_number VARCHAR(20)
)
BEGIN
    -- Start transaction to ensure all updates are atomic
    START TRANSACTION;
    
    -- Update phone number in Patron table
    UPDATE Patron 
    SET phone_number = p_new_phone_number 
    WHERE phone_number = p_old_phone_number;
    
    -- Update related tables
    UPDATE Library_Patron 
    SET phone_number = p_new_phone_number 
    WHERE phone_number = p_old_phone_number;
    
    UPDATE Event_Patron 
    SET phone_number = p_new_phone_number 
    WHERE phone_number = p_old_phone_number;
    
    UPDATE Borrowing 
    SET phone_number = p_new_phone_number 
    WHERE phone_number = p_old_phone_number;
    
    COMMIT;
END //

CREATE PROCEDURE UpdatePatronAddress(
    IN p_phone_number VARCHAR(20),
    IN p_street_num VARCHAR(20),
    IN p_street_name VARCHAR(255),
    IN p_city VARCHAR(100),
    IN p_state VARCHAR(50),
    IN p_zip_code VARCHAR(10)
)
BEGIN
    UPDATE Patron
    SET 
        address_street_num = p_street_num,
        address_street_name = p_street_name,
        address_city = p_city,
        address_state = p_state,
        address_zip_code = p_zip_code
    WHERE phone_number = p_phone_number;
END //

CREATE PROCEDURE UpdatePatronDateOfBirth(
    IN p_phone_number VARCHAR(20),
    IN p_dob DATE
)
BEGIN
    UPDATE Patron
    SET dob = p_dob
    WHERE phone_number = p_phone_number;
END //

CREATE PROCEDURE UpdatePatronCardNumber(
    IN p_phone_number VARCHAR(20),
    IN p_new_card_number VARCHAR(50)
)
BEGIN
    UPDATE Patron
    SET card_number = p_new_card_number
    WHERE phone_number = p_phone_number;
END //


-- Remove patron
CREATE PROCEDURE RemovePatron(
    IN p_phone_number VARCHAR(20)
)
BEGIN
    DELETE FROM Library_Patron WHERE phone_number = p_phone_number;
    DELETE FROM Event_Patron WHERE phone_number = p_phone_number;
    DELETE FROM Borrowing WHERE phone_number = p_phone_number;
    DELETE FROM Patron WHERE phone_number = p_phone_number;
END //

-- Register patron for event
CREATE PROCEDURE RegisterForEvent(
    IN p_phone_number VARCHAR(20),
    IN p_event_id INT
)
BEGIN
    DECLARE current_attendees INT;
    DECLARE event_capacity INT;
    
    -- Get current number of attendees and event capacity
    SELECT 
        COUNT(ep.phone_number),
        e.capacity 
    INTO current_attendees, event_capacity
    FROM Event e
    LEFT JOIN Event_Patron ep ON e.event_id = ep.event_id
    WHERE e.event_id = p_event_id
    GROUP BY e.event_id;
    
    -- Check if there's capacity
    IF current_attendees < event_capacity THEN
        INSERT INTO Event_Patron (event_id, phone_number)
        VALUES (p_event_id, p_phone_number);
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Event is at full capacity';
    END IF;
END //

-- Check out media to patron
CREATE PROCEDURE CheckOutMedia(
    IN p_phone_number VARCHAR(20),
    IN p_mid INT,
    IN p_librarian_id INT
)
BEGIN
    DECLARE is_available BOOLEAN;
    
    -- Check if media is available
    SELECT NOT is_checked_out INTO is_available
    FROM Media
    WHERE mid = p_mid;
    
    IF is_available THEN
        -- Set media as checked out
        UPDATE Media 
        SET is_checked_out = TRUE
        WHERE mid = p_mid;
        
        -- Create borrowing record
        INSERT INTO Borrowing (phone_number, mid, librarian_id, checkout_date, due_date)
        VALUES (p_phone_number, p_mid, p_librarian_id, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY));
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Media is currently checked out';
    END IF;
END //

-- Return media to library
CREATE PROCEDURE ReturnMedia(
    IN p_mid INT
)
BEGIN
    -- Update media status
    UPDATE Media 
    SET is_checked_out = FALSE
    WHERE mid = p_mid;
    
    -- Update borrowing record
    UPDATE Borrowing
    SET return_date = CURDATE()
    WHERE mid = p_mid AND return_date IS NULL;
END //

DELIMITER ;
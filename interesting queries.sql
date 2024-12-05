USE library_management;

-- 1. Media Circulation Analysis by Type and Genre
-- Shows popularity of different media types and genres, including checkout rates
SELECT 
    CASE 
        WHEN b.mid IS NOT NULL THEN 'Book'
        WHEN c.mid IS NOT NULL THEN 'CD'
        WHEN r.mid IS NOT NULL THEN 'Record'
        WHEN m.mid IS NOT NULL THEN 'Magazine'
    END AS media_type,
    g.genre_name,
    COUNT(DISTINCT med.mid) as total_items,
    COUNT(DISTINCT bor.mid) as times_borrowed,
    ROUND(COUNT(DISTINCT bor.mid) * 100.0 / COUNT(DISTINCT med.mid), 2) as checkout_rate_percent,
    AVG(DATEDIFF(bor.return_date, bor.checkout_date)) as avg_checkout_duration_days
FROM Media med
LEFT JOIN Book b ON med.mid = b.mid
LEFT JOIN CD c ON med.mid = c.mid
LEFT JOIN Record r ON med.mid = r.mid
LEFT JOIN Magazine m ON med.mid = m.mid
LEFT JOIN Media_Genre mg ON med.mid = mg.mid
LEFT JOIN Genre g ON mg.genre_name = g.genre_name
LEFT JOIN Borrowing bor ON med.mid = bor.mid
GROUP BY media_type, g.genre_name
ORDER BY times_borrowed DESC;

-- 2. Event Popularity and Demographics
-- Analyzes event attendance patterns and demographics of attendees
SELECT 
    e.event_name,
    e.capacity,
    COUNT(DISTINCT ep.phone_number) as total_attendees,
    ROUND(COUNT(DISTINCT ep.phone_number) * 100.0 / e.capacity, 2) as attendance_rate_percent,
    ROUND(AVG(YEAR(CURRENT_DATE) - YEAR(p.dob)), 2) as avg_attendee_age,
    SUM(CASE WHEN YEAR(CURRENT_DATE) - YEAR(p.dob) < 18 THEN 1 ELSE 0 END) as child_attendees,
    SUM(CASE WHEN YEAR(CURRENT_DATE) - YEAR(p.dob) >= 18 THEN 1 ELSE 0 END) as adult_attendees
FROM Event e
LEFT JOIN Event_Patron ep ON e.event_id = ep.event_id
LEFT JOIN Patron p ON ep.phone_number = p.phone_number
GROUP BY e.event_id
ORDER BY attendance_rate_percent DESC;

-- 3. Library Branch Performance Comparison
-- Compares different library branches across various metrics
SELECT 
    l.name as library_name,
    COUNT(DISTINCT lp.phone_number) as registered_patrons,
    COUNT(DISTINCT e.event_id) as total_events,
    COUNT(DISTINCT ep.phone_number) as event_attendees,
    COUNT(DISTINCT b.mid) as total_checkouts,
    ROUND(AVG(CASE WHEN b.return_date > b.due_date THEN 1 ELSE 0 END) * 100, 2) as late_return_rate_percent,
    COUNT(DISTINCT emp.employee_id) as total_staff,
    SUM(CASE WHEN lib.department = 'Childrens' THEN 1 ELSE 0 END) as childrens_librarians
FROM Library l
LEFT JOIN Library_Patron lp ON l.library_id = lp.library_id
LEFT JOIN Event e ON l.library_id = e.library_id
LEFT JOIN Event_Patron ep ON e.event_id = ep.event_id
LEFT JOIN Employee emp ON l.library_id = emp.library_id
LEFT JOIN Librarian lib ON emp.employee_id = lib.employee_id
LEFT JOIN Borrowing b ON lib.employee_id = b.librarian_id
GROUP BY l.library_id;

-- 4. Patron Activity and Preferences Analysis
-- Analyzes patron behavior including checkout patterns and event participation
SELECT 
    p.phone_number,
    CONCAT(p.first_name, ' ', p.last_name) as patron_name,
    COUNT(DISTINCT b.mid) as total_checkouts,
    COUNT(DISTINCT ep.event_id) as events_attended,
    GROUP_CONCAT(DISTINCT g.genre_name) as preferred_genres,
    ROUND(AVG(DATEDIFF(b.return_date, b.checkout_date)), 2) as avg_checkout_duration_days,
    SUM(CASE WHEN b.return_date > b.due_date THEN 1 ELSE 0 END) as late_returns,
    COUNT(DISTINCT l.library_id) as registered_libraries
FROM Patron p
LEFT JOIN Borrowing b ON p.phone_number = b.phone_number
LEFT JOIN Event_Patron ep ON p.phone_number = ep.phone_number
LEFT JOIN Library_Patron lp ON p.phone_number = lp.phone_number
LEFT JOIN Library l ON lp.library_id = l.library_id
LEFT JOIN Media m ON b.mid = m.mid
LEFT JOIN Media_Genre mg ON m.mid = mg.mid
LEFT JOIN Genre g ON mg.genre_name = g.genre_name
GROUP BY p.phone_number
ORDER BY total_checkouts DESC;

-- 5. Librarian Performance Metrics
-- Analyzes librarian performance based on various metrics
SELECT 
    CONCAT(e.first_name, ' ', e.last_name) as librarian_name,
    l.department,
    l.head as is_department_head,
    COUNT(DISTINCT b.mid) as total_checkouts_processed,
    COUNT(DISTINCT p.phone_number) as unique_patrons_served,
    ROUND(AVG(CASE WHEN b.return_date > b.due_date THEN 1 ELSE 0 END) * 100, 2) as late_return_rate_percent,
    lib.name as library_branch
FROM Employee e
JOIN Librarian l ON e.employee_id = l.employee_id
JOIN Library lib ON e.library_id = lib.library_id
LEFT JOIN Borrowing b ON l.employee_id = b.librarian_id
LEFT JOIN Patron p ON b.phone_number = p.phone_number
GROUP BY e.employee_id;
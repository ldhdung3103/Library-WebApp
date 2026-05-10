USE library_management;

-- USERS
ALTER TABLE users
MODIFY COLUMN password VARCHAR(255);

ALTER TABLE users
MODIFY COLUMN status ENUM('active','inactive','suspended') DEFAULT 'active';

ALTER TABLE users
ADD COLUMN phone VARCHAR(20);

ALTER TABLE users
ADD COLUMN address VARCHAR(255);

ALTER TABLE users
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
ON UPDATE CURRENT_TIMESTAMP;

-- BOOKS
ALTER TABLE books
ADD COLUMN publisher VARCHAR(100);

ALTER TABLE books
ADD COLUMN publish_year INT;

ALTER TABLE books
ADD COLUMN status ENUM('available','unavailable')
DEFAULT 'available';

ALTER TABLE books
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE books
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
ON UPDATE CURRENT_TIMESTAMP;

-- BORROW RECORDS
ALTER TABLE borrow_records
MODIFY COLUMN status ENUM(
    'pending',
    'borrowed',
    'returned',
    'rejected',
    'overdue'
) DEFAULT 'pending';

ALTER TABLE borrow_records
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- PENALTIES
ALTER TABLE penalties
ADD COLUMN payment_date DATE NULL;

ALTER TABLE penalties
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- ACTIVITY LOGS
CREATE TABLE IF NOT EXISTS activity_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NULL,
    action VARCHAR(100) NOT NULL,
    details TEXT,
    log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

-- SAMPLE USERS
INSERT IGNORE INTO users
(username,password,full_name,email,role)
VALUES
('manager1','123456','System Manager','manager@library.com','manager'),
('librarian1','123456','Main Librarian','librarian@library.com','librarian'),
('student1','123456','Nguyen Van A','student1@gmail.com','student'),
('student2','123456','Tran Thi B','student2@gmail.com','student');

-- SAMPLE BOOKS
INSERT IGNORE INTO books
(title,author,publisher,publish_year,category,isbn,description,quantity,available_quantity)
VALUES
('Clean Code','Robert C. Martin','Prentice Hall',2008,'Programming',
'9780132350884','Software craftsmanship best practices',10,10),

('Database System Concepts','Abraham Silberschatz','McGraw-Hill',2019,'Database',
'9780073523323','Database concepts',8,8),

('Java Servlet Programming','Jason Hunter','OReilly',2001,'Web Development',
'9780596000400','Servlet and JSP guide',6,6),

('Design Patterns','Erich Gamma','Addison Wesley',1994,'Software Engineering',
'9780201633610','Classic software patterns',5,5),

('Operating System Concepts','Silberschatz','Wiley',2018,'Operating Systems',
'9781118063330','Operating system concepts',7,7);
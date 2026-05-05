DROP DATABASE IF EXISTS library_management;
CREATE DATABASE library_management;
USE library_management;

-- USERS
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('student','librarian','manager') NOT NULL,
    status ENUM('active','inactive') DEFAULT 'active',
    created_by INT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (created_by) REFERENCES users(user_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
);

-- BOOKS
CREATE TABLE books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    isbn VARCHAR(20) UNIQUE,
    description TEXT,
    quantity INT NOT NULL DEFAULT 0,
    available_quantity INT NOT NULL DEFAULT 0
);

-- BORROW RECORDS
CREATE TABLE borrow_records (
    borrow_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    book_id INT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE NULL,
    status ENUM('borrowed','returned','overdue') DEFAULT 'borrowed',

    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,

    FOREIGN KEY (book_id) REFERENCES books(book_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

-- PENALTIES
CREATE TABLE penalties (
    penalty_id INT PRIMARY KEY AUTO_INCREMENT,
    borrow_id INT NOT NULL,
    amount DECIMAL(10 , 2 ) NOT NULL,
    reason VARCHAR(255),
    payment_status ENUM('paid', 'unpaid') DEFAULT 'unpaid',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (borrow_id)
        REFERENCES borrow_records (borrow_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- SAMPLE USERS
INSERT INTO users(username,password,full_name,email,role)
VALUES
('manager1','123456','System Manager','manager@library.com','manager'),
('librarian1','123456','Main Librarian','librarian@library.com','librarian'),
('student1','123456','Nguyen Van A','student1@gmail.com','student'),
('student2','123456','Tran Thi B','student2@gmail.com','student');

-- SAMPLE BOOKS
INSERT INTO books(title,author,category,isbn,description,quantity,available_quantity)
VALUES
('Clean Code','Robert C. Martin','Programming','9780132350884','Software craftsmanship best practices',10,10),

('Database System Concepts','Abraham Silberschatz','Database','9780073523323','Comprehensive database concepts',8,8),

('Java Servlet Programming','Jason Hunter','Web Development','9780596000400','Servlet and JSP programming guide',6,6),

('Design Patterns','Erich Gamma','Software Engineering','9780201633610','Classic software design patterns',5,5),

('Operating System Concepts','Silberschatz','Operating Systems','9781118063330','OS principles and implementation',7,7);
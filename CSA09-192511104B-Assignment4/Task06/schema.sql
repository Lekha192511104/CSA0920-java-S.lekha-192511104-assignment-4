CREATE DATABASE IF NOT EXISTS campus_events;
USE campus_events;

CREATE TABLE IF NOT EXISTS registrations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_reg_no VARCHAR(30) NOT NULL,
    student_name VARCHAR(100) NOT NULL,
    event_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_student_event (student_reg_no, event_name)
);

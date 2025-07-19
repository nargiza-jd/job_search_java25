CREATE TABLE IF NOT EXISTS users
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    surname VARCHAR(100),
    age INT,
    email VARCHAR(150) UNIQUE,
    password VARCHAR(100),
    phone_number VARCHAR(50),
    avatar VARCHAR(255),
    account_type VARCHAR(30)
    );

CREATE TABLE IF NOT EXISTS categories
(
    id INT PRIMARY KEY,
    name VARCHAR(255),
    parent_id INT
    );

CREATE TABLE IF NOT EXISTS resumes
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    applicant_id INT,
    name VARCHAR(255),
    category_id INT,
    salary DOUBLE,
    is_active BOOLEAN,
    created_date TIMESTAMP,
    update_time TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS vacancies
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    description TEXT,
    category_id INT,
    salary DOUBLE,
    exp_from INT,
    exp_to INT,
    is_active BOOLEAN,
    author_id INT,
    created_date TIMESTAMP,
    update_time TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS responded_applicants
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    resume_id INT,
    vacancy_id INT,
    confirmation BOOLEAN
);

INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type)
VALUES
    ('Aibek', 'Isakov', 25, 'aibek@mail.com', 'qwerty', '0500123456', '', 'APPLICANT'),
    ('Bakyt', 'Erkinov', 35, 'bakyt@mail.com', 'qwerty', '0500654321', '', 'EMPLOYER');

INSERT INTO categories (id, name, parent_id)
VALUES
    (1, 'IT', NULL),
    (2, 'Design', NULL);

INSERT INTO resumes (applicant_id, name, category_id, salary, is_active, created_date, update_time)
VALUES
    (1, 'Java Developer Resume', 1, 100000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (1, 'UX Designer Resume', 2, 90000, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time)
VALUES
    ('Senior Java Developer', 'Looking for experienced backend dev', 1, 120000, 3, 5, true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('UI/UX Designer', 'Creative designer for mobile apps', 2, 95000, 2, 4, true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation)
VALUES (1, 1, true);
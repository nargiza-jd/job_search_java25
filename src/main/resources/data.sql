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
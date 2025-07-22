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

INSERT INTO responded_applicants (resume_id, vacancy_id, confirmation, applicant_id)
VALUES (1, 1, true, 1);
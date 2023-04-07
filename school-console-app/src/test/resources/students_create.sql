CREATE SCHEMA IF NOT EXISTS school;

DROP TABLE IF EXISTS school.students;
CREATE TABLE school.students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INTEGER,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

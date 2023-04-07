CREATE SCHEMA IF NOT EXISTS school;

DROP TABLE IF EXISTS school.courses CASCADE;

CREATE TABLE school.courses
(
    course_id SERIAL PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    course_description VARCHAR(255) NOT NULL
);

CREATE SCHEMA IF NOT EXISTS school;

DROP TABLE IF EXISTS school.groups CASCADE;

CREATE TABLE school.groups
(
    group_id SERIAL PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL
);


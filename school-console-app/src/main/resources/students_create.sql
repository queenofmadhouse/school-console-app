DROP TABLE IF EXISTS school.students;
DROP SEQUENCE IF EXISTS school.students_student_id_seq;

CREATE SEQUENCE school.students_student_id_seq;
CREATE TABLE school.students
(
    student_id integer NOT NULL DEFAULT nextval('school.students_student_id_seq'::regclass),
    group_id integer,
    first_name varchar(255),
    last_name varchar(255),
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);
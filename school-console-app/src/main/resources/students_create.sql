DROP SEQUENCE IF EXISTS school.students_student_id_seq CASCADE;

CREATE SEQUENCE school.students_student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

DROP TABLE IF EXISTS school.students;

CREATE TABLE IF NOT EXISTS school.students
(
    student_id integer NOT NULL DEFAULT nextval('school.students_student_id_seq'::regclass),
    group_id integer,
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
    )

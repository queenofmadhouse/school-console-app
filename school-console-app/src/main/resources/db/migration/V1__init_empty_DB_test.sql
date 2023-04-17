CREATE SCHEMA IF NOT EXISTS school;

DROP SEQUENCE IF EXISTS school.students_student_id_seq;

CREATE SEQUENCE school.students_student_id_seq;
CREATE TABLE school.students
(
    student_id bigint NOT NULL DEFAULT nextval('school.students_student_id_seq'::regclass),
    group_id   bigint,
    first_name varchar(255),
    last_name  varchar(255),
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);

DROP SEQUENCE IF EXISTS school.groups_group_id_seq;

CREATE SEQUENCE school.groups_group_id_seq;
CREATE TABLE IF NOT EXISTS school.groups
(
    group_id   bigint NOT NULL DEFAULT nextval('school.groups_group_id_seq'::regclass),
    group_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
);

DROP SEQUENCE IF EXISTS school.courses_course_id_seq;

CREATE SEQUENCE school.courses_course_id_seq;
CREATE TABLE IF NOT EXISTS school.courses
(
    course_id bigint NOT NULL DEFAULT nextval('school.courses_course_id_seq'::regclass),
    course_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    course_description character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS school.students_courses_relation
(
    student_id bigint NOT NULL,
    course_id  bigint NOT NULL
);

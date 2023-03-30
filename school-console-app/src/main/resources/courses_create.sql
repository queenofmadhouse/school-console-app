DROP TABLE IF EXISTS school.courses CASCADE;
DROP SEQUENCE IF EXISTS school.courses_course_id_seq;

CREATE SEQUENCE school.courses_course_id_seq;
CREATE TABLE IF NOT EXISTS school.courses
(
    course_id integer NOT NULL DEFAULT nextval('school.courses_course_id_seq'::regclass),
    course_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    course_description character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
    );

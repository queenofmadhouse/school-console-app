CREATE SEQUENCE school.courses_course_id_seq;
CREATE TABLE IF NOT EXISTS school.courses
(
    course_id bigint NOT NULL DEFAULT nextval('school.courses_course_id_seq'),
    course_name character varying(255) NOT NULL,
    course_description character varying(255),
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);

DROP SCHEMA IF EXISTS school CASCADE;

CREATE SCHEMA school;

CREATE SEQUENCE school.users_user_id_seq;
CREATE TABLE school.users
(
   user_id bigint NOT NULL DEFAULT nextval('school.users_user_id_seq'::regclass),
   first_name varchar(255),
   last_name  varchar(255),
   CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

CREATE TABLE school.students
(
   user_id bigint NOT NULL,
   group_id bigint,
   CONSTRAINT students_pkey PRIMARY KEY (user_id),
   CONSTRAINT students_user_id_fkey FOREIGN KEY (user_id) REFERENCES school.users (user_id) ON DELETE CASCADE
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
   user_id bigint NOT NULL,
   course_id  bigint NOT NULL
);

CREATE TABLE school.students
(
    user_id bigint NOT NULL,
    group_id bigint,
    CONSTRAINT students_pkey PRIMARY KEY (user_id),
    CONSTRAINT students_user_id_fkey FOREIGN KEY (user_id) REFERENCES school.users (user_id) ON DELETE CASCADE
);

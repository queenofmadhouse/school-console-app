CREATE SEQUENCE school.users_user_id_seq;
CREATE TABLE school.users
(
    user_id bigint NOT NULL DEFAULT nextval('school.users_user_id_seq'),
    first_name varchar(255),
    last_name  varchar(255),
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

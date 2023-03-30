DROP TABLE IF EXISTS school.groups CASCADE;
DROP SEQUENCE IF EXISTS school.groups_group_id_seq;

CREATE SEQUENCE school.groups_group_id_seq;
CREATE TABLE IF NOT EXISTS school.groups
(
    group_id integer NOT NULL DEFAULT nextval('school.groups_group_id_seq'::regclass),
    group_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
    );

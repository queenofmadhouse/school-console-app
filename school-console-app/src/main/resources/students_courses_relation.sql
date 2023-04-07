DROP TABLE IF EXISTS school.students_courses_relation;

CREATE TABLE IF NOT EXISTS school.students_courses_relation
(
    student_id integer NOT NULL,
    course_id  integer NOT NULL
)
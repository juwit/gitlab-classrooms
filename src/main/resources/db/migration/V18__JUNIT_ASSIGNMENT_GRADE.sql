create table junit_assignment_grade
(
    id   uuid primary key not null,
    type character varying(255)
);


create table junit_assignment_grade_test_suites
(
    junit_assignment_grade_id uuid    not null
        constraint fk_junit_assignment_grade references junit_assignment_grade,
    name                      character varying(255),
    tests                     integer not null,
    errors                    integer not null,
    failures                  integer not null,
    skipped                   integer not null
);


create table student_exercise_assignment_assignment_grades
(
    student_exercise_assignment_id uuid not null
        constraint fk_student_exercise_assignment references student_exercise_assignment,
    assignment_grades_id           uuid not null,
    primary key (student_exercise_assignment_id, assignment_grades_id)
);


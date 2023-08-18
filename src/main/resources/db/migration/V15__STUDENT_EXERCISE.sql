create table student_exercise
(
    id                 uuid primary key not null,
    gitlab_project_id  bigint,
    gitlab_project_url character varying(255),
    max_score          bigint,
    score              bigint,
    submission_date    timestamp(6) with time zone,
    assignment_id      uuid
        constraint fk_exercise_assignment references exercise_assignment,
    student_name       character varying(255)
        constraint fk_classroom_user references classroom_user
);

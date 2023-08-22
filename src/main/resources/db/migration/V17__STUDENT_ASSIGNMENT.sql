drop table quiz_score;

drop table student_exercise;

create table student_assignment
(
    id              uuid not null primary key,
    max_score       bigint,
    score           bigint,
    submission_date timestamp(6) with time zone,
    assignment_id   uuid
        constraint fk_assignment references assignment,
    student_name    varchar(255)
        constraint fk_classroom_user references classroom_user
);

create table student_exercise_assignment
(
    id                 uuid not null primary key
        constraint fk_student_assignment
            references student_assignment,
    gitlab_project_id  bigint,
    gitlab_project_url varchar(255)
);

create table public.student_quiz_assignment
(
    id uuid not null primary key
        constraint fk_student_assignment
            references student_assignment
);


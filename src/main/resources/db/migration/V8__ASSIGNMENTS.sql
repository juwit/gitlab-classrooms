create table classroom
(
    id         uuid not null primary key,
    gitlab_url varchar(255),
    name       varchar(255)
);

create table classroom_students
(
    classroom_id  uuid         not null
        constraint fk_classroom references classroom,
    students_name varchar(255) not null
        constraint fk_classroom_user references classroom_user,
    primary key (classroom_id, students_name)
);

create table assignment
(
    id   uuid not null primary key,
    name varchar(255),
    type varchar(255)
        constraint type_check
            check ((type)::text = ANY ((ARRAY ['QUIZ'::character varying, 'EXERCICE'::character varying])::text[]))
);

create table quiz_assignment
(
    id        uuid not null
        primary key
        constraint fk_assignment references assignment,
    quiz_name varchar(255)
        constraint unique_quiz_name unique
        constraint fk_quiz references quiz
);

create table classroom_assignments
(
    classroom_id   uuid not null
        constraint fk_classroom references classroom,
    assignments_id uuid not null
        constraint unique_assignments_id unique
        constraint fk_assignment references assignment
);

create table assignment_students
(
    assignment_id uuid         not null
        constraint fk_assignments references assignment,
    students_name varchar(255) not null
        constraint fk_classroom_user references classroom_user,
    primary key (assignment_id, students_name)
);

alter table student_exercise_assignment
    add column max_score bigint;
alter table student_exercise_assignment
    add column score bigint;
alter table student_exercise_assignment
    add column submission_date timestamp(6) with time zone;
alter table student_exercise_assignment
    add column assignment_id uuid;
alter table student_exercise_assignment
    add column student_name varchar(255);
alter table student_exercise_assignment
    add constraint fk_assignment foreign key (assignment_id) references assignment;
alter table student_exercise_assignment
    add constraint fk_classroom_user foreign key (student_name) references classroom_user;
alter table student_exercise_assignment
    drop constraint fk_student_assignment;

alter table student_quiz_assignment
    add column max_score bigint;
alter table student_quiz_assignment
    add column score bigint;
alter table student_quiz_assignment
    add column submission_date timestamp(6) with time zone;
alter table student_quiz_assignment
    add column assignment_id uuid;
alter table student_quiz_assignment
    add column student_name varchar(255);
alter table student_quiz_assignment
    add constraint fk_assignment foreign key (assignment_id) references assignment;
alter table student_quiz_assignment
    add constraint fk_classroom_user foreign key (student_name) references classroom_user;
alter table student_quiz_assignment
    drop constraint fk_student_assignment;

drop table student_assignment;

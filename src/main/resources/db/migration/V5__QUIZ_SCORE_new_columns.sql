alter table quiz_score
    alter column score type bigint;

alter table quiz_score
    add column max_score bigint;

alter table quiz_score
    add column submission_count int;

alter table quiz_score
    add column submission_date timestamp with time zone;

alter table quiz_score
    drop column submitted_at;

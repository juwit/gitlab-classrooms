create table exercise_assignment
(
    id                            uuid not null
        primary key
        constraint fk_assignment references assignment,
    gitlab_repository_template_id varchar(255)
);

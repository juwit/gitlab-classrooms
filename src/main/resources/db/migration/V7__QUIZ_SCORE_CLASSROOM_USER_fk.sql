drop table public.quiz_score;

create table public.quiz_score
(
    quiz_id             varchar(255) not null,
    classroom_user_name varchar(255) not null
        constraint quiz_score_classroom_user_name_fk
            references public.classroom_user,
    max_score           bigint       not null,
    score               bigint       not null,
    submission_count    integer      not null,
    submission_date     timestamp(6) with time zone,
    primary key (quiz_id, classroom_user_name)
);

create table public.classroom_user
(
    name  character varying(255) primary key not null,
    roles character varying(255)[]
);


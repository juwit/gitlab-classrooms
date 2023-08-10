alter table assignment
    drop constraint type_check;

alter table assignment
    add constraint check_assignment_type
        check ((type)::text = ANY (ARRAY [('QUIZ'::character varying)::text, ('EXERCISE'::character varying)::text]));


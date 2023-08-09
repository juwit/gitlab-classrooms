alter table assignment
    add classroom_id uuid;

alter table assignment
    add constraint fk_classroom foreign key (classroom_id) references public.classroom;

drop table classroom_assignments;

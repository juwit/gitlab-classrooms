alter table classroom
    add teacher_name varchar(255);

alter table classroom
    add constraint fk_classroom_user
        foreign key (teacher_name) references classroom_user;

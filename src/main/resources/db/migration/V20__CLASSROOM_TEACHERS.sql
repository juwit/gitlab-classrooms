CREATE TABLE classroom_teachers
(
    classroom_id  UUID         NOT NULL,
    teachers_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_classroom_teachers PRIMARY KEY (classroom_id, teachers_name)
);

ALTER TABLE classroom_teachers
    ADD CONSTRAINT fk_classroom FOREIGN KEY (classroom_id) REFERENCES classroom (id);

ALTER TABLE classroom_teachers
    ADD CONSTRAINT fk_classroom_user FOREIGN KEY (teachers_name) REFERENCES classroom_user (name);

-- migrate data to new table
INSERT INTO classroom_teachers (classroom_id, teachers_name)
    SELECT id, teacher_name FROM classroom;

ALTER TABLE classroom
    DROP CONSTRAINT fk_classroom_user;

ALTER TABLE classroom
    DROP COLUMN teacher_name;

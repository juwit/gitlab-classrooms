ALTER TABLE assignment
    ADD column if not exists auto_archive bool;

ALTER TABLE assignment
    ADD column if not exists due_date TIMESTAMP WITH TIME ZONE;
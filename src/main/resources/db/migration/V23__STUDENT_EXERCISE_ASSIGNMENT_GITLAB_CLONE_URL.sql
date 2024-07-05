ALTER TABLE student_exercise_assignment
    ADD column if not exists gitlab_clone_url VARCHAR(255);

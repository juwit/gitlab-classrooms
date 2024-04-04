ALTER TABLE quiz_assignment
    ADD max_retakes INTEGER;

UPDATE quiz_assignment
SET max_retakes = '0'
WHERE max_retakes IS NULL;
ALTER TABLE quiz_assignment
    ALTER COLUMN max_retakes SET NOT NULL;

ALTER TABLE student_quiz_assignment
    ADD retakes INTEGER;

UPDATE student_quiz_assignment
SET retakes = '0'
WHERE retakes IS NULL;
ALTER TABLE student_quiz_assignment
    ALTER COLUMN retakes SET NOT NULL;
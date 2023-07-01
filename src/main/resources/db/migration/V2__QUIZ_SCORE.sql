CREATE TABLE quiz_score
(
    quiz_id      varchar(100)                        NOT NULL,
    student_id   varchar(200)                        NOT NULL,
    score        INTEGER                             NOT NULL,
    submitted_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (quiz_id, student_id)
);

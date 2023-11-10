package fr.univ_lille.gitlab.classrooms.assignments;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentQuizAssignmentTest {

    @Test
    void resetGrade_shouldResetTheScore(){
        var studentQuizAssignment = new StudentQuizAssignment();
        studentQuizAssignment.setScore(12L);
        studentQuizAssignment.setMaxScore(42L);

        studentQuizAssignment.resetGrades();

        assertThat(studentQuizAssignment.getScore()).isNull();
        assertThat(studentQuizAssignment.getMaxScore()).isNull();
    }

    @Test
    void resetGrade_shouldSetTheAssignmentUnSubmitted(){
        var studentQuizAssignment = new StudentQuizAssignment();

        studentQuizAssignment.setSubmissionDate(ZonedDateTime.now());

        studentQuizAssignment.resetGrades();

        assertThat(studentQuizAssignment.hasBeenSubmitted()).isFalse();
    }
}

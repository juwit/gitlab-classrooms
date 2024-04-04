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

    @Test
    void quizCanBeRetaken_ifAssignmentAllows(){
        var assignment = new QuizAssignment();
        assignment.setMaxRetakes(1);

        // assignment already taken, with no retakes
        var studentQuizAssignment = new StudentQuizAssignment();
        studentQuizAssignment.setAssignment(assignment);
        studentQuizAssignment.setSubmissionDate(ZonedDateTime.now());
        studentQuizAssignment.setRetakes(0);

        assertThat(studentQuizAssignment.canRetake()).isTrue();
    }

    @Test
    void quizCannotBeRetaken_isAssignmentDisallows(){
        var assignment = new QuizAssignment();
        assignment.setMaxRetakes(0);

        // assignment already taken, with no retakes
        var studentQuizAssignment = new StudentQuizAssignment();
        studentQuizAssignment.setAssignment(assignment);
        studentQuizAssignment.setSubmissionDate(ZonedDateTime.now());
        studentQuizAssignment.setRetakes(0);

        assertThat(studentQuizAssignment.canRetake()).isFalse();
    }

    @Test
    void quizCanBeRetaken_atMostXTimes(){
        var assignment = new QuizAssignment();
        assignment.setMaxRetakes(3);

        // assignment already taken, with no retakes
        var studentQuizAssignment = new StudentQuizAssignment();
        studentQuizAssignment.setAssignment(assignment);
        studentQuizAssignment.setSubmissionDate(ZonedDateTime.now());

        studentQuizAssignment.setRetakes(2);
        assertThat(studentQuizAssignment.canRetake()).isTrue();

        studentQuizAssignment.setRetakes(3);
        assertThat(studentQuizAssignment.canRetake()).isFalse();
    }
}

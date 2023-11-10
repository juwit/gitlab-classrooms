package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.assignments.grading.junit.JUnitAssignmentGrade;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentExerciseAssignmentTest {

    @Test
    void shouldHaveOnlyOneGradeOfEachType() {
        var studentExerciseAssignment = new StudentExerciseAssignment();

        var junitGrade = new JUnitAssignmentGrade();

        var anotherJunitGrade = new JUnitAssignmentGrade();

        studentExerciseAssignment.getAssignmentGrades().add(junitGrade);
        studentExerciseAssignment.getAssignmentGrades().add(anotherJunitGrade);

        assertThat(studentExerciseAssignment.getAssignmentGrades()).hasSize(1);
    }

    @Test
    void resetGrade_shouldRemoveAllTheGrades(){
        var studentExerciseAssignment = new StudentExerciseAssignment();

        var junitGrade = new JUnitAssignmentGrade();
        studentExerciseAssignment.getAssignmentGrades().add(junitGrade);

        studentExerciseAssignment.resetGrades();

        assertThat(studentExerciseAssignment.getAssignmentGrades()).isEmpty();
    }

    @Test
    void resetGrade_shouldSetTheAssignmentUnSubmitted(){
        var studentExerciseAssignment = new StudentExerciseAssignment();

        studentExerciseAssignment.setSubmissionDate(ZonedDateTime.now());

        studentExerciseAssignment.resetGrades();

        assertThat(studentExerciseAssignment.hasBeenSubmitted()).isFalse();
    }
}

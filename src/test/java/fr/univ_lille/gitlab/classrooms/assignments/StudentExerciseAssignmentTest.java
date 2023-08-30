package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.assignments.grading.junit.JUnitAssignmentGrade;
import org.junit.jupiter.api.Test;

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
}

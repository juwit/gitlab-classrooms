package fr.univ_lille.gitlab.classrooms.assignments.grading.junit;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class JUnitAssignmentGradeTest {

    @Test
    void maxScoreShouldBeTheSumOfSuiteMaxScores() {
        var junitAssignmentGrade = new JUnitAssignmentGrade();

        var firstJunitSuite = new JUnitTestSuite();
        firstJunitSuite.setName("First");
        firstJunitSuite.setTests(10);

        var secondJunitSuite = new JUnitTestSuite();
        secondJunitSuite.setName("Second");
        secondJunitSuite.setTests(5);

        junitAssignmentGrade.setTestSuites(Set.of(firstJunitSuite, secondJunitSuite));

        assertThat(junitAssignmentGrade.getMaxScore()).isEqualTo(15);
    }

    @Test
    void scoreShouldBeTheSumOfSuitescores() {
        var junitAssignmentGrade = new JUnitAssignmentGrade();

        var firstJunitSuite = new JUnitTestSuite();
        firstJunitSuite.setName("First");
        firstJunitSuite.setTests(10);
        firstJunitSuite.setFailures(3);

        var secondJunitSuite = new JUnitTestSuite();
        secondJunitSuite.setName("Second");
        secondJunitSuite.setTests(5);

        junitAssignmentGrade.setTestSuites(Set.of(firstJunitSuite, secondJunitSuite));

        assertThat(junitAssignmentGrade.getScore()).isEqualTo(12);
    }
}

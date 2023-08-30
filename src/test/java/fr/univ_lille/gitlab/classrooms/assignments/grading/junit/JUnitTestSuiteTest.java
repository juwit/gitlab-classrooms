package fr.univ_lille.gitlab.classrooms.assignments.grading.junit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class JUnitTestSuiteTest {

    @Test
    void maxScore_shouldBeTestCount() {
        var testSuite = new JUnitTestSuite();
        testSuite.setTests(10);

        assertThat(testSuite.getMaxScore()).isEqualTo(10);
    }

    @Test
    void score_shouldBeTestSuccess() {
        var testSuite = new JUnitTestSuite();
        testSuite.setTests(10);
        testSuite.setErrors(1);
        testSuite.setFailures(1);
        testSuite.setSkipped(2);

        assertThat(testSuite.getScore()).isEqualTo(6);
    }

    @Test
    void suites_shouldBeEqual_whenHavingTheSameName(){
        var testSuite1 = new JUnitTestSuite();
        testSuite1.setName("Suite 1");

        var testSuite2 = new JUnitTestSuite();
        testSuite2.setName("Suite 2");

        var testSuite3 = new JUnitTestSuite();
        testSuite3.setName("Suite 1");

        assertThat(testSuite1)
                .isNotEqualTo(testSuite2)
                .isEqualTo(testSuite3)
                .doesNotHaveSameHashCodeAs(testSuite2)
                .hasSameHashCodeAs(testSuite3);
    }
}

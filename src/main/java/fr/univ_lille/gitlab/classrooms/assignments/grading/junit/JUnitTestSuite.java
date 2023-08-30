package fr.univ_lille.gitlab.classrooms.assignments.grading.junit;

import fr.univ_lille.gitlab.classrooms.assignments.grading.junit.reports.TestSuite;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class JUnitTestSuite {

    private String name;

    private int tests;

    private int failures;

    private int errors;

    private int skipped;

    /**
     * Factory method to build this entity from a TestSuite report parsed from XML
     *
     * @param testSuiteReport
     * @return
     */
    public static JUnitTestSuite fromTestSuiteReport(TestSuite testSuiteReport) {
        var junitTestSuite = new JUnitTestSuite();
        junitTestSuite.name = testSuiteReport.getName();
        if (testSuiteReport.getTests() != null) {
            junitTestSuite.tests = Integer.parseInt(testSuiteReport.getTests());
        }
        if (testSuiteReport.getFailures() != null) {
            junitTestSuite.failures = Integer.parseInt(testSuiteReport.getFailures());
        }
        if (testSuiteReport.getErrors() != null) {
            junitTestSuite.errors = Integer.parseInt(testSuiteReport.getErrors());
        }
        if (testSuiteReport.getSkipped() != null) {
            junitTestSuite.skipped = Integer.parseInt(testSuiteReport.getSkipped());
        }
        return junitTestSuite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    public int getFailures() {
        return failures;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }

    public int getScore() {
        return this.tests - this.failures - this.errors - this.skipped;
    }

    public int getMaxScore() {
        return this.tests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JUnitTestSuite that = (JUnitTestSuite) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

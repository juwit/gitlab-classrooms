package fr.univ_lille.gitlab.classrooms.assignments.grading;

import fr.univ_lille.gitlab.classrooms.assignments.StudentExerciseAssignment;
import fr.univ_lille.gitlab.classrooms.assignments.grading.junit.JUnitAssignmentGrade;
import fr.univ_lille.gitlab.classrooms.assignments.grading.junit.JUnitTestSuite;
import fr.univ_lille.gitlab.classrooms.assignments.grading.junit.reports.TestReportParser;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.ZonedDateTime;

@Service
class AssignmentGradeServiceImpl implements AssignmentGradeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssignmentGradeServiceImpl.class.getName());

    private final TestReportParser testReportParser;

    AssignmentGradeServiceImpl(TestReportParser testReportParser) {
        this.testReportParser = testReportParser;
    }

    @Override
    public void gradeAssignmentWithJUnitReport(StudentExerciseAssignment studentExerciseAssignment, InputStream inputStream) throws AssignmentGradingException {
        // parse test suite
        try {
            var testSuiteReport = testReportParser.parseTestReport(inputStream);
            var junitTestSuite = JUnitTestSuite.fromTestSuiteReport(testSuiteReport);

            // get junit assignment grade, or create it
            var assignmentGrade = studentExerciseAssignment.getAssignmentGrades().stream()
                    .filter(it -> it.getType() == AssignmentGradeType.JUNIT)
                    .findFirst()
                    .orElse(new JUnitAssignmentGrade());

            // add parsed suite to current grade
            var jUnitAssignmentGrade = ((JUnitAssignmentGrade) assignmentGrade);
            // overwrite existing test suite
            jUnitAssignmentGrade.getTestSuites().remove(junitTestSuite);
            jUnitAssignmentGrade.getTestSuites().add(junitTestSuite);

            studentExerciseAssignment.getAssignmentGrades().add(assignmentGrade);

            // update the submission date
            studentExerciseAssignment.setSubmissionDate(ZonedDateTime.now());
        } catch (JAXBException e) {
            LOGGER.error("Could not parse JUnit Report", e);
            throw new AssignmentGradingException("Could not parse JUnit Report", e);
        }
    }
}

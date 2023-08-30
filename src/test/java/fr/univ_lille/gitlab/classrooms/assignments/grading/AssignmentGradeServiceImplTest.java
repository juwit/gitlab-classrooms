package fr.univ_lille.gitlab.classrooms.assignments.grading;

import fr.univ_lille.gitlab.classrooms.assignments.StudentExerciseAssignment;
import fr.univ_lille.gitlab.classrooms.assignments.grading.junit.JUnitAssignmentGrade;
import fr.univ_lille.gitlab.classrooms.assignments.grading.junit.reports.TestReportParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AssignmentGradeServiceImplTest {

    @InjectMocks
    private AssignmentGradeServiceImpl assignmentGradeService;

    @Spy
    private TestReportParser testReportParser = new TestReportParser();

    @Test
    void testGradeAssignmentWithJUnitReport() throws IOException {
        var studentExerciseAssignment = new StudentExerciseAssignment();

        var reportInputStream = new ClassPathResource("/junit-reports/TEST-test.dao.CatalogDaoTest.xml").getInputStream();

        assignmentGradeService.gradeAssignmentWithJUnitReport(studentExerciseAssignment, reportInputStream);

        assertThat(studentExerciseAssignment.getAssignmentGrades())
                .isNotEmpty()
                .hasSize(1);

        var assignmentGrade = studentExerciseAssignment.getAssignmentGrades().stream().findFirst().get();
        assertThat(assignmentGrade)
                .isNotNull()
                .isInstanceOf(JUnitAssignmentGrade.class);
        var junitAssignmentGrade = ((JUnitAssignmentGrade) assignmentGrade);

        assertThat(junitAssignmentGrade.getTestSuites())
                .isNotEmpty()
                .hasSize(1);

        var testSuite = junitAssignmentGrade.getTestSuites().stream().findFirst().get();
        assertThat(testSuite.getName()).isEqualTo("test.dao.CatalogDaoTest");
        assertThat(testSuite.getTests()).isEqualTo(10);
        assertThat(testSuite.getFailures()).isZero();
        assertThat(testSuite.getErrors()).isZero();
        assertThat(testSuite.getSkipped()).isZero();

        assertThat(studentExerciseAssignment.getSubmissionDate()).isEqualToIgnoringSeconds(ZonedDateTime.now());
        assertThat(studentExerciseAssignment.getScore()).isEqualTo(10);
        assertThat(studentExerciseAssignment.getMaxScore()).isEqualTo(10);
    }

    @Test
    void testGradeAssignmentWithMultipleJUnitReports() throws IOException {
        var studentExerciseAssignment = new StudentExerciseAssignment();

        var firstReportInputStream = new ClassPathResource("/junit-reports/TEST-test.dao.CatalogDaoTest.xml").getInputStream();
        var secondReportInputStream = new ClassPathResource("/junit-reports/TEST-test.model.ModelAttributesTest.xml").getInputStream();

        assignmentGradeService.gradeAssignmentWithJUnitReport(studentExerciseAssignment, firstReportInputStream);
        assignmentGradeService.gradeAssignmentWithJUnitReport(studentExerciseAssignment, secondReportInputStream);

        assertThat(studentExerciseAssignment.getSubmissionDate()).isEqualToIgnoringSeconds(ZonedDateTime.now());
        assertThat(studentExerciseAssignment.getScore()).isEqualTo(18);
        assertThat(studentExerciseAssignment.getMaxScore()).isEqualTo(18);
    }

    @Test
    void testGradeAssignmentWithJUnitCucumberReport() throws IOException {
        var studentExerciseAssignment = new StudentExerciseAssignment();

        var reportInputStream = new ClassPathResource("/cucumber-junit-output.xml").getInputStream();

        assignmentGradeService.gradeAssignmentWithJUnitReport(studentExerciseAssignment, reportInputStream);

        assertThat(studentExerciseAssignment.getAssignmentGrades())
                .isNotEmpty()
                .hasSize(1);

        var assignmentGrade = studentExerciseAssignment.getAssignmentGrades().stream().findFirst().get();
        assertThat(assignmentGrade)
                .isNotNull()
                .isInstanceOf(JUnitAssignmentGrade.class);
        var junitAssignmentGrade = ((JUnitAssignmentGrade) assignmentGrade);

        assertThat(junitAssignmentGrade.getTestSuites())
                .isNotEmpty()
                .hasSize(1);

        var testSuite = junitAssignmentGrade.getTestSuites().stream().findFirst().get();
        assertThat(testSuite.getName()).isEqualTo("Cucumber");
        assertThat(testSuite.getTests()).isEqualTo(2);
        assertThat(testSuite.getFailures()).isOne();
        assertThat(testSuite.getErrors()).isZero();
        assertThat(testSuite.getSkipped()).isZero();

        assertThat(studentExerciseAssignment.getSubmissionDate()).isEqualToIgnoringSeconds(ZonedDateTime.now());
        assertThat(studentExerciseAssignment.getScore()).isEqualTo(1);
        assertThat(studentExerciseAssignment.getMaxScore()).isEqualTo(2);
    }
}

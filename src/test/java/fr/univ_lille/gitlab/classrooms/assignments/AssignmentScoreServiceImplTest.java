package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentScoreServiceImplTest {

    @InjectMocks
    private AssignmentScoreServiceImpl assignmentScoreService;

    @Mock
    private StudentAssignmentRepository studentAssignmentRepository;

    @Test
    void registerScore_shouldUpdateStudentAssignment() {
        var assignment = new QuizAssignment();
        var student = new ClassroomUser();
        var studentAssignment = new StudentQuizAssignment();

        when(this.studentAssignmentRepository.findByAssignmentAndStudent(assignment, student)).thenReturn(studentAssignment);

        this.assignmentScoreService.registerScore(assignment, student, 12, 20);

        assertThat(studentAssignment.getScore()).isEqualTo(12);
        assertThat(studentAssignment.getMaxScore()).isEqualTo(20);
        assertThat(studentAssignment.getSubmissionDate()).isEqualToIgnoringSeconds(ZonedDateTime.now());

        verify(this.studentAssignmentRepository).findByAssignmentAndStudent(assignment, student);
        verify(this.studentAssignmentRepository).save(studentAssignment);
    }
}


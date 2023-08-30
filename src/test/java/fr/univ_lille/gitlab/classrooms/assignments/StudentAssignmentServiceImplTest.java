package fr.univ_lille.gitlab.classrooms.assignments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentAssignmentServiceImplTest {

    @InjectMocks
    private StudentAssignmentServiceImpl studentAssignmentService;

    @Mock
    private StudentExerciceAssignmentRepository studentExerciceAssignmentRepository;

    @Test
    void getByGitlabProjectId() {
        this.studentAssignmentService.getByGitlabProjectId(12);

        verify(this.studentExerciceAssignmentRepository).findByGitlabProjectId(12L);
    }

    @Test
    void save() {
        var exercise = new StudentExerciseAssignment();
        this.studentAssignmentService.save(exercise);

        verify(this.studentExerciceAssignmentRepository).save(exercise);
    }
}

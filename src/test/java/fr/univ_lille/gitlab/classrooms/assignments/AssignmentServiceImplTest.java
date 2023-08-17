package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceImplTest {

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Test
    void acceptAssignment_shouldAssociateTheStudentWithTheAssignment_andSave(){
        var assignment = new ExerciseAssignment();

        var student = new ClassroomUser();

        this.assignmentService.acceptAssigment(assignment, student);

        assertThat(assignment.getStudents()).contains(student);

        verify(assignmentRepository).save(assignment);
    }

}

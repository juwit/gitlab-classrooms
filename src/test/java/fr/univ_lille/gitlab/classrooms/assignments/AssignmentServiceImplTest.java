package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomService;
import fr.univ_lille.gitlab.classrooms.quiz.QuizEntity;
import fr.univ_lille.gitlab.classrooms.quiz.QuizService;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.GroupParams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceImplTest {

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private ClassroomService classroomService;

    @Mock
    private QuizService quizService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private GitLabApi gitLabApi;

    @Test
    void acceptAssignment_shouldAssociateTheStudentWithTheAssignment_andSave(){
        var assignment = new ExerciseAssignment();

        var student = new ClassroomUser();

        this.assignmentService.acceptAssigment(assignment, student);

        assertThat(assignment.getStudents()).contains(student);

        verify(assignmentRepository).save(assignment);
    }

    @Test
    void createQuizAssignment_shouldAssociateTheAssignmentWithTheQuiz_andSaveTheClassroom(){
        var quiz = new QuizEntity();
        when(quizService.getQuiz("Test Quiz")).thenReturn(Optional.of(quiz));

        var classroom = new Classroom();
        var assignment = this.assignmentService.createQuizAssignment(classroom, "Test Quiz Assignment", "Test Quiz");

        assertThat(assignment)
                .isNotNull()
                .isInstanceOf(QuizAssignment.class);
        assertThat(assignment.getName()).isEqualTo("Test Quiz Assignment");

        var quizAssignment = ((QuizAssignment) assignment);
        assertThat(quizAssignment.getQuiz()).isEqualTo(quiz);

        assertThat(classroom.getAssignments()).contains(assignment);

        verify(assignmentRepository).save(assignment);
        verify(classroomService).saveClassroom(classroom);
    }

    @Test
    void createExerciseAssignment_shouldCreateAGitlabGroup_andSaveTheClassroom() throws GitLabApiException {
        var group = new Group().withId(12L);
        when(gitLabApi.getGroupApi().createGroup(any())).thenReturn(group);

        var classroom = new Classroom();
        classroom.setGitlabGroupId(9L);
        var assignment = this.assignmentService.createExerciseAssignment(classroom, "Test Exercise Assignment", null);

        assertThat(assignment)
                .isNotNull()
                .isInstanceOf(ExerciseAssignment.class);
        assertThat(assignment.getName()).isEqualTo("Test Exercise Assignment");

        var exerciseAssignment = ((ExerciseAssignment) assignment);
        assertThat(exerciseAssignment.getGitlabGroupId()).isEqualTo(12);

        assertThat(classroom.getAssignments()).contains(assignment);

        var captor = ArgumentCaptor.forClass(GroupParams.class);
        verify(gitLabApi.getGroupApi()).createGroup(captor.capture());
        var groupParams = captor.getValue();
        assertThat(groupParams)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "Test Exercise Assignment")
                .hasFieldOrPropertyWithValue("path", "Test_Exercise_Assignment")
                .hasFieldOrPropertyWithValue("parentId", 9L);

        verify(assignmentRepository).save(assignment);
        verify(classroomService).saveClassroom(classroom);
    }

}
package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.classrooms.Classroom;
import fr.univ_lille.gitlab.classrooms.classrooms.ClassroomService;
import fr.univ_lille.gitlab.classrooms.gitlab.Gitlab;
import fr.univ_lille.gitlab.classrooms.quiz.QuizEntity;
import fr.univ_lille.gitlab.classrooms.quiz.QuizService;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
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

    @Mock
    private Gitlab gitlab;

    @Mock
    private StudentAssignmentRepository studentExerciseRepository;

    @Test
    void acceptAssignment_shouldAssociateTheStudentWithTheAssignment_andSave() throws GitLabApiException {
        var assignment = new QuizAssignment();

        var student = new ClassroomUser();

        this.assignmentService.acceptAssigment(assignment, student);

        assertThat(assignment.getStudents()).contains(student);

        verify(assignmentRepository).save(assignment);
    }

    @Test
    void acceptExerciceAssignment_shouldCreateAGitlabProject() throws GitLabApiException {
        var teacher = new ClassroomUser();

        var classroom = new Classroom();
        classroom.setTeacher(teacher);

        var assignment = new ExerciseAssignment();
        assignment.setName("Exercice 1");
        assignment.setGitlabGroupId(12L);
        classroom.addAssignment(assignment);

        var student = new ClassroomUser();
        student.setName("luke.skywalker");

        var project = new Project();
        project.setId(25L);
        project.setWebUrl("https://gitlab.univ-lille.fr/gitlab-classroom");
        when(gitlab.createProject(assignment, student)).thenReturn(project);

        this.assignmentService.acceptAssigment(assignment, student);

        verify(gitlab).createProject(assignment, student);

        var studentExerciseCaptor = ArgumentCaptor.forClass(StudentExerciseAssignment.class);
        verify(studentExerciseRepository).save(studentExerciseCaptor.capture());

        var studentExercise = studentExerciseCaptor.getValue();
        assertThat(studentExercise).isNotNull();
        assertThat(studentExercise.getAssignment()).isEqualTo(assignment);
        assertThat(studentExercise.getStudent()).isEqualTo(student);
        assertThat(studentExercise.getGitlabProjectId()).isEqualTo(25L);
        assertThat(studentExercise.getGitlabProjectUrl()).isEqualTo("https://gitlab.univ-lille.fr/gitlab-classroom");
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
        var classroom = new Classroom();
        classroom.setGitlabGroupId(9L);
        var assignment = this.assignmentService.createExerciseAssignment(classroom, "Test Exercise Assignment", null);

        assertThat(assignment)
                .isNotNull()
                .isInstanceOf(ExerciseAssignment.class);
        assertThat(assignment.getName()).isEqualTo("Test Exercise Assignment");

        assertThat(classroom.getAssignments()).contains(assignment);

        verify(gitlab).createGroup((ExerciseAssignment) assignment, classroom);

        verify(assignmentRepository).save(assignment);
        verify(classroomService).saveClassroom(classroom);
    }

}

package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomService;
import fr.univ_lille.gitlab.classrooms.quiz.*;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUserService;
import fr.univ_lille.gitlab.classrooms.users.WithMockStudent;
import fr.univ_lille.gitlab.classrooms.users.WithMockTeacher;
import org.gitlab4j.api.GitLabApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/sql/init-test-users.sql")
class AssignmentControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssignmentService assignmentService;

    @MockBean
    private QuizScoreService quizScoreService;

    @MockBean
    private ClassroomService classroomService;

    @Autowired
    private ClassroomUserService classroomUserService;

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private GitLabApi gitLabApi;

    private final UUID quizAssignmentId = UUID.randomUUID();

    private final UUID exerciseAssignmentId = UUID.randomUUID();

    private final UUID classroomId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        var classroom = new Classroom();
        classroom.setName("AssignmentControllerMVCTest classroom");
        classroom.setId(classroomId);

        when(classroomService.getClassroom(classroomId)).thenReturn(Optional.of(classroom));

        var quiz = new QuizEntity();

        var quizAssignment = new QuizAssignment();
        quizAssignment.setId(quizAssignmentId);
        quizAssignment.setName("AssignmentControllerMVCTest Quiz assignment");
        quizAssignment.setQuiz(quiz);

        var exerciseAssignment = new ExerciseAssignment();
        exerciseAssignment.setId(exerciseAssignmentId);

        classroom.addAssignment(quizAssignment);
        classroom.addAssignment(exerciseAssignment);

        when(assignmentService.getAssignment(quizAssignmentId)).thenReturn(Optional.of(quizAssignment));
        when(assignmentService.getAssignment(exerciseAssignmentId)).thenReturn(Optional.of(exerciseAssignment));

        when(quizScoreService.getQuizResultForClassroom(quiz, classroom)).thenReturn(new QuizResult(List.of()));

        when(assignmentService.getAssignmentResults(exerciseAssignment)).thenReturn(List.of());
    }

    @Test
    void acceptAssignment_shouldAskForLogin() throws Exception {
        mockMvc.perform(get("/assignments/"+ quizAssignmentId +"/accept"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockStudent
    void acceptAssignment_shouldRedirectToClassroomJoin_ifStudentDoesNotBelongToTheClassroom() throws Exception {
        mockMvc.perform(get("/assignments/"+ quizAssignmentId +"/accept"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classrooms/" + classroomId + "/join"));
    }

    @Test
    @WithMockStudent
    void acceptAssignment_shouldShowAcceptPage_ifStudentBelongsToTheClassroom() throws Exception {
        // make the student join the classroom first
        var classroom = this.classroomService.getClassroom(this.classroomId).orElseThrow();
        classroom.join(classroomUserService.getClassroomUser("luke.skywalker"));

        mockMvc.perform(get("/assignments/"+ quizAssignmentId +"/accept"))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments/accept"))
                .andExpect(model().attributeExists("assignment"));
    }

    @Test
    @WithMockStudent
    void acceptAssignment_shouldShowAddStudentToTheListOfAccepted() throws Exception {
        mockMvc.perform(post("/assignments/"+ quizAssignmentId +"/accept").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments/accepted"));

        verify(assignmentService).acceptAssigment(any(), any());
    }

    @Test
    @WithMockStudent
    void viewQuizAssignment_shouldShowTheAssignmentResults() throws Exception {
        mockMvc.perform(get("/assignments/"+ quizAssignmentId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("quizResult"))
                .andExpect(view().name("quiz/all-submissions"));
    }

    @Test
    @WithMockStudent
    void viewExerciseAssignment_shouldShowTheAssignmentResults() throws Exception {
        mockMvc.perform(get("/assignments/"+ exerciseAssignmentId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("exercise"))
                .andExpect(model().attributeExists("exerciseResults"))
                .andExpect(view().name("exercise/all-submissions"));
    }

    @Test
    @WithMockTeacher
    void createAssignment_shouldShowNewAssignmentPage() throws Exception {
        mockMvc.perform(get("/classrooms/"+classroomId+"/assignments/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments/new"))
                .andExpect(model().attributeExists("classroom"))
                .andExpect(model().attributeExists("quizzes"))
                .andExpect(model().attributeExists("repositories"));

        verify(this.gitLabApi.getProjectApi()).getMemberProjects();
    }

    @Test
    @WithMockTeacher
    void createQuizAssignment_shouldSaveTheAssignmentToTheClassroom() throws Exception {
        mockMvc.perform(post("/classrooms/"+classroomId+"/assignments/new")
                        .with(csrf())
                        .param("assignmentName", "ClassroomControllerMVCTest assignment")
                        .param("assignmentType", "QUIZ")
                        .param("quizName", "ClassroomControllerMVCTest quiz"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classrooms/"+classroomId));
    }

    @Test
    @WithMockTeacher
    void createExerciseAssignment_shouldSaveTheAssignmentToTheClassroom() throws Exception {
        mockMvc.perform(post("/classrooms/"+classroomId+"/assignments/new")
                        .with(csrf())
                        .param("assignmentName", "ClassroomControllerMVCTest assignment")
                        .param("assignmentType", "EXERCISE")
                        .param("repositoryId", "Fake ID"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/classrooms/"+classroomId));
    }

}

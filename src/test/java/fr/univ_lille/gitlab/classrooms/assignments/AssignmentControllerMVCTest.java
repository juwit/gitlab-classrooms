package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomService;
import fr.univ_lille.gitlab.classrooms.quiz.*;
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
    private QuizRepository quizRepository;

    @MockBean
    private QuizScoreService quizScoreService;

    @MockBean
    private ClassroomService classroomService;

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private GitLabApi gitLabApi;

    private final UUID assignmentId = UUID.randomUUID();

    private final UUID classroomId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        var classroom = new Classroom();
        classroom.setName("AssignmentControllerMVCTest classroom");
        classroom.setId(classroomId);
        when(classroomService.getClassroom(classroomId)).thenReturn(Optional.of(classroom));

        var quiz = new QuizEntity();

        var assignment = new QuizAssignment();
        assignment.setId(assignmentId);
        assignment.setName("AssignmentControllerMVCTest assignment");
        assignment.setQuiz(quiz);

        classroom.addAssignment(assignment);

        when(assignmentService.getAssignment(assignmentId)).thenReturn(Optional.of(assignment));

        when(quizScoreService.getQuizResultForClassroom(quiz, classroom)).thenReturn(new QuizResult(List.of()));
    }

    @Test
    void acceptAssignment_shouldAskForLogin() throws Exception {
        mockMvc.perform(get("/assignments/"+assignmentId+"/accept"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockStudent
    void acceptAssignment_shouldShowAcceptPage() throws Exception {
        mockMvc.perform(get("/assignments/"+assignmentId+"/accept"))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments/accept"))
                .andExpect(model().attributeExists("assignment"));
    }

    @Test
    @WithMockStudent
    void acceptAssignment_shouldShowAddStudentToTheListOfAccepted() throws Exception {
        mockMvc.perform(post("/assignments/"+assignmentId+"/accept").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments/accepted"));

        verify(assignmentService).acceptAssigment(any(), any());
    }

    @Test
    @WithMockStudent
    void viewAssignment_shouldShowTheAssignmentResults() throws Exception {
        mockMvc.perform(get("/assignments/"+assignmentId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("quizResult"))
                .andExpect(view().name("quiz/all-submissions"));
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

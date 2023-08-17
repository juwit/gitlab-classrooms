package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.quiz.QuizEntity;
import fr.univ_lille.gitlab.classrooms.quiz.QuizRepository;
import fr.univ_lille.gitlab.classrooms.ui.WithMockClassroomUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/sql/init-test-users.sql")
class AssignmentControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ClassroomUserRepository classroomUserRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    private String assignmentId = "bb886148-08d3-476f-bf7d-a65a8e1ce9a8";

    @BeforeEach
    void setUp() {
        var quiz = new QuizEntity();
        quiz.setName("AssignmentControllerMVCTest quiz");
        quizRepository.save(quiz);

        var classroom = new Classroom();
        classroom.setName("AssignmentControllerMVCTest classroom");
        classroomRepository.save(classroom);

        var assignment = new QuizAssignment();
        assignment.setId(UUID.fromString(assignmentId));
        assignment.setName("AssignmentControllerMVCTest assignment");
        assignment.setQuiz(quiz);

        classroom.addAssignment(assignment);

        assignmentRepository.save(assignment);

        var luke = new ClassroomUser("luke.skywalker", List.of(ClassroomRole.STUDENT));
        classroomUserRepository.save(luke);
    }

    @AfterEach
    void tearDown() {
        assignmentRepository.deleteAll();
        classroomUserRepository.deleteAll();
    }

    @Test
    void acceptAssignment_shouldAskForLogin() throws Exception {
        mockMvc.perform(get("/assignments/"+assignmentId+"/accept"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockClassroomUser
    void acceptAssignment_shouldShowAcceptPage() throws Exception {
        mockMvc.perform(get("/assignments/"+assignmentId+"/accept"))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments/accept"))
                .andExpect(model().attributeExists("assignment"));
    }

    @Test
    @WithMockClassroomUser
    void acceptAssignment_shouldShowAddStudentToTheListOfAccepted() throws Exception {
        mockMvc.perform(post("/assignments/"+assignmentId+"/accept").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments/accepted"));

        var assignment = this.assignmentRepository.findById(UUID.fromString(assignmentId));
        assertThat(assignment).isPresent();

        assertThat(assignment.get().getStudents()).hasSize(1);
    }

    @Test
    @WithMockClassroomUser
    void viewAssignment_shouldShowTheAssignmentResults() throws Exception {
        mockMvc.perform(get("/assignments/"+assignmentId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("quizResult"))
                .andExpect(view().name("quiz/all-submissions"));
    }

}

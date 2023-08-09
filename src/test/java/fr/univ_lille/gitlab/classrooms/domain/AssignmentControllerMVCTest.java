package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.ui.WithMockClassroomUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
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
class AssignmentControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ClassroomUserRepository classroomUserRepository;

    private String assignmentId = "bb886148-08d3-476f-bf7d-a65a8e1ce9a8";

    @BeforeEach
    void setUp() {
        var assignment = new Assignment();
        assignment.setId(UUID.fromString(assignmentId));
        assignment.setName("AssignmentControllerMVCTest Assignment");
        assignment.setType(AssignmentType.QUIZ);
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

}
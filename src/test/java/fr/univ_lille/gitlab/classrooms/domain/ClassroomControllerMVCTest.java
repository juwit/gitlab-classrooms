package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomRole;
import fr.univ_lille.gitlab.classrooms.quiz.QuizEntity;
import fr.univ_lille.gitlab.classrooms.quiz.QuizRepository;
import fr.univ_lille.gitlab.classrooms.ui.WithMockClassroomUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ClassroomControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ClassroomUserRepository classroomUserRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private QuizRepository quizRepository;

    private UUID classroomId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        var classroom = new Classroom();
        classroom.setId(classroomId);
        classroom.setName("ClassroomControllerMVCTest classroom");
        classroomRepository.save(classroom);

        var luke = new ClassroomUser("luke.skywalker", List.of(ClassroomRole.STUDENT));
        classroomUserRepository.save(luke);

        var quiz = new QuizEntity();
        quiz.setName("ClassroomControllerMVCTest quiz");
        quizRepository.save(quiz);
    }

    @AfterEach
    void tearDown() {
        classroomRepository.deleteById(classroomId);
        assignmentRepository.deleteAll();
        classroomUserRepository.deleteById("luke.skywalker");
        quizRepository.deleteById("ClassroomControllerMVCTest quiz");
    }


    @Nested
    @WithMockClassroomUser(username = "obiwan.kenobi", roles = {ClassroomRole.TEACHER})
    class TeacherRole {

        @Test
        void shouldAccessNewClassroomPage() throws Exception {
            mockMvc.perform(get("/classrooms/new"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @WithMockClassroomUser(username = "luke.skywalker", roles = {ClassroomRole.STUDENT})
    class StudentRole {

        @Test
        void shouldNotAccessNewClassroomPage() throws Exception {
            mockMvc.perform(get("/classrooms/new"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @Test
        void shouldNotAccessNewAssignmentPage() throws Exception {
            mockMvc.perform(get("/classrooms/"+classroomId+"/assignments/new"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    @WithMockClassroomUser
    void joinClassroom_shouldShowJoinPage() throws Exception {
        mockMvc.perform(get("/classrooms/"+classroomId+"/join"))
                .andExpect(status().isOk())
                .andExpect(view().name("classrooms/join"))
                .andExpect(model().attributeExists("classroom"));
    }

    @Test
    @WithMockClassroomUser
    void joinClassroom_shouldAddStudentToTheListOfJoined() throws Exception {
        mockMvc.perform(post("/classrooms/"+classroomId+"/join").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("classrooms/joined"));

        var classroom = this.classroomRepository.findById(classroomId);
        assertThat(classroom).isPresent();

        assertThat(classroom.get().getStudents()).hasSize(1);
    }

    @Test
    @WithMockClassroomUser(username = "darth.vader", roles = ClassroomRole.TEACHER)
    void createAssignment_shouldShowNewAssignmentPage() throws Exception {
        mockMvc.perform(get("/classrooms/"+classroomId+"/assignments/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments/new"))
                .andExpect(model().attributeExists("classroom"))
                .andExpect(model().attributeExists("quizzes"));
    }

    @Test
    @WithMockClassroomUser(username = "darth.vader", roles = ClassroomRole.TEACHER)
    void createAssignment_shouldSaveTheAssignmentToTheClassroom() throws Exception {
        mockMvc.perform(post("/classrooms/"+classroomId+"/assignments/new")
                        .with(csrf())
                        .param("assignmentName", "ClassroomControllerMVCTest assignment")
                        .param("quizName", "ClassroomControllerMVCTest quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("classrooms/view"))
                .andExpect(model().attributeExists("classroom"));
    }

}

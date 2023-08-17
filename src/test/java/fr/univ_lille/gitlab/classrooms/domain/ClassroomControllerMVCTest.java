package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.quiz.QuizEntity;
import fr.univ_lille.gitlab.classrooms.quiz.QuizRepository;
import fr.univ_lille.gitlab.classrooms.ui.WithMockClassroomUser;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.GroupParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/sql/init-test-users.sql")
class ClassroomControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private QuizRepository quizRepository;

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private GitLabApi gitLabApi;

    @Captor
    private ArgumentCaptor<GroupParams> gitlabGroupCaptor;

    private UUID classroomId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        var classroom = new Classroom();
        classroom.setId(classroomId);
        classroom.setName("ClassroomControllerMVCTest classroom");
        classroomRepository.save(classroom);

        var quiz = new QuizEntity();
        quiz.setName("ClassroomControllerMVCTest quiz");
        quizRepository.save(quiz);
    }

    @AfterEach
    void tearDown() {
        assignmentRepository.deleteAll();
        classroomRepository.deleteAll();
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
    @WithMockClassroomUser(username = "obiwan.kenobi", roles = {ClassroomRole.TEACHER})
    void createClassroom_shouldCreateAGitlabGroup() throws Exception {
        mockMvc.perform(
                    post("/classrooms/new")
                            .with(csrf())
                            .param("classroomName", "ClassroomControllerMVCTest newClassroom"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(this.gitLabApi.getGroupApi()).createGroup(gitlabGroupCaptor.capture());

        assertThat(gitlabGroupCaptor.getValue())
                .hasFieldOrPropertyWithValue("name", "ClassroomControllerMVCTest newClassroom")
                .hasFieldOrPropertyWithValue("path", "ClassroomControllerMVCTest_newClassroom")
                .hasFieldOrPropertyWithValue("description", "Gitlab group for the Classroom ClassroomControllerMVCTest newClassroom");
    }

    @Test
    @WithMockClassroomUser(username = "obiwan.kenobi", roles = {ClassroomRole.TEACHER})
    void createClassroom_shouldSaveTheAssociatedTeacher() throws Exception {
        mockMvc.perform(
                        post("/classrooms/new")
                                .with(csrf())
                                .param("classroomName", "ClassroomControllerMVCTest newClassroom"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        var exampleClassroom = new Classroom();
        exampleClassroom.setId(null);
        exampleClassroom.setName("ClassroomControllerMVCTest newClassroom");

        var createdClassroom = this.classroomRepository.findOne(Example.of(exampleClassroom));

        assertThat(createdClassroom).isPresent()
                .get()
                .extracting("teacher.name").isEqualTo("obiwan.kenobi");
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
    @WithMockClassroomUser(username = "obiwan.kenobi", roles = ClassroomRole.TEACHER)
    void createAssignment_shouldShowNewAssignmentPage() throws Exception {
        mockMvc.perform(get("/classrooms/"+classroomId+"/assignments/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("assignments/new"))
                .andExpect(model().attributeExists("classroom"))
                .andExpect(model().attributeExists("quizzes"))
                .andExpect(model().attributeExists("repositories"));
    }

    @Test
    @WithMockClassroomUser(username = "obiwan.kenobi", roles = ClassroomRole.TEACHER)
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
    @WithMockClassroomUser(username = "obiwan.kenobi", roles = ClassroomRole.TEACHER)
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
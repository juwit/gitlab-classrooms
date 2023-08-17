package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.users.ClassroomRole;
import fr.univ_lille.gitlab.classrooms.users.WithMockClassroomUser;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/sql/init-test-users.sql")
class QuizEditionControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizRepository quizRepository;

    @Nested
    @WithMockClassroomUser(username = "obiwan.kenobi", roles = {ClassroomRole.TEACHER})
    class TeacherRole {

        @Test
        void shouldAccessQuizListPage() throws Exception {
            mockMvc.perform(get("/quiz"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void shouldAccessNewQuizPage() throws Exception {
            mockMvc.perform(get("/quiz/new/edit"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void shouldAccessQuizEditPage() throws Exception {
            var quiz = new QuizEntity();
            quiz.setName("death-star-quiz");
            quiz.setMarkdownContent("""
                # who build the Death Star ?
                (x) the Galactic Empire
                ( ) Sauron
                """);
            when(quizRepository.findById("death-star-quiz")).thenReturn(Optional.of(quiz));

            mockMvc.perform(get("/quiz/death-star-quiz/edit"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("quiz/edit"))
                    .andExpect(model().attributeExists("quiz"))
                    .andExpect(model().attribute("quiz", hasProperty("name", equalTo("death-star-quiz"))));
        }

        @Test
        void shouldSaveQuiz() throws Exception {
            var quiz = new QuizEntity();
            quiz.setName("death-star-quiz");
            quiz.setMarkdownContent("""
                # who build the Death Star ?
                (x) the Galactic Empire
                ( ) Sauron
                """);
            when(quizRepository.findById("death-star-quiz")).thenReturn(Optional.of(quiz));

            mockMvc.perform(post("/quiz/death-star-quiz/edit").with(csrf()))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void shouldSeeQuizResults() throws Exception {
            var quiz = new QuizEntity();
            quiz.setName("death-star-quiz");
            quiz.setMarkdownContent("""
                # who build the Death Star ?
                (x) the Galactic Empire
                ( ) Sauron
                """);
            when(quizRepository.findById("death-star-quiz")).thenReturn(Optional.of(quiz));


            mockMvc.perform(get("/quiz/death-star-quiz/results"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("quiz/all-submissions"))
                    .andExpect(model().attributeExists("quiz"))
                    .andExpect(model().attributeExists("quizResult"))
                    .andExpect(model().attribute("quiz", hasProperty("name", equalTo("death-star-quiz"))));
        }

    }

    @Nested
    @WithMockClassroomUser(username = "luke.skywalker", roles = {ClassroomRole.STUDENT})
    class StudentRole {

        @Test
        void shouldNotAccessQuizListPage() throws Exception {
            mockMvc.perform(get("/quiz"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @Test
        void shouldNotAccessNewQuizPage() throws Exception {
            mockMvc.perform(get("/quiz/new/edit"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @Test
        void shouldNotAccessQuizEditPage() throws Exception {
            mockMvc.perform(get("/quiz/death-star-quiz/edit"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @Test
        void shouldNotSaveQuiz() throws Exception {
            mockMvc.perform(post("/quiz/death-star-quiz/edit"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

        @Test
        void shouldNotSeeQuizResult() throws Exception {
            mockMvc.perform(post("/quiz/death-star-quiz/results"))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        }

    }

}

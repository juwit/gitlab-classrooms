package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomRole;
import fr.univ_lille.gitlab.classrooms.ui.WithMockClassroomUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/sql/init-test-users.sql")
class QuizAnswerControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizRepository quizRepository;

    @MockBean
    private QuizScoreService quizScoreService;

    @Test
    @WithMockClassroomUser(username = "luke.skywalker", roles = {ClassroomRole.STUDENT})
    void shouldReturn404_whenQuizDoesNotExists() throws Exception {
        mockMvc.perform(get("/quiz/unknown"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockClassroomUser(username = "luke.skywalker", roles = {ClassroomRole.STUDENT})
    void shouldReturnQuizPage_whenQuizExists() throws Exception {
        var quiz = new QuizEntity();
        quiz.setName("death-star-quiz");
        quiz.setMarkdownContent("""
                # who build the Death Star ?
                (x) the Galactic Empire
                ( ) Sauron
                """);
        when(quizRepository.findById("death-star-quiz")).thenReturn(Optional.of(quiz));

        mockMvc.perform(get("/quiz/death-star-quiz"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("quiz/answer"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeDoesNotExist("previousSubmission"));
    }

    @Test
    @WithMockClassroomUser(username = "luke.skywalker", roles = {ClassroomRole.STUDENT})
    void shouldReturnQuizPage_withPreviouslySubmittedAnswer_whenQuizExists() throws Exception {
        var quiz = new QuizEntity();
        quiz.setName("death-star-quiz");
        quiz.setMarkdownContent("""
                # who build the Death Star ?
                (x) the Galactic Empire
                ( ) Sauron
                """);
        when(quizRepository.findById("death-star-quiz")).thenReturn(Optional.of(quiz));

        var quizScore = new QuizScore();
        when(quizScoreService.getPreviousQuizSubmission(eq("death-star-quiz"), any())).thenReturn(Optional.of(quizScore));

        mockMvc.perform(get("/quiz/death-star-quiz"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("quiz/answer"))
                .andExpect(model().attributeExists("quiz"))
                .andExpect(model().attributeExists("previousSubmission"));
    }

}

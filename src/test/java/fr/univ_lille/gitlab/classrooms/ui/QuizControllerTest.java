package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.quiz.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizControllerTest {

    @InjectMocks
    private QuizController quizController;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizScoreService quizScoreService;

    @Mock
    private Authentication authentication;

    @Mock
    private Model model;

    @Test
    void submitQuizAnswers_shouldOutputAnError_whenQuizIsNotFullyAnswered(){
        var quiz = new QuizEntity();
        quiz.setMarkdownContent("""
                # a question
                [ ] wrong answer
                [x] good answer
                """);
        when(quizRepository.findById("testQuiz")).thenReturn(Optional.of(quiz));

        quizController.submitQuizAnswers(model, "testQuiz", Map.of(), authentication);

        verify(model).addAttribute("message", "Il manque des réponses à certaines questions.");
    }

    @Test
    void submitQuizAnswers_shouldSaveTheScore_whenQuizIsAnswered(){
        var quizId = "testQuiz";
        var quiz = new QuizEntity();
        quiz.setName(quizId);
        quiz.setMarkdownContent("""
                # a question
                [ ] wrong answer
                [x] good answer
                """);
        when(quizRepository.findById(quizId)).thenReturn(Optional.of(quiz));

        // get the answer id from the first answer of the quiz
        var answerKey = Quiz.fromMarkdown(quiz.getMarkdownContent(), quizId).getQuestions().get(0).getAnswers().get(0).getId();

        var result = quizController.submitQuizAnswers(model, quizId, Map.of(answerKey, "wrong answer"), authentication);

        assertThat(result).isEqualTo("quiz-submitted-with-answers-correction");

        verify(quizScoreService).registerScoreForStudent(any(), any());
    }

}

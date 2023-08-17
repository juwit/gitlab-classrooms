package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizAnswerControllerTest {

    @InjectMocks
    private QuizAnswerController quizAnswerController;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizScoreService quizScoreService;

    @Mock
    private Model model;

    private ClassroomUser student = new ClassroomUser();

    @Test
    void submitQuizAnswers_shouldOutputAnError_whenQuizIsNotFullyAnswered(){
        var quiz = new QuizEntity();
        quiz.setMarkdownContent("""
                # a question
                [ ] wrong answer
                [x] good answer
                """);
        when(quizRepository.findById("testQuiz")).thenReturn(Optional.of(quiz));

        quizAnswerController.submitQuizAnswers(model, "testQuiz", Map.of(), student);

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

        var result = quizAnswerController.submitQuizAnswers(model, quizId, Map.of(answerKey, "wrong answer"), student);

        assertThat(result).isEqualTo("quiz/results");

        verify(quizScoreService).registerScoreForStudent(any(), eq(student));
    }

}

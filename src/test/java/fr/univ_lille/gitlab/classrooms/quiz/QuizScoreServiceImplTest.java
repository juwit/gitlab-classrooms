package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomUser;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizScoreServiceImplTest {

    @InjectMocks
    private QuizScoreServiceImpl quizScoreService;

    @Mock
    private QuizScoreRepository quizScoreRepository;

    @Mock
    private ClassroomUserService classroomUserService;

    @Captor
    private ArgumentCaptor<QuizScore> captor;

    @Test
    void registerScoreForStudent() {
        var quiz = Quiz.fromMarkdown("""
                # a question
                [ ] wrong answer
                [x] good answer
                """, "dummy");
        var question = quiz.getQuestions().get(0);
        var firstAnswer = question.getAnswers().get(0);
        question.answer(firstAnswer, "");

        var vader = new ClassroomUser();

        quizScoreService.registerScoreForStudent(quiz, vader);

        verify(quizScoreRepository).save(captor.capture());

        var quizScore = captor.getValue();

        assertThat(quizScore.quizId).isEqualTo("dummy");
        assertThat(quizScore.score).isZero();
        assertThat(quizScore.classroomUser).isEqualTo(vader);
        assertThat(quizScore.submissionCount).isOne();
        assertThat(quizScore.maxScore).isOne();
        assertThat(quizScore.submissionDate).isEqualToIgnoringSeconds(ZonedDateTime.now());
    }
}

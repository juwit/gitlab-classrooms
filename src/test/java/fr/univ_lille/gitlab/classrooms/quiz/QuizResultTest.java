package fr.univ_lille.gitlab.classrooms.quiz;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuizResultTest {

    @Test
    void shouldComputeAverage(){
        var score1 = new QuizScore();
        score1.score = 10L;

        var score2 = new QuizScore();
        score2.score = 0L;

        var result = new QuizResult(List.of(score1, score2));

        assertThat(result.average()).isEqualTo(5);
    }

    @Test
    void shouldGiveQuestionCount(){
        var score1 = new QuizScore();
        score1.score = 10L;
        score1.maxScore = 20L;

        var result = new QuizResult(List.of(score1));

        assertThat(result.questionCount()).isEqualTo(20);
    }

}

package fr.univ_lille.gitlab.classrooms.quiz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    String sampleCorrectAnswer = "[x] Java";
    String sampleInCorrectAnswer = "[ ] JavaScript";

    String sampleSingleChoiceCorrectAnswer = "(x) Requête/réponse";
    String sampleSingleChoiceInCorrectAnswer = "( ) Non connecté";

    String sampleFullTextAnswer = "= 8080";

    @ParameterizedTest
    @ValueSource(strings = {"[x] Java", "[ ] JavaScript", "(x) Requête/réponse", "( ) Non connecté", "= 8080"})
    void shouldParseMarkdown(String answerMarkdown){
        var answer = Answer.fromMarkdown(answerMarkdown, "0");

        assertNotNull(answer);
    }

    @Test
    void selectedCorrectAnswer_shouldBeCorrect(){
        var answer = Answer.fromMarkdown(sampleCorrectAnswer, "0");

        answer.select(null);

        assertTrue(answer.isSelected());
        assertTrue(answer.isCorrect());
    }

    @Test
    void unselectedCorrectAnswer_shouldBeInCorrect(){
        var answer = Answer.fromMarkdown(sampleCorrectAnswer, "0");

        assertFalse(answer.isSelected());
        assertFalse(answer.isCorrect());
    }

    @Test
    void selectedInCorrectAnswer_shouldBeInCorrect(){
        var answer = Answer.fromMarkdown(sampleInCorrectAnswer, "0");

        answer.select(null);

        assertTrue(answer.isSelected());
        assertFalse(answer.isCorrect());
    }

    @Test
    void unselectedInCorrectAnswer_shouldBeCorrect(){
        var answer = Answer.fromMarkdown(sampleInCorrectAnswer, "0");

        assertFalse(answer.isSelected());
        assertTrue(answer.isCorrect());
    }

    @Test
    void selectedSingleChoiceCorrectAnswer_shouldBeCorrect(){
        var answer = Answer.fromMarkdown(sampleSingleChoiceCorrectAnswer, "0");

        answer.select(null);

        assertTrue(answer.isSelected());
        assertTrue(answer.isCorrect());
    }

    @Test
    void unselectedSingleChoiceCorrectAnswer_shouldBeInCorrect(){
        var answer = Answer.fromMarkdown(sampleSingleChoiceCorrectAnswer, "0");

        assertFalse(answer.isSelected());
        assertFalse(answer.isCorrect());
    }

    @Test
    void selectedSingleChoiceInCorrectAnswer_shouldBeInCorrect(){
        var answer = Answer.fromMarkdown(sampleSingleChoiceInCorrectAnswer, "0");

        answer.select(null);

        assertTrue(answer.isSelected());
        assertFalse(answer.isCorrect());
    }

    @Test
    void unselectedSingleChoiceInCorrectAnswer_shouldBeCorrect(){
        var answer = Answer.fromMarkdown(sampleSingleChoiceInCorrectAnswer, "0");

        assertFalse(answer.isSelected());
        assertTrue(answer.isCorrect());
    }

    @Test
    void wrongFullTextAnswer_shouldBeIncorrect(){
        var answer = Answer.fromMarkdown(sampleFullTextAnswer, "0");

        answer.select("12");

        assertFalse(answer.isCorrect());
    }

    @Test
    void goodFullTextAnswer_shouldBeCorrect(){
        var answer = Answer.fromMarkdown(sampleFullTextAnswer, "0");

        answer.select("8080");

        assertTrue(answer.isCorrect());
    }

}

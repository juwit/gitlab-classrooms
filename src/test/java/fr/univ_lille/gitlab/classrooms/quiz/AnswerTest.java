package fr.univ_lille.gitlab.classrooms.quiz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    String sampleCorrectAnswer = "[x] Java";
    String sampleInCorrectAnswer = "[ ] JavaScript";

    @Test
    void shouldParseMarkdown(){
        var answer = Answer.fromMarkdown(sampleCorrectAnswer, "0");

        assertNotNull(answer);
        assertEquals("Java", answer.getText());
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

}
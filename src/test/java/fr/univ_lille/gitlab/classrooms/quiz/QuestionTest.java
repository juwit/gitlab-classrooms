package fr.univ_lille.gitlab.classrooms.quiz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    String sampleQuestion = """
            # La notion de session :
            [ ] Est nécessairement associée à un login et un mot de passe
            [x] Permet de suivre l’activité d’un client
            [x] Est identique pour les JSP et les servlet
            [x] A une durée de vie maximale
            """;

    @Test
    void shouldParseMarkdown(){
        var question = Question.fromMarkdown(sampleQuestion);

        assertNotNull(question);
        assertEquals("La notion de session :", question.getText());
        assertEquals(4, question.getAnswers().size());
    }

    @Test
    void shouldBeCorrect_whenAllAnswersAreCorrect() {
        var question = Question.fromMarkdown(sampleQuestion);

        question.getAnswers().get(1).select(null);
        question.getAnswers().get(2).select(null);
        question.getAnswers().get(3).select(null);

        assertTrue(question.isCorrect());
    }

    @Test
    void shouldBeInCorrect_whenACorrectAnswerIsMissing() {
        var question = Question.fromMarkdown(sampleQuestion);

        question.getAnswers().get(1).select(null);
        question.getAnswers().get(2).select(null);

        assertFalse(question.isCorrect());
    }

    @Test
    void shouldBeInCorrect_whenAnInCorrectAnswerIsSelected() {
        var question = Question.fromMarkdown(sampleQuestion);

        question.getAnswers().get(0).select(null);
        question.getAnswers().get(1).select(null);
        question.getAnswers().get(2).select(null);
        question.getAnswers().get(3).select(null);

        assertFalse(question.isCorrect());
    }

}
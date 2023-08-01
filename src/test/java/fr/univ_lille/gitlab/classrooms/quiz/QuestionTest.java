package fr.univ_lille.gitlab.classrooms.quiz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    String sampleMultiChoiceQuestion = """
            # La notion de session :
            [ ] Est nécessairement associée à un login et un mot de passe
            [x] Permet de suivre l’activité d’un client
            [x] Est identique pour les JSP et les servlet
            [x] A une durée de vie maximale
            """;

    String sampleFullTextQuestion = """
            # Par défaut, sur quel port Tomcat délivre-t-il son contenu ?
            = 8080
            """;

    String sampleQuestionWithExplanation = """
            # Par défaut, sur quel port Tomcat délivre-t-il son contenu ?
            = 8080
            > Lien vers le support de cours :
            > <a href="https://gitlab.univ-lille.fr/SRA1-2023/sra1/-/blob/main/week1/http.md">HTTP</a>
            """;

    @Test
    void shouldParseMultipleChoiceQuestion(){
        var question = Question.fromMarkdown(sampleMultiChoiceQuestion);

        assertNotNull(question);
        assertEquals("La notion de session :", question.getText());
        assertEquals(4, question.getAnswers().size());

        assertEquals(Question.QuestionType.MULTIPLE_CHOICE, question.getQuestionType());
    }

    @Test
    void shouldParseFullTextQuestion(){
        var question = Question.fromMarkdown(sampleFullTextQuestion);

        assertNotNull(question);
        assertEquals("Par défaut, sur quel port Tomcat délivre-t-il son contenu ?", question.getText());
        assertEquals(Question.QuestionType.FULL_TEXT, question.getQuestionType());
    }

    @Test
    void shouldParseMarkdown_forAQuestionWithExplanation(){
        var question = Question.fromMarkdown(sampleQuestionWithExplanation);

        assertNotNull(question);
        assertEquals("Lien vers le support de cours : \n<a href=\"https://gitlab.univ-lille.fr/SRA1-2023/sra1/-/blob/main/week1/http.md\">HTTP</a>", question.getExplanation());
    }

    @Test
    void shouldBeCorrect_whenAllAnswersAreCorrect() {
        var question = Question.fromMarkdown(sampleMultiChoiceQuestion);

        question.getAnswers().get(1).select(null);
        question.getAnswers().get(2).select(null);
        question.getAnswers().get(3).select(null);

        assertTrue(question.isCorrect());
    }

    @Test
    void shouldBeInCorrect_whenACorrectAnswerIsMissing() {
        var question = Question.fromMarkdown(sampleMultiChoiceQuestion);

        question.getAnswers().get(1).select(null);
        question.getAnswers().get(2).select(null);

        assertFalse(question.isCorrect());
    }

    @Test
    void shouldBeInCorrect_whenAnInCorrectAnswerIsSelected() {
        var question = Question.fromMarkdown(sampleMultiChoiceQuestion);

        question.getAnswers().get(0).select(null);
        question.getAnswers().get(1).select(null);
        question.getAnswers().get(2).select(null);
        question.getAnswers().get(3).select(null);

        assertFalse(question.isCorrect());
    }

}

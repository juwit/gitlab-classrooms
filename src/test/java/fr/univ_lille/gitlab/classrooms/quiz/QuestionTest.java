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

    String sampleSingleChoiceQuestion = """
            # JDBC fonctionne en mode
            ( ) Non connecté
            (x) Requête/réponse
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
    void shouldParseSingleChoiceQuestion(){
        var question = Question.fromMarkdown(sampleSingleChoiceQuestion);

        assertNotNull(question);
        assertEquals("JDBC fonctionne en mode", question.getText());
        assertEquals(Question.QuestionType.SINGLE_CHOICE, question.getQuestionType());
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

    @Test
    void singleChoiceQuestions_shouldBeCorrect_whenACorrectAnswerIsSelected(){
        var question = Question.fromMarkdown(sampleSingleChoiceQuestion);

        var answers = question.getAnswers();

        question.answer(answers.get(1), "");

        assertTrue(question.isAnswered());
        assertTrue(question.isCorrect());
    }

    @Test
    void singleChoiceQuestions_shouldBeInCorrect_whenAnInCorrectAnswerIsSelected(){
        var question = Question.fromMarkdown(sampleSingleChoiceQuestion);

        var answers = question.getAnswers();

        question.answer(answers.get(0), "");

        assertTrue(question.isAnswered());
        assertFalse(question.isCorrect());
    }

    @Test
    void singleChoiceQuestions_shouldAlwaysOnlyHaveOneChoiceSelectted(){
        var question = Question.fromMarkdown(sampleSingleChoiceQuestion);

        var answers = question.getAnswers();

        question.answer(answers.get(0), "");
        question.answer(answers.get(1), "");

        // selecting the second answer should unselect the first
        assertFalse(answers.get(0).isSelected());
        assertTrue(answers.get(1).isSelected());
    }
}

package fr.univ_lille.gitlab.classrooms.quiz;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class QuizTest {

    static String sampleQuizContent;

    @BeforeAll
    static void beforeAll() throws IOException {
        var resource = new ClassPathResource("/quiz/sample.md");

        sampleQuizContent = resource.getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void shouldParseMarkdown(){
        var quiz = Quiz.fromMarkdown(sampleQuizContent, "sample");

        assertNotNull(quiz);
        assertEquals("sample", quiz.getName());
        assertEquals(2, quiz.getQuestions().size());
    }

    @Test
    void shouldParseMarkdownWithWindowsLineBreaks() throws IOException {
        var resource = new ClassPathResource("/quiz/sampleWindows.md");
        var windowsSampleQuizContent = resource.getContentAsString(StandardCharsets.UTF_8);
        var quiz = Quiz.fromMarkdown(windowsSampleQuizContent, "sample");

        assertNotNull(quiz);
        assertEquals("sample", quiz.getName());
        assertEquals(2, quiz.getQuestions().size());
    }

    @Test
    void shouldNotBeFullyAnswered_whenNotAllQuestionsAreAnswered(){
        var quiz = Quiz.fromMarkdown(sampleQuizContent, "sample");
        // answer no questions
        assertFalse(quiz.isFullyAnswered());
    }

    @Test
    void shouldBeFullyAnswered_whenAllQuestionsAreAnswered(){
        var quiz = Quiz.fromMarkdown(sampleQuizContent, "sample");

        quiz.getQuestions().forEach(it -> it.answer());

        assertTrue(quiz.isFullyAnswered());
    }

    @Test
    void shouldHaveAScoreOfZero_whenAllAnswersAreIncorrect(){
        var quiz = Quiz.fromMarkdown(sampleQuizContent, "sample");

        // answer no questions
        assertEquals(0, quiz.score());
    }

    @Test
    void shouldHaveAScoreOfOne_whenOneAnswerIsIncorrect(){
        var quiz = Quiz.fromMarkdown(sampleQuizContent, "sample");

        quiz.getQuestions().get(0).getAnswers().get(1).select(null);

        assertEquals(1, quiz.score());
    }

    @Test
    void shouldHaveAScoreOfTwo_whenTwoAnswerAreIncorrect(){
        var quiz = Quiz.fromMarkdown(sampleQuizContent, "sample");

        quiz.getQuestions().get(0).getAnswers().get(1).select(null);
        quiz.getQuestions().get(1).getAnswers().get(3).select(null);

        assertEquals(2, quiz.score());
    }
}

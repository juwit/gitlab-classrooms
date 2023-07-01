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
        assertEquals(17, quiz.getQuestions().size());
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
}
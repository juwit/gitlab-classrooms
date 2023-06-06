package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private final List<Question> questions;

    private Quiz(List<Question> questions) {
        this.questions = questions;
    }

    public void reset() {
        for (Question question : questions) {
            question.reset();
        }
    }

    public static Quiz fromMarkdown(String markdown) {
        String[] markdownQuestions = markdown.split("\n\n");

        List<Question> questions = new ArrayList<>();
        for (String markdownQuestion : markdownQuestions) {
            Question question = Question.fromMarkdown(markdownQuestion);
            questions.add(question);
        }

        return new Quiz(questions);
    }
}

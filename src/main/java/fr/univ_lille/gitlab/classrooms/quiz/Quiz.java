package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private String name;
    private final List<Question> questions;

    private Quiz(List<Question> questions, String name) {
        this.questions = questions;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void reset() {
        for (Question question : questions) {
            question.reset();
        }
    }

    public static Quiz fromMarkdown(String markdown, String name) {
        String[] markdownQuestions = markdown.split("\n\n");

        List<Question> questions = new ArrayList<>();
        for (String markdownQuestion : markdownQuestions) {
            Question question = Question.fromMarkdown(markdownQuestion);
            questions.add(question);
        }

        return new Quiz(questions, name);
    }
}

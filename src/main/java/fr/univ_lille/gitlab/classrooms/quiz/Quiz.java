package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * Ugly method to answer questions from a form submission
     * @param quizAnswers
     */
    public void answerQuestions(Map<String, String> quizAnswers) {
        this.questions.forEach(question -> {
            question.getAnswers().forEach(answer -> {
                if(quizAnswers.containsKey(answer.getId()) && ! quizAnswers.get(answer.getId()).isBlank()){
                    answer.select(quizAnswers.get(answer.getId()));
                    // mark the question as answered
                    question.answer();
                }
            });
        });
    }

    public boolean isFullyAnswered(){
        return this.questions.stream().allMatch(question -> question.isAnswered());
    }
}

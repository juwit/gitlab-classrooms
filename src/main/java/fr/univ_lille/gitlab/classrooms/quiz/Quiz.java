package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Quiz {

    private final List<Question> questions;
    private String name;

    private Quiz(List<Question> questions, String name) {
        this.questions = questions;
        this.name = name;
    }

    public static Quiz fromMarkdown(String markdown, String name) {
        String[] markdownQuestions = markdown.split("(\\r\\n|\\n){2}");

        List<Question> questions = new ArrayList<>();
        for (String markdownQuestion : markdownQuestions) {
            Question question = Question.fromMarkdown(markdownQuestion);
            questions.add(question);
        }

        return new Quiz(questions, name);
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Ugly method to answer questions from a form submission
     *
     * @param quizAnswers
     */
    public void answerQuestions(Map<String, String> quizAnswers) {
        this.questions.forEach(question -> {
            question.getAnswers().forEach(answer -> {
                if (quizAnswers.containsKey(answer.getId()) && !quizAnswers.get(answer.getId()).isBlank()) {
                    // mark the question as answered
                    question.answer(answer, quizAnswers.get(answer.getId()));
                }
            });

            // single choice answers, receiving a key = question id && value = selected answer id
            if (quizAnswers.containsKey(question.getId())) {
                var selectedAnswerId = quizAnswers.get(question.getId());
                question.getAnswers().stream()
                        // find the answer
                        .filter(it -> it.getId().equals(selectedAnswerId))
                        .findFirst()
                        // and select it
                        .ifPresent(it -> question.answer(it, ""));
            }
        });
    }

    public boolean isFullyAnswered() {
        return this.questions.stream().allMatch(Question::isAnswered);
    }

    /**
     * Computes scoring for the quiz.
     */
    public long score() {
        return this.questions.stream().filter(Question::isCorrect).count();
    }
}

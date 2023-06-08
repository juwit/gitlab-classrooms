package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.Arrays;
import java.util.List;

public class Question {
    private final String text;
    private final List<Answer> answers;
    private boolean answered = false;

    enum QuestionType {
        FULL_TEXT, MULTIPLE_CHOICE
    }

    private Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public static Question fromMarkdown(String markdown) {
        String[] lines = markdown.split("\n");

        String questionText = lines[0].trim().substring(2);

        var answers = Arrays.stream(lines)
                .skip(1)
                .map(Answer::fromMarkdown)
                .toList();

        return new Question(questionText, answers);
    }

    public void answer() {
        answered = true;
    }

    public boolean isAnswered() {
        return answered;
    }

    public int score() {
        return (int) answers.stream()
                .filter(answer -> answer.isCorrect())
                .count();
    }

    public void reset() {
        answered = false;
        for (Answer answer : answers) {
            answer.reset();
        }
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public QuestionType getQuestionType(){
        return switch (this.answers.get(0).getType()){
            case FULL_TEXT -> QuestionType.FULL_TEXT;
            case MULTIPLE_CHOICE -> QuestionType.MULTIPLE_CHOICE;
        };
    }
}

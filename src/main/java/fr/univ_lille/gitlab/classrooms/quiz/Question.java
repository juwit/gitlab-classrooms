package fr.univ_lille.gitlab.classrooms.quiz;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Question {
    private final String text;
    private final List<Answer> answers;
    private final String explanation;
    private boolean answered = false;

    private Question(String text, List<Answer> answers, String explanation) {
        this.text = text;
        this.answers = answers;
        this.explanation = explanation;
    }

    public static Question fromMarkdown(String markdown) {
        String[] lines = markdown.split("\n");

        String questionText = lines[0].trim().substring(2);

        var answers = Arrays.stream(lines)
                .skip(1) // skip question line
                .filter(it -> !it.startsWith(">")) // filter explanation lines
                .map(it -> Answer.fromMarkdown(it, DigestUtils.sha256Hex(questionText + it)))
                .toList();

        var explanation = Arrays.stream(lines)
                .filter(it -> it.startsWith(">"))
                .map(it -> it.substring(1).trim())
                .collect(Collectors.joining(" \n"));

        return new Question(questionText, answers, explanation);
    }

    public void answer(Answer answer, String value) {
        if (this.getQuestionType().equals(QuestionType.SINGLE_CHOICE)) {
            this.reset();
        }
        answer.select(value);
        answered = true;
    }

    public boolean isAnswered() {
        return answered;
    }

    public int score() {
        return (int) answers.stream()
                .filter(Answer::isCorrect)
                .count();
    }

    public void reset() {
        answered = false;
        for (Answer answer : answers) {
            answer.reset();
        }
    }

    public String getId() {
        return DigestUtils.sha256Hex(this.text);
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getExplanation() {
        return explanation;
    }

    public QuestionType getQuestionType() {
        return switch (this.answers.get(0).getType()) {
            case FULL_TEXT -> QuestionType.FULL_TEXT;
            case SINGLE_CHOICE -> QuestionType.SINGLE_CHOICE;
            case MULTIPLE_CHOICE -> QuestionType.MULTIPLE_CHOICE;
        };
    }

    public boolean isCorrect() {
        return this.answers.stream().allMatch(Answer::isCorrect);
    }

    enum QuestionType {
        FULL_TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE
    }
}

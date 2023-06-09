package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.regex.*;

public class Answer {

    enum AnswerType {
        FULL_TEXT, MULTIPLE_CHOICE
    }
    private static final Pattern correctRegex = Pattern.compile("\\[x\\] (.*)");
    private static final Pattern incorrectRegex = Pattern.compile("\\[ \\] (.*)");
    private static final Pattern fullTextRegex = Pattern.compile("= (.*)");

    private final String text;
    private final boolean correct;
    private boolean selected = false;
    private String inputText;
    private AnswerType type;

    private String id;

    private Answer(String text, boolean correct, AnswerType type, String id) {
        this.text = text;
        this.correct = correct;
        this.type = type;
        this.id = id;
    }

    public static Answer fromMarkdown(String markdown, String id) {
        Matcher correctMatcher = correctRegex.matcher(markdown);
        if (correctMatcher.find()) {
            String text = correctMatcher.group(1);
            return new Answer(text, true, AnswerType.MULTIPLE_CHOICE, id);
        }

        Matcher incorrectMatcher = incorrectRegex.matcher(markdown);
        if (incorrectMatcher.find()) {
            String text = incorrectMatcher.group(1);
            return new Answer(text, false, AnswerType.MULTIPLE_CHOICE, id);
        }

        Matcher fullTextMatcher = fullTextRegex.matcher(markdown);
        if (fullTextMatcher.find()) {
            String text = fullTextMatcher.group(1);
            return new Answer(text, true, AnswerType.FULL_TEXT, id);
        }

        return null; // Ajoutez un comportement par défaut approprié si nécessaire
    }

    public void reset(){
        this.selected = false;
    }

    public void select(String selection) {
        this.selected = true;
        this.inputText = selection;
    }

    public boolean isCorrect() {
        return this.selected && this.correct;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getId(){
        return this.id;
    }
    public String getText() {
        return this.text;
    }

    AnswerType getType() {
        return type;
    }
}

package fr.univ_lille.gitlab.classrooms.quiz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Answer {

    enum AnswerType {
        FULL_TEXT, SINGLE_CHOICE, MULTIPLE_CHOICE
    }
    private static final Pattern multipleChoiceCorrectRegex = Pattern.compile("\\[x\\] (.*)");
    private static final Pattern multipleChoiceIncorrectRegex = Pattern.compile("\\[ \\] (.*)");

    private static final Pattern singleChoiceCorrectRegex = Pattern.compile("\\(x\\) (.*)");

    private static final Pattern singleChoiceIncorrectRegex = Pattern.compile("\\( \\) (.*)");
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
        Matcher multipleChoiceCorrectMatcher = multipleChoiceCorrectRegex.matcher(markdown);
        if (multipleChoiceCorrectMatcher.find()) {
            String text = multipleChoiceCorrectMatcher.group(1);
            return new Answer(text, true, AnswerType.MULTIPLE_CHOICE, id);
        }

        Matcher multipleChoiceIncorrectMatcher = multipleChoiceIncorrectRegex.matcher(markdown);
        if (multipleChoiceIncorrectMatcher.find()) {
            String text = multipleChoiceIncorrectMatcher.group(1);
            return new Answer(text, false, AnswerType.MULTIPLE_CHOICE, id);
        }

        Matcher singleChoiceCorrectMatcher = singleChoiceCorrectRegex.matcher(markdown);
        if (singleChoiceCorrectMatcher.find()) {
            String text = singleChoiceCorrectMatcher.group(1);
            return new Answer(text, true, AnswerType.SINGLE_CHOICE, id);
        }

        Matcher singleChoiceInCorrectMatcher = singleChoiceIncorrectRegex.matcher(markdown);
        if (singleChoiceInCorrectMatcher.find()) {
            String text = singleChoiceInCorrectMatcher.group(1);
            return new Answer(text, false, AnswerType.SINGLE_CHOICE, id);
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
        return switch (this.type){
            case SINGLE_CHOICE, MULTIPLE_CHOICE -> this.selected == this.correct;
            case FULL_TEXT -> this.inputText.trim().equalsIgnoreCase(this.text);
        };
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

    public String getInputText() {
        return inputText;
    }
}

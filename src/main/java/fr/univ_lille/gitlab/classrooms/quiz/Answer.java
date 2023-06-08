package fr.univ_lille.gitlab.classrooms.quiz;

import org.apache.commons.codec.digest.DigestUtils;

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
    private AnswerType type;

    private Answer(String text, boolean correct, AnswerType type) {
        this.text = text;
        this.correct = correct;
        this.type = type;
    }

    public static Answer fromMarkdown(String markdown) {
        Matcher correctMatcher = correctRegex.matcher(markdown);
        if (correctMatcher.find()) {
            String text = correctMatcher.group(1);
            return new Answer(text, true, AnswerType.MULTIPLE_CHOICE);
        }

        Matcher incorrectMatcher = incorrectRegex.matcher(markdown);
        if (incorrectMatcher.find()) {
            String text = incorrectMatcher.group(1);
            return new Answer(text, false, AnswerType.MULTIPLE_CHOICE);
        }

        Matcher fullTextMatcher = fullTextRegex.matcher(markdown);
        if (fullTextMatcher.find()) {
            String text = fullTextMatcher.group(1);
            return new Answer(text, true, AnswerType.FULL_TEXT);
        }

        return null; // Ajoutez un comportement par défaut approprié si nécessaire
    }

    public void reset(){
        this.selected = false;
    }

    public boolean isCorrect() {
        return this.selected && this.correct;
    }


    public String getId(){
        return DigestUtils.sha256Hex(this.text);
    }
    public String getText() {
        return this.text;
    }

    AnswerType getType() {
        return type;
    }
}

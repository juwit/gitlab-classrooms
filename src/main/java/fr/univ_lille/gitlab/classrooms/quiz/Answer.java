package fr.univ_lille.gitlab.classrooms.quiz;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.*;

public class Answer {
    private static final Pattern correctRegex = Pattern.compile("\\[x\\] (.*)");
    private static final Pattern incorrectRegex = Pattern.compile("\\[ \\] (.*)");
    private static final Pattern fullTextRegex = Pattern.compile("= (.*)");

    private final String text;
    private final boolean correct;
    private boolean selected = false;
    private String type;

    private Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public static Answer fromMarkdown(String markdown) {
        Matcher correctMatcher = correctRegex.matcher(markdown);
        if (correctMatcher.find()) {
            String text = correctMatcher.group(1);
            return new Answer(text, true);
        }

        Matcher incorrectMatcher = incorrectRegex.matcher(markdown);
        if (incorrectMatcher.find()) {
            String text = incorrectMatcher.group(1);
            return new Answer(text, false);
        }

        Matcher fullTextMatcher = fullTextRegex.matcher(markdown);
        if (fullTextMatcher.find()) {
            String text = fullTextMatcher.group(1);
            return new Answer(text, true);
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
}

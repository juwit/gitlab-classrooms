package fr.univ_lille.gitlab.classrooms.gitlab;

import org.gitlab4j.api.GitLabApiException;

public class GitLabException extends Exception {

    public GitLabException(String message) {
        super(message);
    }

    public GitLabException(String message, GitLabApiException cause) {
        super(message, cause);
    }
}

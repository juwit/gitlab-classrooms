package fr.univ_lille.gitlab.classrooms.export;

/**
 * A exception occurring when trying to export data from GitLab Classrooms
 */
public class ExportException extends Exception {

    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
}

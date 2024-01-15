package fr.univ_lille.gitlab.classrooms.export;

import fr.univ_lille.gitlab.classrooms.classrooms.Classroom;
import org.gitlab4j.api.GitLabApiException;


/**
 * Service that helps exporting data from GitLab Classrooms.
 */
public interface ExportService {

    /**
     * Generates a script that git clones all repositories from a classroom, organized by student.
     * @param classroom the classroom to export
     * @return a String containing a sh script
     * @throws ExportException if the export fails
     */
    String generateCloneClassroomScriptByStudent(Classroom classroom) throws ExportException;

}

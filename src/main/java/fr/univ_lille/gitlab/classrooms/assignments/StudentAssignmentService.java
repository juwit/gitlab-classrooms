package fr.univ_lille.gitlab.classrooms.assignments;

import java.util.Optional;

public interface StudentAssignmentService {

    /**
     * Gets an assignment from its Gitlab Project id
     *
     * @param gitlabProjectId
     * @return
     */
    Optional<StudentExerciseAssignment> getByGitlabProjectId(long gitlabProjectId);

    void save(StudentExerciseAssignment exerciseAssignment);
}

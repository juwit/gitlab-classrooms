package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;

import java.util.Optional;
import java.util.UUID;

public interface StudentAssignmentService {

    /**
     * Gets an assignment from its Gitlab Project id
     *
     * @param gitlabProjectId
     * @return
     */
    Optional<StudentExerciseAssignment> getByGitlabProjectId(long gitlabProjectId);

    void resetGrades(ClassroomUser student, UUID studentAssignmentId);

    void save(StudentExerciseAssignment exerciseAssignment);
}

package fr.univ_lille.gitlab.classrooms.gitlab;

import fr.univ_lille.gitlab.classrooms.assignments.ExerciseAssignment;
import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Project;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Provides an abstraction to the Gitlab API.
 */
public interface Gitlab {

    List<Project> getProjectsOfConnectedUser() throws GitLabApiException;

    List<Group> getGroupsOfConnectedUser() throws GitLabApiException;

    URI getGroupURI(Classroom classroom) throws GitLabApiException;

    /**
     * Create a Gitlab group for the Classroom.
     * The ID of the created group is set to the {@link Classroom#setGitlabGroupId(Long)} attribute.
     * @param classroom the classroom to create a group for.
     * @param parentGroupId the optional parent group id to create the classroom group in.
     */
    void createGroup(Classroom classroom, Optional<Long> parentGroupId) throws GitLabApiException;

    /**
     * Create a Gitlab group for the ExerciseAssignment, in the group of the given Classroom.
     * The ID of the created group is set to the {@link ExerciseAssignment#setGitlabGroupId(Long)} attribute.
     * @param exerciseAssignment the exerciseAssignment to create a group for.
     * @param classroom the classroom of the assignment.
     */
    void createGroup(ExerciseAssignment exerciseAssignment, Classroom classroom) throws GitLabApiException;

    /**
     * Creates the Gitlab project for the ExerciseAssignment, for the given Student.
     * @param exerciseAssignment the exerciseAssignment to create the project for.
     * @param student the student who accepted the assignment
     * @return the Gitlab Project
     */
    Project createProject(ExerciseAssignment exerciseAssignment, ClassroomUser student) throws GitLabApiException;
}

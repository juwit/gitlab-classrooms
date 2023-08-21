package fr.univ_lille.gitlab.classrooms.gitlab;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Project;

import java.util.List;

/**
 * Provides an abstraction to the Gitlab API.
 */
public interface Gitlab {

    List<Project> getProjectsOfConnectedUser() throws GitLabApiException;

    List<Group> getGroupsOfConnectedUser() throws GitLabApiException;
}

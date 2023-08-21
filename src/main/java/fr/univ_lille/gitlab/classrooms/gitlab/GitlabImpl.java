package fr.univ_lille.gitlab.classrooms.gitlab;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Project;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
class GitlabImpl implements Gitlab {

    private final GitLabApi gitLabApi;

    public GitlabImpl(GitLabApi gitLabApi) {
        this.gitLabApi = gitLabApi;
    }

    @Override
    public List<Project> getProjectsOfConnectedUser() throws GitLabApiException {
        return this.gitLabApi.getProjectApi().getMemberProjects();
    }

    @Override
    public List<Group> getGroupsOfConnectedUser() throws GitLabApiException {
        return this.gitLabApi.getGroupApi().getGroups();
    }

    @Override
    public URI getGroupURI(Classroom classroom) throws GitLabApiException {
        var group = this.gitLabApi.getGroupApi().getGroup(classroom.getGitlabGroupId());
        return URI.create(group.getWebUrl());
    }
}

package fr.univ_lille.gitlab.classrooms.gitlab;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitlabImplTest {

    @InjectMocks
    private GitlabImpl gitlab;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private GitLabApi gitLabApi;

    @Test
    void getProjectsOfConnectedUser_shouldListMemberProjects() throws GitLabApiException {
        gitlab.getProjectsOfConnectedUser();

        verify(gitLabApi.getProjectApi()).getMemberProjects();
    }

    @Test
    void getGroupsOfConnectedUser_shouldListUserGroups() throws GitLabApiException {
        gitlab.getGroupsOfConnectedUser();

        verify(gitLabApi.getGroupApi()).getGroups();
    }

    @Test
    void getGroupURI_shouldGetTheURI_forAGivenClassroom() throws GitLabApiException {
        var classroom = new Classroom();
        classroom.setGitlabGroupId(12L);

        var group = new Group();
        group.setWebUrl("https://gitlab.univ-lille.fr/gitlab-classrooms");

        when(gitLabApi.getGroupApi().getGroup(12L)).thenReturn(group);

        var uri = gitlab.getGroupURI(classroom);

        assertThat(uri).isEqualTo(URI.create("https://gitlab.univ-lille.fr/gitlab-classrooms"));
    }
}

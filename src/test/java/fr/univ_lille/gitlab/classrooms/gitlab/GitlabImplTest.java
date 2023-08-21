package fr.univ_lille.gitlab.classrooms.gitlab;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.GroupParams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.Optional;

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

    @Test
    void createGroup_shouldCreateAGroup_forAGivenClassroom() throws GitLabApiException{
        var classroom = new Classroom();
        classroom.setName("Test classroom");

        var group = new Group();
        group.setId(36L);

        when(gitLabApi.getGroupApi().createGroup(any())).thenReturn(group);

        gitlab.createGroup(classroom, Optional.empty());

        var gitlabGroupCaptor = ArgumentCaptor.forClass(GroupParams.class);
        verify(this.gitLabApi.getGroupApi()).createGroup(gitlabGroupCaptor.capture());

        assertThat(gitlabGroupCaptor.getValue())
                .hasFieldOrPropertyWithValue("name", "Test classroom")
                .hasFieldOrPropertyWithValue("path", "Test_classroom")
                .hasFieldOrPropertyWithValue("description", "Gitlab group for the Classroom Test classroom");

        assertThat(classroom.getGitlabGroupId()).isEqualTo(36L);
    }
}

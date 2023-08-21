package fr.univ_lille.gitlab.classrooms.gitlab;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
}

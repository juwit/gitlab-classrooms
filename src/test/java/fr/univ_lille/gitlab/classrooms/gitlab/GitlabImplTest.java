package fr.univ_lille.gitlab.classrooms.gitlab;

import fr.univ_lille.gitlab.classrooms.assignments.ExerciseAssignment;
import fr.univ_lille.gitlab.classrooms.classrooms.Classroom;
import fr.univ_lille.gitlab.classrooms.users.ClassroomRole;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.AccessLevel;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.GroupParams;
import org.gitlab4j.api.models.Project;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitlabImplTest {

    @InjectMocks
    private GitlabImpl gitlab;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private GitLabApi gitLabApi;

    @Mock
    private GitlabApiFactory gitlabApiFactory;

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

    @Test
    void createGroup_shouldCreateAGroup_forAGivenExerciseAssignment() throws GitLabApiException{
        var classroom = new Classroom();
        classroom.setName("Test classroom");
        classroom.setGitlabGroupId(12L);

        var assignment = new ExerciseAssignment();
        assignment.setName("Exercice 1");

        var group = new Group();
        group.setId(72L);
        when(gitLabApi.getGroupApi().createGroup(any())).thenReturn(group);

        gitlab.createGroup(assignment, classroom);

        var gitlabGroupCaptor = ArgumentCaptor.forClass(GroupParams.class);
        verify(this.gitLabApi.getGroupApi()).createGroup(gitlabGroupCaptor.capture());

        assertThat(gitlabGroupCaptor.getValue())
                .hasFieldOrPropertyWithValue("name", "Exercice 1")
                .hasFieldOrPropertyWithValue("path", "Exercice_1")
                .hasFieldOrPropertyWithValue("parentId", 12L)
                .hasFieldOrPropertyWithValue("description", "Gitlab group for the assignment Exercice 1");

        assertThat(assignment.getGitlabGroupId()).isEqualTo(72L);
    }

    @Test
    void createProject_shouldCreateAProject_andGiveAccessToTheStudent() throws GitLabApiException {
        var student = new ClassroomUser("luke.skywalker", List.of(ClassroomRole.STUDENT));
        student.setGitlabUserId(8L);
        var teacher = new ClassroomUser("obiwan.kenobi", List.of(ClassroomRole.TEACHER));

        var assignment = new ExerciseAssignment();
        assignment.setName("Exercice 1");
        assignment.setGitlabGroupId(72L);

        var classroom = new Classroom();
        classroom.setTeacher(teacher);
        classroom.addAssignment(assignment);

        when(gitlabApiFactory.userGitlabApi(teacher)).thenReturn(gitLabApi);

        var projectMock = new Project();
        projectMock.setId(125L);
        when(gitLabApi.getProjectApi().createProject(any(Project.class))).thenReturn(projectMock);

        gitlab.createProject(assignment, student);

        var projectCaptor = ArgumentCaptor.forClass(Project.class);
        verify(gitLabApi.getProjectApi()).createProject(projectCaptor.capture());

        var project = projectCaptor.getValue();
        assertThat(project).isNotNull();
        assertThat(project.getName()).isEqualTo("Exercice 1-luke.skywalker");
        assertThat(project.getNamespace().getId()).isEqualTo(72L);

        verify(gitLabApi.getProjectApi()).addMember(125L, 8L, AccessLevel.MAINTAINER);

        verifyNoMoreInteractions(gitLabApi.getProjectApi());
    }
}
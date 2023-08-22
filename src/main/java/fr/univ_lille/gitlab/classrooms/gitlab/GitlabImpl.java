package fr.univ_lille.gitlab.classrooms.gitlab;

import fr.univ_lille.gitlab.classrooms.assignments.ExerciseAssignment;
import fr.univ_lille.gitlab.classrooms.classrooms.Classroom;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.AccessLevel;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.GroupParams;
import org.gitlab4j.api.models.Project;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
class GitlabImpl implements Gitlab {

    private final GitLabApi gitLabApi;

    private final GitlabApiFactory gitlabApiFactory;

    public GitlabImpl(GitLabApi gitLabApi, GitlabApiFactory gitlabApiFactory) {
        this.gitLabApi = gitLabApi;
        this.gitlabApiFactory = gitlabApiFactory;
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

    @Override
    public void createGroup(Classroom classroom, Optional<Long> parentGroupId) throws GitLabApiException {
        var classroomName = classroom.getName();
        var groupPath = classroomName.trim().replaceAll("[^\\w\\-.]", "_");

        var groupParams = new GroupParams()
                .withName(classroomName)
                .withPath(groupPath)
                .withDescription("Gitlab group for the Classroom " + classroomName);
        parentGroupId.ifPresent(groupParams::withParentId);

        var group = this.gitLabApi.getGroupApi().createGroup(groupParams);

        classroom.setGitlabGroupId(group.getId());
    }

    @Override
    public void createGroup(ExerciseAssignment exerciseAssignment, Classroom classroom) throws GitLabApiException {
        var assignmentName = exerciseAssignment.getName();
        var groupPath = assignmentName.trim().replaceAll("[^\\w\\-.]", "_");

        var groupParams = new GroupParams()
                .withName(assignmentName)
                .withPath(groupPath)
                .withDescription("Gitlab group for the assignment " + assignmentName)
                .withParentId(classroom.getGitlabGroupId());

        var group = this.gitLabApi.getGroupApi().createGroup(groupParams);

        exerciseAssignment.setGitlabGroupId(group.getId());
    }

    @Override
    public Project createProject(ExerciseAssignment exerciseAssignment, ClassroomUser student) throws GitLabApiException {
        var classroom = exerciseAssignment.getClassroom();
        var teacher = classroom.getTeacher();
        // get a gitlab api client ith the teacher's rights
        var teacherGitlabApi = this.gitlabApiFactory.userGitlabApi(teacher);

        // create a blank project
        var projectParams = new Project()
                .withName(exerciseAssignment.getName() + "-" + student.getName())
                .withNamespaceId(exerciseAssignment.getGitlabGroupId());
        var project = teacherGitlabApi.getProjectApi().createProject(projectParams);

        // grant the student access to its project
        teacherGitlabApi.getProjectApi().addMember(project.getId(), student.getGitlabUserId(), AccessLevel.MAINTAINER);

        return project;
    }
}

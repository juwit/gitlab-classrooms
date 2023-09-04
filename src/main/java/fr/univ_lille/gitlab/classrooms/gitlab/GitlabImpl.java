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

    /**
     * Slugify a name by removing all chars that are not letters, numbers, underscore or hyphens, and replacing them by underscores.
     *
     * @param name value to slugify
     * @return slugified value
     */
    private String slugify(String name) {
        return name.trim().replaceAll("[^\\w\\-.]", "_");
    }

    @Override
    public void createGroup(Classroom classroom, Optional<Long> parentGroupId) throws GitLabApiException {
        var classroomName = classroom.getName();
        var groupPath = slugify(classroomName);

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
        var groupPath = slugify(assignmentName);

        var groupParams = new GroupParams()
                .withName(assignmentName)
                .withPath(groupPath)
                .withDescription("Gitlab group for the assignment " + assignmentName)
                .withParentId(classroom.getGitlabGroupId());

        var group = this.gitLabApi.getGroupApi().createGroup(groupParams);

        exerciseAssignment.setGitlabGroupId(group.getId());
    }

    @Override
    public Project createProject(ExerciseAssignment exerciseAssignment, ClassroomUser student) throws GitLabApiException, GitLabException {
        var classroom = exerciseAssignment.getClassroom();
        var teacher = classroom.getTeacher();
        // get a gitlab api client ith the teacher's rights
        var teacherGitlabApi = this.gitlabApiFactory.userGitlabApi(teacher);

        Project project = null;

        // get gitlab group info
        var group = teacherGitlabApi.getGroupApi().getGroup(exerciseAssignment.getGitlabGroupId());
        var projectName = exerciseAssignment.getName() + "-" + student.getName();

        try {
            project = teacherGitlabApi.getProjectApi().getProject(group.getFullPath(), projectName);
        }
        catch (GitLabApiException ignore){
            // ignoring the 404 exception, meaning that the project doesn't exists yet
        }

        if (project == null && (exerciseAssignment.getGitlabRepositoryTemplateId() != null && !exerciseAssignment.getGitlabRepositoryTemplateId().isBlank())) {
            var path = slugify(projectName);
            // fork the template project
            project = teacherGitlabApi.getProjectApi().forkProject(
                    exerciseAssignment.getGitlabRepositoryTemplateId(),
                    group.getFullPath(),
                    path,
                    projectName);
            // remove the fork link
            teacherGitlabApi.getProjectApi().deleteForkedFromRelationship(project.getId());
        } else if (project == null) {
            // create a blank project
            var projectParams = new Project()
                    .withName(projectName)
                    .withNamespaceId(group.getId());
            project = teacherGitlabApi.getProjectApi().createProject(projectParams);
        }

        // grant the student access to its project
        this.ensureStudentCanAccessItsProject(teacherGitlabApi, project, student);

        return project;
    }

    private void ensureStudentCanAccessItsProject(GitLabApi gitlabApi, Project project, ClassroomUser student) throws GitLabException {
        try {
            var member = gitlabApi.getProjectApi().getMember(project.getId(), student.getGitlabUserId(), true);
            if(member != null){
                // found, student has access
                return;
            }
        } catch (GitLabApiException ignore) {
            // ignoring the 404 exception, meaning that the student doesn't have access to its repository
        }
        // grant the student access to its project
        try {
            gitlabApi.getProjectApi().addMember(project.getId(), student.getGitlabUserId(), AccessLevel.MAINTAINER);
        } catch (GitLabApiException e) {
            var message = String.format("Unable to give student %s access to its GitLab project %s", student.getGitlabUserId(), project.getId());
            throw new GitLabException(message, e);
        }

    }
}

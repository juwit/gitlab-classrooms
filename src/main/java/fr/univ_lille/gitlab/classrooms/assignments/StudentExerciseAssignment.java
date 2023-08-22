package fr.univ_lille.gitlab.classrooms.assignments;

import jakarta.persistence.Entity;

@Entity
public class StudentExerciseAssignment extends StudentAssignment {

    private Long gitlabProjectId;

    private String gitlabProjectUrl;

    public Long getGitlabProjectId() {
        return gitlabProjectId;
    }

    public void setGitlabProjectId(Long gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
    }

    public String getGitlabProjectUrl() {
        return gitlabProjectUrl;
    }

    public void setGitlabProjectUrl(String gitlabProjectUrl) {
        this.gitlabProjectUrl = gitlabProjectUrl;
    }

}

package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.persistence.Entity;

@Entity
public class ExerciseAssignment extends Assignment {

    private String gitlabRepositoryTemplateId;

    private Long gitlabGroupId;

    public ExerciseAssignment() {
        this.setType(AssignmentType.EXERCISE);
    }

    public void setGitlabRepositoryTemplateId(String gitlabTemplateRepository) {
        this.gitlabRepositoryTemplateId = gitlabTemplateRepository;
    }

    public String getGitlabRepositoryTemplateId() {
        return gitlabRepositoryTemplateId;
    }

    public Long getGitlabGroupId() {
        return gitlabGroupId;
    }

    public void setGitlabGroupId(Long gitlabGroupId) {
        this.gitlabGroupId = gitlabGroupId;
    }
}

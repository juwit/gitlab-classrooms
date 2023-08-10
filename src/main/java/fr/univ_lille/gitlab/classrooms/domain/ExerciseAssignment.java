package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.persistence.Entity;

@Entity
public class ExerciseAssignment extends Assignment {

    private String gitlabRepositoryTemplateId;

    public ExerciseAssignment() {
        this.setType(AssignmentType.EXERCISE);
    }

    public void setGitlabRepositoryTemplateId(String gitlabTemplateRepository) {
        this.gitlabRepositoryTemplateId = gitlabTemplateRepository;
    }

    public String getGitlabRepositoryTemplateId() {
        return gitlabRepositoryTemplateId;
    }
}

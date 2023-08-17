package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.assignments.Assignment;
import fr.univ_lille.gitlab.classrooms.assignments.AssignmentType;
import jakarta.persistence.Entity;

@Entity
class ExerciseAssignment extends Assignment {

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

package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.assignments.grading.AssignmentGrade;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.Set;

@Entity
public class StudentExerciseAssignment extends StudentAssignment {

    private Long gitlabProjectId;

    private String gitlabProjectUrl;

    @ElementCollection
    private Set<AssignmentGrade> assignmentGrades = new HashSet<>();

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


    public Set<AssignmentGrade> getAssignmentGrades() {
        return assignmentGrades;
    }

    public void setAssignmentGrades(Set<AssignmentGrade> assignmentGrades) {
        this.assignmentGrades = assignmentGrades;
    }

    @Override
    public Long getScore() {
        return this.assignmentGrades.stream()
                .mapToLong(AssignmentGrade::getScore)
                .sum();
    }

    @Override
    public Long getMaxScore() {
        return this.assignmentGrades.stream()
                .mapToLong(AssignmentGrade::getMaxScore)
                .sum();
    }
}

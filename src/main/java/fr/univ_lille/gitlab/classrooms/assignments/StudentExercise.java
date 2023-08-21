package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
public class StudentExercise {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private ExerciseAssignment assignment;

    @ManyToOne
    private ClassroomUser student;

    private Long gitlabProjectId;

    private String gitlabProjectUrl;

    private ZonedDateTime submissionDate;

    private Long score;

    private Long maxScore;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ExerciseAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(ExerciseAssignment assignment) {
        this.assignment = assignment;
    }

    public ClassroomUser getStudent() {
        return student;
    }

    public void setStudent(ClassroomUser student) {
        this.student = student;
    }

    public ZonedDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(ZonedDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

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

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Long maxScore) {
        this.maxScore = maxScore;
    }
}

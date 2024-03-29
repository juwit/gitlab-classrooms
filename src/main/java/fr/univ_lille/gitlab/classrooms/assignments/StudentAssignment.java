package fr.univ_lille.gitlab.classrooms.assignments;


import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.persistence.*;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class StudentAssignment {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Assignment assignment;

    @ManyToOne
    private ClassroomUser student;

    private ZonedDateTime submissionDate;

    private Long score;

    private Long maxScore;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
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

    public ZonedDateTime getLocalizedSubmissionDate(){
        return submissionDate.withZoneSameInstant(LocaleContextHolder.getTimeZone().toZoneId());
    }

    public void setSubmissionDate(ZonedDateTime submissionDate) {
        this.submissionDate = submissionDate;
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

    public boolean hasBeenSubmitted() {
        return this.submissionDate != null;
    }

    public abstract void resetGrades();
}

package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class QuizScoreId implements Serializable {

    String quizId;

    ClassroomUser classroomUser;

    public QuizScoreId() {
    }

    public QuizScoreId(String quizId, ClassroomUser classroomUser) {
        this.quizId = quizId;
        this.classroomUser = classroomUser;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public ClassroomUser getClassroomUser() {
        return classroomUser;
    }

    public void setClassroomUser(ClassroomUser classroomUser) {
        this.classroomUser = classroomUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizScoreId that = (QuizScoreId) o;
        return new EqualsBuilder().append(quizId, that.quizId).append(classroomUser, that.classroomUser).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(quizId).append(classroomUser).toHashCode();
    }
}

@Entity
@IdClass(QuizScoreId.class)
public class QuizScore {

    @Id
    String quizId;

    @Id
    @ManyToOne
    ClassroomUser classroomUser;

    int submissionCount = 1;

    ZonedDateTime submissionDate = ZonedDateTime.now();

    long score;

    long maxScore;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public ClassroomUser getClassroomUser() {
        return classroomUser;
    }

    public void setClassroomUser(ClassroomUser classroomUser) {
        this.classroomUser = classroomUser;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getSubmissionCount() {
        return submissionCount;
    }

    public void setSubmissionCount(int submissionCount) {
        this.submissionCount = submissionCount;
    }

    public ZonedDateTime getSubmissionDate() {
        return submissionDate.withZoneSameInstant(ZoneId.of("CET"));
    }

    public void setSubmissionDate(ZonedDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public long getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(long maxScore) {
        this.maxScore = maxScore;
    }

}

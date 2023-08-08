package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class QuizScoreId implements Serializable {

    @Id
    String quizId;

    @Id
    @ManyToOne
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
}

@Entity
@IdClass(QuizScoreId.class)
public class QuizScore extends QuizScoreId{

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

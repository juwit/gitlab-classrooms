package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.quiz.QuizEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class QuizAssignment extends Assignment {

    @OneToOne
    private QuizEntity quiz;

    public QuizAssignment() {
        this.setType(AssignmentType.QUIZ);
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }
}

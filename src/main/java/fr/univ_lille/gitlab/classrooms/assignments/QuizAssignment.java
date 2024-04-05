package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.quiz.QuizEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class QuizAssignment extends Assignment {

    @ManyToOne
    private QuizEntity quiz;

    private int maxRetakes = 0;

    public QuizAssignment() {
        this.setType(AssignmentType.QUIZ);
    }

    public QuizEntity getQuiz() {
        return quiz;
    }

    public void setQuiz(QuizEntity quiz) {
        this.quiz = quiz;
    }

    public int getMaxRetakes() {
        return maxRetakes;
    }

    public void setMaxRetakes(int maxRetakes) {
        this.maxRetakes = maxRetakes;
    }

    public boolean allowRetakes(){
        return this.maxRetakes > 0;
    }
}

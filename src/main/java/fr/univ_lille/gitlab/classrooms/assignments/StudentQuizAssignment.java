package fr.univ_lille.gitlab.classrooms.assignments;

import jakarta.persistence.Entity;

@Entity
public class StudentQuizAssignment extends StudentAssignment {

    private int retakes = 0;

    public int getRetakes() {
        return retakes;
    }

    public void setRetakes(int retakes) {
        this.retakes = retakes;
    }

    @Override
    public void resetGrades() {
        this.setSubmissionDate(null);
        this.setScore(null);
        this.setMaxScore(null);
    }

    public boolean canRetake(){
        return this.retakes < ((QuizAssignment)this.getAssignment()).getMaxRetakes();
    }

}

package fr.univ_lille.gitlab.classrooms.assignments;

import jakarta.persistence.Entity;

@Entity
public class StudentQuizAssignment extends StudentAssignment {

    @Override
    public void resetGrades() {
        this.setSubmissionDate(null);
        this.setScore(null);
        this.setMaxScore(null);
    }
}

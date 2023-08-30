package fr.univ_lille.gitlab.classrooms.assignments.grading;

import jakarta.persistence.*;

@Embeddable
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AssignmentGrade {

    @Enumerated(EnumType.STRING)
    private AssignmentGradeType type;

    protected AssignmentGrade() {
    }

    protected AssignmentGrade(AssignmentGradeType type) {
        this.type = type;
    }

    public AssignmentGradeType getType() {
        return type;
    }

    public void setType(AssignmentGradeType type) {
        this.type = type;
    }

    public abstract int getScore();
    public abstract int getMaxScore();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssignmentGrade that)) return false;

        return type == that.type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}

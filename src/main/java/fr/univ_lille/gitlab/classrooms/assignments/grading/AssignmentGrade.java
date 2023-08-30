package fr.univ_lille.gitlab.classrooms.assignments.grading;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AssignmentGrade {

    @Id
    private UUID id = UUID.randomUUID();

    @Enumerated(EnumType.STRING)
    private AssignmentGradeType type;

    protected AssignmentGrade() {
    }

    protected AssignmentGrade(AssignmentGradeType type) {
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

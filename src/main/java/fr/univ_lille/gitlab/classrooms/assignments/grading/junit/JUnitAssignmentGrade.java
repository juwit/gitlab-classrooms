package fr.univ_lille.gitlab.classrooms.assignments.grading.junit;

import fr.univ_lille.gitlab.classrooms.assignments.grading.AssignmentGrade;
import fr.univ_lille.gitlab.classrooms.assignments.grading.AssignmentGradeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.Set;

@Entity
public class JUnitAssignmentGrade extends AssignmentGrade {

    public JUnitAssignmentGrade() {
        super(AssignmentGradeType.JUNIT);
    }

    @ElementCollection
    private Set<JUnitTestSuite> testSuites = new HashSet<>();

    public Set<JUnitTestSuite> getTestSuites() {
        return testSuites;
    }

    public void setTestSuites(Set<JUnitTestSuite> testSuites) {
        this.testSuites = testSuites;
    }

    @Override
    public int getScore() {
        return this.testSuites.stream()
                .mapToInt(JUnitTestSuite::getScore)
                .sum();
    }

    @Override
    public int getMaxScore() {
        return this.testSuites.stream()
                .mapToInt(JUnitTestSuite::getMaxScore)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

package fr.univ_lille.gitlab.classrooms.assignments.grading;

import fr.univ_lille.gitlab.classrooms.assignments.StudentExerciseAssignment;

import java.io.InputStream;

public interface AssignmentGradeService {

    void gradeAssignmentWithJUnitReport(StudentExerciseAssignment studentExerciseAssignment, InputStream inputStream);

}

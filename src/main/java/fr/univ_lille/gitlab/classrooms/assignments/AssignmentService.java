package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.transaction.Transactional;
import org.gitlab4j.api.GitLabApiException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssignmentService {
    Optional<Assignment> getAssignment(UUID id);

    @Transactional
    void acceptAssigment(Assignment assignment, ClassroomUser student) throws GitLabApiException;

    List<StudentExercise> getAssignmentResults(Assignment assignment);

    @Transactional
    Assignment createQuizAssignment(Classroom classroom, String assignmentName, String quizName);

    @Transactional
    Assignment createExerciseAssignment(Classroom classroom, String assignmentName, String repositoryId) throws GitLabApiException;

    StudentExercise getAssignmentResultsForStudent(Assignment assignment, ClassroomUser student);
}

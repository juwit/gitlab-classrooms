package fr.univ_lille.gitlab.classrooms.domain;

import jakarta.transaction.Transactional;
import org.gitlab4j.api.GitLabApiException;

import java.util.Optional;
import java.util.UUID;

public interface AssignmentService {
    Optional<Assignment> getAssignment(UUID id);

    @Transactional
    void acceptAssigment(Assignment assignment, ClassroomUser student);

    @Transactional
    Assignment createQuizAssignment(Classroom classroom, String assignmentName, String quizName);

    @Transactional
    Assignment createExerciseAssignment(Classroom classroom, String assignmentName, String repositoryId) throws GitLabApiException;
}

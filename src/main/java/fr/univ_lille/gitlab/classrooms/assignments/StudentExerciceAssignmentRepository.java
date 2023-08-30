package fr.univ_lille.gitlab.classrooms.assignments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface StudentExerciceAssignmentRepository extends JpaRepository<StudentExerciseAssignment, UUID> {

    Optional<StudentExerciseAssignment> findByGitlabProjectId(Long gitlabProjectId);
}

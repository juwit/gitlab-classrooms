package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface StudentExerciseRepository extends JpaRepository<StudentExercise, UUID> {
    List<StudentExercise> findAllByAssignment(Assignment assignment);

    StudentExercise findByAssignmentAndStudent(Assignment assignment, ClassroomUser student);
}

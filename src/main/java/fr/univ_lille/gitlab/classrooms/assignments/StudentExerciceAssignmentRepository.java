package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.classrooms.Classroom;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface StudentExerciceAssignmentRepository extends JpaRepository<StudentExerciseAssignment, UUID> {

    Optional<StudentExerciseAssignment> findByGitlabProjectId(Long gitlabProjectId);

    List<StudentExerciseAssignment> findByAssignmentClassroomAndStudent(Classroom classroom, ClassroomUser student);
}

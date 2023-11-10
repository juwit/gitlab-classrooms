package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.classrooms.Classroom;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface StudentAssignmentRepository extends JpaRepository<StudentAssignment, UUID> {
    List<StudentAssignment> findAllByAssignment(Assignment assignment);

    boolean existsByAssignmentAndStudent(Assignment assignment, ClassroomUser student);

    StudentAssignment findByAssignmentAndStudent(Assignment assignment, ClassroomUser student);

    List<StudentAssignment> findByAssignmentClassroomAndStudent(Classroom classroom, ClassroomUser student);

    Optional<StudentAssignment> findByAssignmentIdAndStudent(UUID assignmentId, ClassroomUser student);
}

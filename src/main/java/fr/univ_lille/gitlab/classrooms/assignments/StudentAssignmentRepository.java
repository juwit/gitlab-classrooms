package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface StudentAssignmentRepository extends JpaRepository<StudentAssignment, UUID> {
    List<StudentAssignment> findAllByAssignment(Assignment assignment);

    StudentAssignment findByAssignmentAndStudent(Assignment assignment, ClassroomUser student);
}

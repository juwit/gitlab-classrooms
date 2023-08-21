package fr.univ_lille.gitlab.classrooms.assignments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface AssignmentRepository extends JpaRepository<Assignment, UUID> {
}

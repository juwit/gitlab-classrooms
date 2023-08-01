package fr.univ_lille.gitlab.classrooms.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomUserRepository extends CrudRepository<ClassroomUser, String> {
}

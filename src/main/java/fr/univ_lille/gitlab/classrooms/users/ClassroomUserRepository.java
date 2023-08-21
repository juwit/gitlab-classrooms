package fr.univ_lille.gitlab.classrooms.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ClassroomUserRepository extends CrudRepository<ClassroomUser, String> {
}

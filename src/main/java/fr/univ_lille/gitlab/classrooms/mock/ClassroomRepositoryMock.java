package fr.univ_lille.gitlab.classrooms.mock;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
class ClassroomRepositoryMock implements ClassroomRepository {

    Map<String, Classroom> classrooms = new HashMap<>();

    @Override
    public Iterable<Classroom> findAllClassrooms() {
        return classrooms.values();
    }

    @Override
    public void saveClassroom(Classroom classroom) {
        this.classrooms.put(classroom.id(), classroom);
    }
}

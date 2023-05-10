package fr.univ_lille.gitlab.classrooms.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
class ClassroomRepositoryMock implements ClassroomRepository{

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

package fr.univ_lille.gitlab.classrooms.domain;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
class ClassroomRepositoryMock implements ClassroomRepository{

    @Override
    public Iterable<Classroom> findAllClassrooms() {
        var classroom = new Classroom(UUID.randomUUID().toString(),"Test");
        return List.of(classroom);
    }
}

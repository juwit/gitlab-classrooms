package fr.univ_lille.gitlab.classrooms.domain;

public interface ClassroomRepository {

    Iterable<Classroom> findAllClassrooms();

    void saveClassroom(Classroom classroom);
}

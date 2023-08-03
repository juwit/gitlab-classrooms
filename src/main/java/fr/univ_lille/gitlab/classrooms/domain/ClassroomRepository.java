package fr.univ_lille.gitlab.classrooms.domain;

import org.gitlab4j.api.GitLabApiException;

public interface ClassroomRepository {

    Iterable<Classroom> findAllClassrooms() throws GitLabApiException;

    void saveClassroom(Classroom classroom) throws GitLabApiException;
}

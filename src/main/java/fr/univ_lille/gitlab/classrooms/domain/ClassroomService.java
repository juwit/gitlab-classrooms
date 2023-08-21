package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.gitlab4j.api.GitLabApiException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassroomService {

    List<Classroom> getAllClassrooms();

    Optional<Classroom> getClassroom(UUID uuid);

    void joinClassroom(Classroom classroom, ClassroomUser student) throws GitLabApiException;

    void createClassroom(String classroomName, Long parentGitlabGroupId, ClassroomUser teacher) throws GitLabApiException;

    void saveClassroom(Classroom classroom);
}

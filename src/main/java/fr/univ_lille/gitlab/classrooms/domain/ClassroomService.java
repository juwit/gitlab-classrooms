package fr.univ_lille.gitlab.classrooms.domain;

import org.gitlab4j.api.GitLabApiException;

import java.util.List;

public interface ClassroomService {

    List<Classroom> getAllClassrooms();

    void createClassroom(String classroomName, Long parentGitlabGroupId, ClassroomUser teacher) throws GitLabApiException;
}

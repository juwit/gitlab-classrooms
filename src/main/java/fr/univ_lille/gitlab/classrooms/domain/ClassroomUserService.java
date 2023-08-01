package fr.univ_lille.gitlab.classrooms.domain;

public interface ClassroomUserService {
    ClassroomUser loadOrCreateClassroomUser(String name);
}

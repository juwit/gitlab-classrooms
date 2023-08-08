package fr.univ_lille.gitlab.classrooms.domain;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface ClassroomUserService {

    ClassroomUser getClassroomUser(String userId);

    ClassroomUser loadOrCreateClassroomUser(OAuth2User oauth2User);
}

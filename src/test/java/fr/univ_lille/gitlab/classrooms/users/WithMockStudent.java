package fr.univ_lille.gitlab.classrooms.users;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockClassroomUser(username = "luke.skywalker", roles = {ClassroomRole.STUDENT})
public @interface WithMockStudent {
}

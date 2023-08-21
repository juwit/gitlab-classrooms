package fr.univ_lille.gitlab.classrooms.users;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockClassroomUser(username = "obiwan.kenobi", roles = {ClassroomRole.TEACHER})
public @interface WithMockTeacher {
}

package fr.univ_lille.gitlab.classrooms.users;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockClassroomUserSecurityContextFactory.class)
@interface WithMockClassroomUser {
    String username();

    ClassroomRole[] roles();

}

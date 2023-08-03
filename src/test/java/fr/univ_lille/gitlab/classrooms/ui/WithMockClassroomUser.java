package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockClassroomUserSecurityContextFactory.class)
public @interface WithMockClassroomUser {
    String username() default "luke.skywalker";

    ClassroomRole[] roles() default {ClassroomRole.STUDENT};

}

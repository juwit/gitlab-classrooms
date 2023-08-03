package fr.univ_lille.gitlab.classrooms.ui;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.Map;

public class WithMockClassroomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockClassroomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockClassroomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        var authorities = Arrays.stream(customUser.roles())
                .map(it -> new SimpleGrantedAuthority("ROLE_" + it.name()))
                .toList();

        Map<String, Object> attributes = Map.of(
                "name", customUser.username(),
                "avatar_url", ""
        );

        // create a fake gitlab user
        OAuth2User principal = new DefaultOAuth2User(authorities, attributes, "name");
        var auth = new OAuth2AuthenticationToken(principal, principal.getAuthorities(), "gitlab");

        context.setAuthentication(auth);
        return context;
    }
}

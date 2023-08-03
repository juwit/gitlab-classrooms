package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
public class WebSecurityConfiguration implements WebMvcConfigurer {

    private static final String LOGIN_PAGE = "login";

    private ClassroomUserService classroomUserService;

    public WebSecurityConfiguration(ClassroomUserService classroomUserService) {
        this.classroomUserService = classroomUserService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // serve the login page as-is
        registry.addViewController(LOGIN_PAGE).setViewName(LOGIN_PAGE);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(it -> {
                    it.requestMatchers(LOGIN_PAGE).permitAll();
                    it.requestMatchers("images/**").permitAll();
                    it.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2.loginPage("/" + LOGIN_PAGE);
                    oauth2.userInfoEndpoint(userInfo -> userInfo.userService(this.userService()));
                })
                .build();
    }

    @Bean
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService(JdbcOperations jdbcOperations, ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcOperations, clientRegistrationRepository);
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> userService() {
        var delegate = new DefaultOAuth2UserService();

        return userRequest -> {
            var oauth2User = delegate.loadUser(userRequest);

            // load additional authorities
            var classroomUser = this.classroomUserService.loadOrCreateClassroomUser(oauth2User.getName());
            var authorities = classroomUser.getRoles().stream()
                    .map(it -> new SimpleGrantedAuthority("ROLE_" + it.name()))
                    .toList();

            String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                    .getUserNameAttributeName();
            return new DefaultOAuth2User(authorities, oauth2User.getAttributes(), userNameAttributeName);
        };
    }
}

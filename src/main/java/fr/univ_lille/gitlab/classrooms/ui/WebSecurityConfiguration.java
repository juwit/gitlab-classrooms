package fr.univ_lille.gitlab.classrooms.ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.logging.Logger;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration implements WebMvcConfigurer {

    private static final Logger LOGGER = Logger.getLogger(WebSecurityConfiguration.class.getName());

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // serve the login page as-is
        registry.addViewController("login").setViewName("login");
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(it -> {
                    it.requestMatchers("login").permitAll();
                    it.requestMatchers("images/**").permitAll();
                    it.anyRequest().authenticated();
                })
                .oauth2Login(it -> {
                    it.loginPage("/login");
                })
                .build();
    }

    @Bean
    GrantedAuthoritiesMapper grantedAuthoritiesMapper(){
        return authorities -> {
            var mappedAuthorities = new HashSet<GrantedAuthority>();

            authorities.forEach(authority -> {
                if (authority instanceof OAuth2UserAuthority) {
                    // TODO request the database to load authorities for the user
                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }
            });

            return mappedAuthorities;
        };
    }

    @Bean
    OAuth2AuthorizedClientService oAuth2AuthorizedClientService(JdbcOperations jdbcOperations, ClientRegistrationRepository clientRegistrationRepository){
        return new JdbcOAuth2AuthorizedClientService(jdbcOperations, clientRegistrationRepository);
    }

}

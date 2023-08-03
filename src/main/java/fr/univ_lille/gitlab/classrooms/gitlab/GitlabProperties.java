package fr.univ_lille.gitlab.classrooms.gitlab;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gitlab")
public record GitlabProperties(
        String url
) {
}

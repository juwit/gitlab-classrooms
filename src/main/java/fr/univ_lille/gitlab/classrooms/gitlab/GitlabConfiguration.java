package fr.univ_lille.gitlab.classrooms.gitlab;

import org.gitlab4j.api.GitLabApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitlabConfiguration {

    private GitlabProperties gitlabProperties;

    public GitlabConfiguration(GitlabProperties gitlabProperties) {
        this.gitlabProperties = gitlabProperties;
    }

    @Bean
    GitLabApi gitLabApi() {
        return new GitLabApi(gitlabProperties.url(), gitlabProperties.accessToken());
    }

}

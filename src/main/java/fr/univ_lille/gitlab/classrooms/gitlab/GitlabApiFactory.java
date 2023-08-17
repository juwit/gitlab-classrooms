package fr.univ_lille.gitlab.classrooms.gitlab;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApi;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
public class GitlabApiFactory {

    private final GitlabProperties gitlabProperties;

    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    public GitlabApiFactory(GitlabProperties gitlabProperties, OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        this.gitlabProperties = gitlabProperties;
        this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
    }

    /**
     * Builds a Gitlab Api client using the credentials of the given classroom user.
     * @param user
     * @return
     */
    public GitLabApi userGitlabApi(ClassroomUser user){
        var oauth2AuthorizedRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("gitlab")
                .principal(user.getName())
                .build();
        var client = new GitLabApi(gitlabProperties.url(), Constants.TokenType.OAUTH2_ACCESS, "");
        client.setAuthTokenSupplier(() -> {
            var oauth2Client = oAuth2AuthorizedClientManager.authorize(oauth2AuthorizedRequest);
            return oauth2Client.getAccessToken().getTokenValue();
        });
        return client;
    }
}

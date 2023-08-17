package fr.univ_lille.gitlab.classrooms.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassroomUserServiceImplTest {

    @Mock
    private ClassroomUserRepository classroomUserRepository;

    @InjectMocks
    private ClassroomUserServiceImpl classroomUserService;

    @Test
    void loadOrCreateUser_shouldLoadExistingUser() {
        var luke = new ClassroomUser("Luke", List.of(ClassroomRole.TEACHER));
        when(classroomUserRepository.findById("luke-skywalker")).thenReturn(Optional.of(luke));

        var oauth2User = new DefaultOAuth2User(null, Map.of("name", "luke-skywalker"), "name");

        var user = classroomUserService.loadOrCreateClassroomUser(oauth2User);

        assertThat(user).isEqualTo(luke);
    }

    @Test
    void loadOrCreateUser_shouldCreateNonExistingUser() {
        when(classroomUserRepository.findById("luke-skywalker")).thenReturn(Optional.empty());
        when(classroomUserRepository.save(any())).thenAnswer(it -> it.getArgument(0));

        var oauth2User = new DefaultOAuth2User(null, Map.of("name", "luke-skywalker"), "name");

        var user = classroomUserService.loadOrCreateClassroomUser(oauth2User);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("luke-skywalker");
        assertThat(user.getRoles()).isEqualTo(List.of(ClassroomRole.STUDENT));

        verify(classroomUserRepository).save(user);
    }

    @Test
    void loadOrCreateUser_shouldUpdateAvatarURIAndEmail() throws MalformedURLException {
        var luke = new ClassroomUser("luke-skywalker", List.of(ClassroomRole.TEACHER));
        when(classroomUserRepository.findById("luke-skywalker")).thenReturn(Optional.of(luke));

        Map<String, Object> attributes = Map.of(
                "name", "luke-skywalker",
                "avatar_url", "https://gravatar.com/luke",
                "email", "luke@rebels.com"
        );
        var oauth2User = new DefaultOAuth2User(null, attributes, "name");

        var user = classroomUserService.loadOrCreateClassroomUser(oauth2User);

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("luke-skywalker");
        assertThat(user.getRoles()).isEqualTo(List.of(ClassroomRole.TEACHER));
        assertThat(user.getAvatarUrl()).isEqualTo(new URL("https://gravatar.com/luke"));
        assertThat(user.getEmail()).isEqualTo("luke@rebels.com");
    }

}

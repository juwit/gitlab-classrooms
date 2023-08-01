package fr.univ_lille.gitlab.classrooms.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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

        var user = classroomUserService.loadOrCreateClassroomUser("luke-skywalker");

        assertThat(user).isEqualTo(luke);
    }

    @Test
    void loadOrCreateUser_shouldCreateNonExistingUser() {
        when(classroomUserRepository.findById("luke-skywalker")).thenReturn(Optional.empty());
        when(classroomUserRepository.save(any())).thenAnswer(it -> it.getArgument(0));

        var user = classroomUserService.loadOrCreateClassroomUser("luke-skywalker");

        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("luke-skywalker");
        assertThat(user.getRoles()).isEqualTo(List.of(ClassroomRole.STUDENT));

        verify(classroomUserRepository).save(user);
    }

}

package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.gitlab.GitlabApiFactory;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.checkerframework.checker.units.qual.C;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.GitLabApiForm;
import org.gitlab4j.api.models.AccessLevel;
import org.gitlab4j.api.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceImplTest {

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private GitlabApiFactory gitlabApiFactory;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private GitLabApi gitLabApi;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllClassrooms_shouldReturnAllClassrooms() {
        var classrooms = classroomService.getAllClassrooms();

        assertThat(classrooms).isNotNull();

        verify(classroomRepository).findAll();
    }

    @Test
    void joinClassroom_shouldAddTheStudentToTheClassroom() throws GitLabApiException {
        var student = new ClassroomUser();
        student.setName("luke.skywalker");

        var classroom = new Classroom();
        classroom.setGitlabGroupId(12L);

        when(gitlabApiFactory.userGitlabApi(any())).thenReturn(gitLabApi);

        classroomService.joinClassroom(classroom, student);

        assertThat(classroom.getStudents()).contains(student);

        verify(classroomRepository).save(classroom);
    }

    @Test
    void joinClassroom_shouldAddTheStudentToTheGitlabGroup() throws GitLabApiException {
        var student = new ClassroomUser();
        student.setName("luke.skywalker");

        var teacher = new ClassroomUser();
        teacher.setName("obiwan.kenobi");

        var classroom = new Classroom();
        classroom.setTeacher(teacher);
        classroom.setGitlabGroupId(12L);

        when(gitlabApiFactory.userGitlabApi(teacher)).thenReturn(gitLabApi);
        when(gitLabApi.getUserApi().getUser("luke.skywalker")).thenReturn(new User().withId(5L));

        classroomService.joinClassroom(classroom, student);

        verify(gitLabApi.getGroupApi()).addMember(12L, 5L, AccessLevel.DEVELOPER);
    }
}

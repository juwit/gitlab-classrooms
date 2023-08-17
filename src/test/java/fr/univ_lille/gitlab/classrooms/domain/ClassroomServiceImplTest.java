package fr.univ_lille.gitlab.classrooms.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceImplTest {

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @Mock
    private ClassroomRepository classroomRepository;

    @Test
    void getAllClassrooms_shouldReturnAllClassrooms() {
        var classrooms = classroomService.getAllClassrooms();

        assertThat(classrooms).isNotNull();

        verify(classroomRepository).findAll();
    }
}

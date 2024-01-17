package fr.univ_lille.gitlab.classrooms.export;

import fr.univ_lille.gitlab.classrooms.assignments.AssignmentService;
import fr.univ_lille.gitlab.classrooms.assignments.StudentExerciseAssignment;
import fr.univ_lille.gitlab.classrooms.classrooms.Classroom;
import fr.univ_lille.gitlab.classrooms.gitlab.Gitlab;
import fr.univ_lille.gitlab.classrooms.users.ClassroomRole;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.gitlab4j.api.GitLabApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExportServiceImplTest {

    @InjectMocks
    private ExportServiceImpl exportService;

    @Mock
    private AssignmentService assignmentService;

    @Mock
    private Gitlab gitlab;

    @Test
    void listStudentRepositories_ForAMultipleStudentsAndExercises() throws ExportException, GitLabApiException {
        var classroom = new Classroom();
        classroom.setName("Test Classroom");
        classroom.setId(UUID.randomUUID());

        var luke = new ClassroomUser("Luke", List.of(ClassroomRole.STUDENT));
        classroom.join(luke);

        var leia = new ClassroomUser("Leia", List.of(ClassroomRole.STUDENT));
        classroom.join(leia);

        var lukeExercise1 = new StudentExerciseAssignment();
        var lukeExercise2 = new StudentExerciseAssignment();
        var leiaExercise1 = new StudentExerciseAssignment();
        var leiaExercise2 = new StudentExerciseAssignment();

        when(assignmentService.getAllStudentAssignmentsForAClassroom(classroom, luke)).thenReturn(List.of(lukeExercise1, lukeExercise2));
        when(assignmentService.getAllStudentAssignmentsForAClassroom(classroom, leia)).thenReturn(List.of(leiaExercise1, leiaExercise2));

        lukeExercise1.setGitlabCloneUrl("git+ssh://luke-fake-url-exercise-1.git");
        when(gitlab.getAssignmentCloneUrl(lukeExercise2)).thenReturn("git+ssh://luke-fake-url-exercise-2.git");
        leiaExercise1.setGitlabCloneUrl("git+ssh://leia-fake-url-exercise-1.git");
        when(gitlab.getAssignmentCloneUrl(leiaExercise2)).thenReturn("git+ssh://leia-fake-url-exercise-2.git");

        var studentRepositories = exportService.listStudentRepositories(classroom);

        assertThat(studentRepositories)
                .hasSize(2)
                .first()
                .satisfies(it -> {
                    assertThat(it.studentName()).isEqualTo("Leia");
                    assertThat(it.cloneUrls()).containsExactly("git+ssh://leia-fake-url-exercise-1.git", "git+ssh://leia-fake-url-exercise-2.git");
                });
        assertThat(studentRepositories)
                .last()
                .satisfies(it -> {
                    assertThat(it.studentName()).isEqualTo("Luke");
                    assertThat(it.cloneUrls()).containsExactly("git+ssh://luke-fake-url-exercise-1.git", "git+ssh://luke-fake-url-exercise-2.git");
                });
    }
}
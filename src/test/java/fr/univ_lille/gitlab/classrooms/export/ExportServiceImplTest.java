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
    void generateCloneClassroomScript_ForASingleStudentExercise() throws ExportException, GitLabApiException {
        var expectedScript = """
                #!/bin/sh
                
                mkdir -p classroom
                cd classroom
                
                mkdir -p Luke
                cd Luke
                git clone git+ssh://luke-fake-url.git
                cd ..
                
                cd ..
                """;

        var classroom = new Classroom();
        classroom.setName("Test Classroom");
        classroom.setId(UUID.randomUUID());

        var yoda = new ClassroomUser("Yoda", List.of(ClassroomRole.TEACHER));
        classroom.addTeacher(yoda);

        var luke = new ClassroomUser("Luke", List.of(ClassroomRole.STUDENT));
        classroom.join(luke);

        var lukeExercise = new StudentExerciseAssignment();

        when(assignmentService.getAllStudentAssignmentsForAClassroom(classroom, luke)).thenReturn(List.of(lukeExercise));

        when(gitlab.getAssignmentCloneUrl(lukeExercise)).thenReturn(URI.create("git+ssh://luke-fake-url.git"));

        var exported = exportService.generateCloneClassroomScriptByStudent(classroom);

        assertThat(exported).isEqualTo(expectedScript);
    }

    @Test
    void generateCloneClassroomScript_ForAMultipleStudents() throws ExportException, GitLabApiException {
        var expectedScript = """
                #!/bin/sh
                
                mkdir -p classroom
                cd classroom
                
                mkdir -p Leia
                cd Leia
                git clone git+ssh://leia-fake-url.git
                cd ..
                
                mkdir -p Luke
                cd Luke
                git clone git+ssh://luke-fake-url.git
                cd ..
                
                cd ..
                """;

        var classroom = new Classroom();
        classroom.setName("Test Classroom");
        classroom.setId(UUID.randomUUID());

        var yoda = new ClassroomUser("Yoda", List.of(ClassroomRole.TEACHER));
        classroom.addTeacher(yoda);

        var luke = new ClassroomUser("Luke", List.of(ClassroomRole.STUDENT));
        classroom.join(luke);

        var leia = new ClassroomUser("Leia", List.of(ClassroomRole.STUDENT));
        classroom.join(leia);

        var lukeExercise = new StudentExerciseAssignment();
        var leiaExercise = new StudentExerciseAssignment();

        when(assignmentService.getAllStudentAssignmentsForAClassroom(classroom, luke)).thenReturn(List.of(lukeExercise));
        when(assignmentService.getAllStudentAssignmentsForAClassroom(classroom, leia)).thenReturn(List.of(leiaExercise));

        when(gitlab.getAssignmentCloneUrl(lukeExercise)).thenReturn(URI.create("git+ssh://luke-fake-url.git"));
        when(gitlab.getAssignmentCloneUrl(leiaExercise)).thenReturn(URI.create("git+ssh://leia-fake-url.git"));

        var exported = exportService.generateCloneClassroomScriptByStudent(classroom);

        assertThat(exported).isEqualTo(expectedScript);
    }

    @Test
    void generateCloneClassroomScript_ForAMultipleStudentsAndExercises() throws ExportException, GitLabApiException {
        var expectedScript = """
                #!/bin/sh
                
                mkdir -p classroom
                cd classroom
                
                mkdir -p Leia
                cd Leia
                git clone git+ssh://leia-fake-url-exercise-1.git
                git clone git+ssh://leia-fake-url-exercise-2.git
                cd ..
                
                mkdir -p Luke
                cd Luke
                git clone git+ssh://luke-fake-url-exercise-1.git
                git clone git+ssh://luke-fake-url-exercise-2.git
                cd ..
                
                cd ..
                """;

        var classroom = new Classroom();
        classroom.setName("Test Classroom");
        classroom.setId(UUID.randomUUID());

        var yoda = new ClassroomUser("Yoda", List.of(ClassroomRole.TEACHER));
        classroom.addTeacher(yoda);

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

        when(gitlab.getAssignmentCloneUrl(lukeExercise1)).thenReturn(URI.create("git+ssh://luke-fake-url-exercise-1.git"));
        when(gitlab.getAssignmentCloneUrl(lukeExercise2)).thenReturn(URI.create("git+ssh://luke-fake-url-exercise-2.git"));
        when(gitlab.getAssignmentCloneUrl(leiaExercise1)).thenReturn(URI.create("git+ssh://leia-fake-url-exercise-1.git"));
        when(gitlab.getAssignmentCloneUrl(leiaExercise2)).thenReturn(URI.create("git+ssh://leia-fake-url-exercise-2.git"));

        var exported = exportService.generateCloneClassroomScriptByStudent(classroom);

        assertThat(exported).isEqualTo(expectedScript);
    }
}
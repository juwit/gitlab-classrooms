package fr.univ_lille.gitlab.classrooms.assignments;

import fr.univ_lille.gitlab.classrooms.gitlab.GitLabException;
import fr.univ_lille.gitlab.classrooms.gitlab.Gitlab;
import org.springframework.stereotype.Component;

@Component
public class ArchiveAssignmentUseCase {

    private final StudentAssignmentRepository studentAssignmentRepository;

    private final Gitlab gitlab;

    ArchiveAssignmentUseCase(StudentAssignmentRepository studentAssignmentRepository, Gitlab gitlab) {
        this.studentAssignmentRepository = studentAssignmentRepository;
        this.gitlab = gitlab;
    }

    public void archive(Assignment assignment){
        var studentAssignments = this.studentAssignmentRepository.findAllByAssignment(assignment);
        studentAssignments.stream()
                .map(it -> (StudentExerciseAssignment)it)
                .forEach(studentExerciseAssignment -> {
                    try {
                        this.gitlab.archiveProject(studentExerciseAssignment);
                    } catch (GitLabException e) {
                        System.out.println(e.getMessage());
                    }
                });
    }

}

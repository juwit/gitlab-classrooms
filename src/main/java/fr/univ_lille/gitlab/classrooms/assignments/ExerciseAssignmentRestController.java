package fr.univ_lille.gitlab.classrooms.assignments;

import jakarta.annotation.security.RolesAllowed;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RolesAllowed("TEACHER")
public class ExerciseAssignmentRestController {

    private AssignmentService assignmentService;

    private GitLabApi gitlab;

    public ExerciseAssignmentRestController(AssignmentService assignmentService, GitLabApi gitlab) {
        this.assignmentService = assignmentService;
        this.gitlab = gitlab;
    }

    @GetMapping(value = "/assignments/{id}/clone-script", produces = "text/plain")
    @ResponseBody
    String buildGitlabCloneScript(@PathVariable UUID id) {
        var assignment = this.assignmentService.getAssignment(id).orElseThrow();

        var studentAssignments = this.assignmentService.getAssignmentResults(assignment);

        var urls = studentAssignments.stream()
                .map(it -> (StudentExerciseAssignment) it)
                .map(StudentExerciseAssignment::getGitlabProjectId)
                .map(it -> {
                    try {
                        return gitlab.getProjectApi().getProject(it);
                    } catch (GitLabApiException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Project::getSshUrlToRepo)
                .map(it -> "git clone " + it);

        return urls.collect(Collectors.joining("\n"));
    }

    @GetMapping("/assignments/{assignmentId}/archive")
    String archiveAssignment(@PathVariable UUID assignmentId, Model model) {
        var assignment = this.assignmentService.getAssignment(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.assignmentService.archive(assignment);

        return "redirect:/classrooms/" + assignment.getClassroom().getId();
    }

}

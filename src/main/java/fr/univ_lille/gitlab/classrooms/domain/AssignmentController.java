package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.quiz.QuizScoreService;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Namespace;
import org.gitlab4j.api.models.Project;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    private QuizScoreService quizScoreService;

    private GitLabApi gitLabApi;

    public AssignmentController(AssignmentRepository assignmentRepository, QuizScoreService quizScoreService, GitLabApi gitLabApi) {
        this.assignmentRepository = assignmentRepository;
        this.quizScoreService = quizScoreService;
        this.gitLabApi = gitLabApi;
    }

    @GetMapping("/{assignmentId}")
    String viewAssignment(@PathVariable UUID assignmentId, Model model) {
        var assignment = this.assignmentRepository.findById(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (assignment.getType() == AssignmentType.QUIZ) {
            var quizAssignment = (QuizAssignment)assignment;
            // view quiz results for the classroom
            model.addAttribute("quiz", quizAssignment.getQuiz());

            var quizResult = this.quizScoreService.getQuizResultForClassroom(quizAssignment.getQuiz(), assignment.getClassroom());
            model.addAttribute("quizResult", quizResult);

            return "quiz/all-submissions";
        }
        if (assignment.getType() == AssignmentType.EXERCISE ){
            var exerciseAssignment = (ExerciseAssignment)assignment;
            model.addAttribute("exercise", exerciseAssignment);
            return "exercise/all-submissions";
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{assignmentId}/accept")
    String showAcceptAssignment(@PathVariable UUID assignmentId, Model model) {
        var assignment = this.assignmentRepository.findById(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("assignment", assignment);
        return "assignments/accept";
    }

    @PostMapping("/{assignmentId}/accept")
    String acceptAssignment(@PathVariable UUID assignmentId, ClassroomUser student, Model model) throws GitLabApiException {
        var assignment = this.assignmentRepository.findById(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        assignment.accept(student);
        this.assignmentRepository.save(assignment);

        model.addAttribute("assignment", assignment);
        return "assignments/accepted";
    }

}

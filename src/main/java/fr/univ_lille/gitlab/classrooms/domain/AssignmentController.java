package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.quiz.QuizScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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

    private final ClassroomUserService classroomUserService;

    private QuizScoreService quizScoreService;

    public AssignmentController(AssignmentRepository assignmentRepository, ClassroomUserService classroomUserService, QuizScoreService quizScoreService) {
        this.assignmentRepository = assignmentRepository;
        this.classroomUserService = classroomUserService;
        this.quizScoreService = quizScoreService;
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

            return "all-submissions";
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
    String acceptAssignment(@PathVariable UUID assignmentId, Authentication authentication, Model model) {
        var assignment = this.assignmentRepository.findById(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var student = this.classroomUserService.getClassroomUser(authentication.getName());

        assignment.accept(student);

        this.assignmentRepository.save(assignment);

        model.addAttribute("assignment", assignment);
        return "assignments/accepted";
    }

}

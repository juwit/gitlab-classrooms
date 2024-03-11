package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.assignments.AssignmentScoreService;
import fr.univ_lille.gitlab.classrooms.assignments.AssignmentService;
import fr.univ_lille.gitlab.classrooms.assignments.QuizAssignment;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/assignments/{assignmentId}/quiz")
@RolesAllowed("STUDENT")
class QuizAnswerController {

    private final AssignmentService assignmentService;

    private final AssignmentScoreService assignmentScoreService;

    QuizAnswerController(AssignmentService assignmentService, AssignmentScoreService assignmentScoreService) {
        this.assignmentService = assignmentService;
        this.assignmentScoreService = assignmentScoreService;
    }

    @GetMapping("")
    public String showQuiz(Model model, @PathVariable UUID assignmentId, @ModelAttribute("user") ClassroomUser student) {
        var assignment = this.assignmentService.getAssignment(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!(assignment instanceof QuizAssignment quizAssignment)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        var quizEntity = quizAssignment.getQuiz();
        var quiz = Quiz.fromMarkdown(quizEntity.getMarkdownContent(), quizEntity.getName());

        model.addAttribute("quiz", quiz);
        model.addAttribute("assignment", assignment);

        var studentAssignment = this.assignmentService.getAssignmentResultsForStudent(assignment, student);
        if(studentAssignment.hasBeenSubmitted()){
            model.addAttribute("previousSubmission", studentAssignment);
        }

        return "quiz/answer";
    }

    @PostMapping(path = "/submit",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitQuizAnswers(
            Model model,
            @PathVariable UUID assignmentId,
            @RequestParam Map<String, String> quizAnswers,
            @ModelAttribute("user") ClassroomUser student
    ) {
        var assignment = this.assignmentService.getAssignment(assignmentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!(assignment instanceof QuizAssignment quizAssignment)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var quizEntity = quizAssignment.getQuiz();
        var quiz = Quiz.fromMarkdown(quizEntity.getMarkdownContent(), quizEntity.getName());

        quiz.answerQuestions(quizAnswers);

        if (!quiz.isFullyAnswered()) {
            model.addAttribute("quiz", quiz);
            model.addAttribute("assignment", assignment);
            model.addAttribute("message", "Il manque des réponses à certaines questions.");
            return "quiz/answer";
        }

        var score = quiz.score();
        var maxScore= quiz.getQuestions().size();
        this.assignmentScoreService.registerScore(assignment, student, score, maxScore);

        model.addAttribute("quiz", quiz);
        return "quiz/results";
    }
}

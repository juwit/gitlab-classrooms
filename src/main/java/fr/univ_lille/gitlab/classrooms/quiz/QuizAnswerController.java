package fr.univ_lille.gitlab.classrooms.quiz;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizAnswerController {

    QuizRepository quizRepository;

    QuizScoreService quizScoreService;

    public QuizAnswerController(QuizScoreService quizScoreService, QuizRepository quizRepository) {
        this.quizScoreService = quizScoreService;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/{quizId}")
    public String showQuiz(Model model, @PathVariable String quizId, Authentication authentication) {
        var quizEntity = this.quizRepository.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var quiz = Quiz.fromMarkdown(quizEntity.getMarkdownContent(), quizId);

        model.addAttribute("quiz", quiz);

        var studentId = authentication.getName();
        var previousSubmission = this.quizScoreService.getPreviousQuizSubmission(quizId, studentId);
        previousSubmission.ifPresent(submission -> model.addAttribute("previousSubmission", submission));

        return "quiz/answer";
    }

    @PostMapping(path = "/{quizId}/submit",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitQuizAnswers(
            Model model,
            @PathVariable String quizId,
            @RequestParam Map<String, String> quizAnswers,
            Authentication authentication
    ) {
        var quizEntity = this.quizRepository.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var quiz = Quiz.fromMarkdown(quizEntity.getMarkdownContent(), quizId);

        quiz.answerQuestions(quizAnswers);

        if (!quiz.isFullyAnswered()) {
            model.addAttribute("quiz", quiz);
            model.addAttribute("message", "Il manque des réponses à certaines questions.");
            return "quiz/answer";
        }

        var studentId = authentication.getName();
        this.quizScoreService.registerScoreForStudent(quiz, studentId);

        model.addAttribute("quiz", quiz);
        return "quiz/results";
    }
}

package fr.univ_lille.gitlab.classrooms.quiz;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Controller
@RequestMapping("/quiz")
class QuizAnswerController {

    QuizRepository quizRepository;

    QuizScoreService quizScoreService;

    public QuizAnswerController(QuizScoreService quizScoreService, QuizRepository quizRepository) {
        this.quizScoreService = quizScoreService;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/{quizId}")
    public String showQuiz(Model model, @PathVariable String quizId, @ModelAttribute("user") ClassroomUser student) {
        var quizEntity = this.quizRepository.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var quiz = Quiz.fromMarkdown(quizEntity.getMarkdownContent(), quizId);

        model.addAttribute("quiz", quiz);

        var previousSubmission = this.quizScoreService.getPreviousQuizSubmission(quizId, student);
        previousSubmission.ifPresent(submission -> model.addAttribute("previousSubmission", submission));

        return "quiz/answer";
    }

    @PostMapping(path = "/{quizId}/submit",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitQuizAnswers(
            Model model,
            @PathVariable String quizId,
            @RequestParam Map<String, String> quizAnswers,
            @ModelAttribute("user") ClassroomUser student
    ) {
        var quizEntity = this.quizRepository.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var quiz = Quiz.fromMarkdown(quizEntity.getMarkdownContent(), quizId);

        quiz.answerQuestions(quizAnswers);

        if (!quiz.isFullyAnswered()) {
            model.addAttribute("quiz", quiz);
            model.addAttribute("message", "Il manque des réponses à certaines questions.");
            return "quiz/answer";
        }

        this.quizScoreService.registerScoreForStudent(quiz, student);

        model.addAttribute("quiz", quiz);
        return "quiz/results";
    }
}

package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.quiz.Quiz;
import fr.univ_lille.gitlab.classrooms.quiz.QuizRepository;
import fr.univ_lille.gitlab.classrooms.quiz.QuizScore;
import fr.univ_lille.gitlab.classrooms.quiz.QuizScoreRepository;
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
public class QuizController {

    QuizScoreRepository quizScoreRepository;

    QuizRepository quizRepository;

    public QuizController(QuizScoreRepository quizScoreRepository, QuizRepository quizRepository) {
        this.quizScoreRepository = quizScoreRepository;
        this.quizRepository = quizRepository;
    }

    @GetMapping("/{quizId}")
    public String showQuiz(Model model, @PathVariable String quizId) {
        var quizEntity = this.quizRepository.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var quiz = Quiz.fromMarkdown(quizEntity.getMarkdownContent(), quizId);

        model.addAttribute("quiz", quiz);
        return "quiz";
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
            return "quiz";
        }

        // saves the score of the student
        var score = new QuizScore();
        score.setQuizId(quizId);
        score.setStudentId(authentication.getName());
        score.setScore(quiz.score());
        quizScoreRepository.save(score);

        model.addAttribute("quiz", quiz);
        return "quiz-submitted-with-answers-correction";
    }

}

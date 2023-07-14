package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.quiz.Quiz;
import fr.univ_lille.gitlab.classrooms.quiz.QuizScore;
import fr.univ_lille.gitlab.classrooms.quiz.QuizScoreRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    QuizScoreRepository quizScoreRepository;

    public QuizController(QuizScoreRepository quizScoreRepository) {
        this.quizScoreRepository = quizScoreRepository;
    }

    @GetMapping("/{quizId}")
    public String showQuiz(Model model, @PathVariable String quizId) throws IOException {
        var quizContent = new ClassPathResource("/quiz/" + quizId + ".md");

        var quiz = Quiz.fromMarkdown(quizContent.getContentAsString(Charset.defaultCharset()), quizId);
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
    ) throws IOException {
        var quizContent = new ClassPathResource("/quiz/" + quizId + ".md");

        var quiz = Quiz.fromMarkdown(quizContent.getContentAsString(Charset.defaultCharset()), quizId);

        quiz.answerQuestions(quizAnswers);

        if(!quiz.isFullyAnswered()){
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

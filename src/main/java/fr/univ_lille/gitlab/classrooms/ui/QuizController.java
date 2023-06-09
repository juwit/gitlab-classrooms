package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.quiz.Quiz;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @GetMapping("/{quizId}")
    public String showQuiz(Model model, @PathVariable String quizId) throws IOException {
        var quizContent = new ClassPathResource("/quiz/" + quizId + ".md");

        var quiz = Quiz.fromMarkdown(quizContent.getContentAsString(Charset.defaultCharset()), quizId);
        model.addAttribute("quiz", quiz);
        return "quiz";
    }

    @PostMapping(path = "/{quizId}/submit",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitQuizAnswers(Model model, @PathVariable String quizId, @RequestParam Map<String, String> quizAnswers) throws IOException {
        var quizContent = new ClassPathResource("/quiz/" + quizId + ".md");

        var quiz = Quiz.fromMarkdown(quizContent.getContentAsString(Charset.defaultCharset()), quizId);

        quiz.answerQuestions(quizAnswers);

        if(!quiz.isFullyAnswered()){
            model.addAttribute("quiz", quiz);
            model.addAttribute("message", "Il manque des réponses à certaines questions.");
            return "quiz";
        }

        return "quizAnswered";
    }

}

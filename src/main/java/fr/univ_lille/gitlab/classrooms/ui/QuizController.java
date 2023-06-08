package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.quiz.Quiz;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.charset.Charset;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Value("classpath:/quiz/week1.md")
    private Resource week1;

    @GetMapping("/{quizId}")
    public String showQuiz(Model model, @PathVariable String quizId) throws IOException {
        var quiz = Quiz.fromMarkdown(week1.getContentAsString(Charset.defaultCharset()));
        model.addAttribute("quiz", quiz);
        return "quiz";
    }

}

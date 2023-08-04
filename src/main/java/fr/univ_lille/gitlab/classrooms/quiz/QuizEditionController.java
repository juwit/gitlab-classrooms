package fr.univ_lille.gitlab.classrooms.quiz;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/quiz")
@RolesAllowed("TEACHER")
public class QuizEditionController {

    private static final String QUIZ_LIST_PAGE = "quiz/list";
    private static final String QUIZ_EDIT_PAGE = "quiz/edit";

    QuizRepository quizRepository;

    QuizScoreService quizScoreService;

    public QuizEditionController(QuizScoreService quizScoreService, QuizRepository quizRepository) {
        this.quizScoreService = quizScoreService;
        this.quizRepository = quizRepository;
    }

    @GetMapping("")
    public String listQuiz(Model model) {
        model.addAttribute("quizzes", this.quizRepository.findAll(Sort.by("name")));
        return QUIZ_LIST_PAGE;
    }

    @GetMapping("/new/edit")
    public String newQuiz(Model model) {
        model.addAttribute("quiz", new QuizEntity());
        return QUIZ_EDIT_PAGE;
    }

    @GetMapping("/{quizId}/edit")
    public String editQuiz(Model model, @PathVariable String quizId) {
        var quizEntity = this.quizRepository.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("quiz", quizEntity);
        return QUIZ_EDIT_PAGE;
    }

    @PostMapping("/{quizId}/edit")
    public String saveQuiz(@ModelAttribute QuizEntity quiz, Model model, @PathVariable String quizId) {
        this.quizRepository.save(quiz);
        model.addAttribute("quiz", quiz);
        model.addAttribute("successMessage", "Quiz successfully saved");
        return QUIZ_EDIT_PAGE;
    }
}

package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.quiz.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Sort;
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

    private static final String QUIZ_LIST_PAGE = "quiz/list";
    private static final String QUIZ_EDIT_PAGE = "quiz/edit";

    QuizScoreRepository quizScoreRepository;

    QuizRepository quizRepository;

    public QuizController(QuizScoreRepository quizScoreRepository, QuizRepository quizRepository) {
        this.quizScoreRepository = quizScoreRepository;
        this.quizRepository = quizRepository;
    }

    @GetMapping("")
    @RolesAllowed("TEACHER")
    public String listQuiz(Model model){
        model.addAttribute("quizzes", this.quizRepository.findAll(Sort.by("name")));
        return QUIZ_LIST_PAGE;
    }

    @GetMapping("/new/edit")
    @RolesAllowed("TEACHER")
    public String newQuiz(Model model){
        model.addAttribute("quiz", new QuizEntity());
        return QUIZ_EDIT_PAGE;
    }

    @GetMapping("/{quizId}/edit")
    @RolesAllowed("TEACHER")
    public String editQuiz(Model model, @PathVariable String quizId){
        var quizEntity = this.quizRepository.findById(quizId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("quiz", quizEntity);
        return QUIZ_EDIT_PAGE;
    }

    @PostMapping("/{quizId}/edit")
    @RolesAllowed("TEACHER")
    public String saveQuiz(@ModelAttribute QuizEntity quiz, Model model, @PathVariable String quizId){
        this.quizRepository.save(quiz);
        model.addAttribute("quiz", quiz);
        model.addAttribute("successMessage", "Quiz successfully saved");
        return QUIZ_EDIT_PAGE;
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

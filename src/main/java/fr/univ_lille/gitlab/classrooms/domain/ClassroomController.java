package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.quiz.QuizRepository;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
@RequestMapping("/classrooms")
@RolesAllowed("TEACHER")
public class ClassroomController {

    private final ClassroomRepository classroomRepository;

    private final ClassroomUserService classroomUserService;

    private final QuizRepository quizRepository;

    private final AssignmentRepository assignmentRepository;

    public ClassroomController(ClassroomRepository classroomRepository, ClassroomUserService classroomUserService, QuizRepository quizRepository, AssignmentRepository assignmentRepository) {
        this.classroomRepository = classroomRepository;
        this.classroomUserService = classroomUserService;
        this.quizRepository = quizRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping("/new")
    String newClassroom() {
        return "classrooms/new";
    }

    @PostMapping("/new")
    String newClassroom(@RequestParam String classroomName) {
        var classroom = new Classroom();
        classroom.setId(UUID.randomUUID());
        classroom.setName(classroomName);
        classroomRepository.save(classroom);
        return "redirect:/";
    }

    @GetMapping("/{classroomId}")
    String showClassroom(@PathVariable UUID classroomId, Model model){
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);
        return "classrooms/view";
    }

    @GetMapping("/{classroomId}/join")
    @RolesAllowed({"TEACHER","STUDENT"})
    String showJoinClassroom(@PathVariable UUID classroomId, Model model){
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);
        return "classrooms/join";
    }

    @PostMapping("/{classroomId}/join")
    @RolesAllowed({"TEACHER","STUDENT"})
    String joinClassroom(@PathVariable UUID classroomId, Authentication authentication, Model model){
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var student = this.classroomUserService.getClassroomUser(authentication.getName());

        classroom.join(student);

        this.classroomRepository.save(classroom);

        model.addAttribute("classroom", classroom);
        return "classrooms/joined";
    }

    @GetMapping("/{classroomId}/assignments/new")
    String newAssignment(@PathVariable UUID classroomId, Model model){
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);

        model.addAttribute("quizzes", quizRepository.findAll());
        return "assignments/new";
    }

    @PostMapping("/{classroomId}/assignments/new")
    String createAssignment(@PathVariable UUID classroomId, Model model, @RequestParam String assignmentName, @RequestParam String quizName){
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var quiz = this.quizRepository.findById(quizName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var quizAssignment = new QuizAssignment();
        quizAssignment.setName(assignmentName);
        quizAssignment.setQuiz(quiz);

        classroom.assignments.add(quizAssignment);

        this.assignmentRepository.save(quizAssignment);
        this.classroomRepository.save(classroom);

        model.addAttribute("classroom", classroom);
        return "classrooms/view";
    }
}

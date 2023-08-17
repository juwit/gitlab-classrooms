package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.quiz.QuizRepository;
import jakarta.annotation.security.RolesAllowed;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.GroupParams;
import org.springframework.http.HttpStatus;
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

    private final ClassroomService classroomService;

    private final QuizRepository quizRepository;

    private final AssignmentService assignmentService;

    private final GitLabApi gitLabApi;

    public ClassroomController(ClassroomRepository classroomRepository, ClassroomService classroomService, QuizRepository quizRepository, AssignmentService assignmentService, GitLabApi gitLabApi) {
        this.classroomRepository = classroomRepository;
        this.classroomService = classroomService;
        this.quizRepository = quizRepository;
        this.assignmentService = assignmentService;
        this.gitLabApi = gitLabApi;
    }

    @GetMapping("/new")
    String newClassroom(Model model) throws GitLabApiException {
        model.addAttribute("groups", this.gitLabApi.getGroupApi().getGroups());
        return "classrooms/new";
    }

    @PostMapping("/new")
    String newClassroom(@RequestParam String classroomName, @RequestParam(required = false) Long parentGitlabGroupId, @ModelAttribute("user") ClassroomUser teacher) throws GitLabApiException {
        this.classroomService.createClassroom(classroomName, parentGitlabGroupId, teacher);
        return "redirect:/";
    }

    @GetMapping("/{classroomId}")
    String showClassroom(@PathVariable UUID classroomId, Model model) {
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);
        return "classrooms/view";
    }

    @GetMapping("/{classroomId}/join")
    @RolesAllowed({"TEACHER", "STUDENT"})
    String showJoinClassroom(@PathVariable UUID classroomId, Model model) {
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);
        return "classrooms/join";
    }

    @PostMapping("/{classroomId}/join")
    @RolesAllowed({"TEACHER", "STUDENT"})
    String joinClassroom(@PathVariable UUID classroomId, @ModelAttribute("user") ClassroomUser student, Model model) {
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        classroom.join(student);

        this.classroomRepository.save(classroom);

        model.addAttribute("classroom", classroom);
        return "classrooms/joined";
    }

    @GetMapping("/{classroomId}/assignments/new")
    String newAssignment(@PathVariable UUID classroomId, Model model) throws GitLabApiException {
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);

        model.addAttribute("quizzes", quizRepository.findAll());

        model.addAttribute("repositories", this.gitLabApi.getProjectApi().getMemberProjects());
        return "assignments/new";
    }

    record CreateAssignmentDTO(String assignmentName,
                               AssignmentType assignmentType,
                               String quizName,
                               String repositoryId) {

    }

    @PostMapping("/{classroomId}/assignments/new")
    String createAssignment(@PathVariable UUID classroomId, CreateAssignmentDTO createAssignmentDTO) throws GitLabApiException {
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (createAssignmentDTO.assignmentType == AssignmentType.QUIZ) {
            this.assignmentService.createQuizAssignment(classroom, createAssignmentDTO.assignmentName, createAssignmentDTO.quizName);
        } else if (createAssignmentDTO.assignmentType == AssignmentType.EXERCISE) {
            this.assignmentService.createExerciseAssignment(classroom, createAssignmentDTO.assignmentName, createAssignmentDTO.repositoryId);
        }

        return "redirect:/classrooms/"+classroomId;
    }
}

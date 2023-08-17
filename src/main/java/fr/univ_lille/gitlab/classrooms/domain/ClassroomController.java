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

    private final QuizRepository quizRepository;

    private final AssignmentRepository assignmentRepository;

    private final AssignmentServiceImpl assignmentService;

    private final GitLabApi gitLabApi;

    public ClassroomController(ClassroomRepository classroomRepository, QuizRepository quizRepository, AssignmentRepository assignmentRepository, AssignmentServiceImpl assignmentService, GitLabApi gitLabApi) {
        this.classroomRepository = classroomRepository;
        this.quizRepository = quizRepository;
        this.assignmentRepository = assignmentRepository;
        this.assignmentService = assignmentService;
        this.gitLabApi = gitLabApi;
    }

    @GetMapping("/new")
    String newClassroom(Model model) throws GitLabApiException {
        model.addAttribute("groups", this.gitLabApi.getGroupApi().getGroups());
        return "classrooms/new";
    }

    @PostMapping("/new")
    String newClassroom(@RequestParam String classroomName, @RequestParam(required = false) Long parentGitlabGroupId) throws GitLabApiException {
        var classroom = new Classroom();
        classroom.setName(classroomName);

        var groupPath = classroomName.trim().replaceAll("[^\\w\\d\\-_.]", "_");

        var groupParams = new GroupParams()
                .withName(classroomName)
                .withPath(groupPath)
                .withDescription("Gitlab group for the Classroom " + classroomName);
        if(parentGitlabGroupId != null){
            groupParams.withParentId(parentGitlabGroupId);
        }
        var group = this.gitLabApi.getGroupApi().createGroup(groupParams);

        classroom.setGitlabGroupId(group.getId());

        classroomRepository.save(classroom);
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

        switch (createAssignmentDTO.assignmentType) {
            case QUIZ -> this.assignmentService.createQuizAssignment(classroom, createAssignmentDTO.assignmentName, createAssignmentDTO.quizName);
            case EXERCISE -> this.assignmentService.createExerciseAssignment(classroom, createAssignmentDTO.assignmentName, createAssignmentDTO.repositoryId);
        };

        return "redirect:/classrooms/"+classroomId;
    }
}

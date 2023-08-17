package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.annotation.security.RolesAllowed;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
@RequestMapping("/classrooms")
@RolesAllowed("TEACHER")
class ClassroomController {

    private final ClassroomService classroomService;

    private final GitLabApi gitLabApi;

    public ClassroomController(ClassroomService classroomService, GitLabApi gitLabApi) {
        this.classroomService = classroomService;
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
        var classroom = this.classroomService.getClassroom(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);
        return "classrooms/view";
    }

    @GetMapping("/{classroomId}/join")
    @RolesAllowed({"TEACHER", "STUDENT"})
    String showJoinClassroom(@PathVariable UUID classroomId, Model model) {
        var classroom = this.classroomService.getClassroom(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);
        return "classrooms/join";
    }

    @PostMapping("/{classroomId}/join")
    @RolesAllowed({"TEACHER", "STUDENT"})
    String joinClassroom(@PathVariable UUID classroomId, @ModelAttribute("user") ClassroomUser student, Model model) throws GitLabApiException {
        var classroom = this.classroomService.getClassroom(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        this.classroomService.joinClassroom(classroom, student);

        model.addAttribute("classroom", classroom);
        return "classrooms/joined";
    }

}

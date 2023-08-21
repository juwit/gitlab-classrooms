package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.gitlab.Gitlab;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpSession;
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

    private final Gitlab gitlab;

    private static final System.Logger LOGGER = System.getLogger(ClassroomController.class.getName());

    public ClassroomController(ClassroomService classroomService, Gitlab gitlab) {
        this.classroomService = classroomService;
        this.gitlab = gitlab;
    }

    @GetMapping("/new")
    String newClassroom(Model model) throws GitLabApiException {
        model.addAttribute("groups", this.gitlab.getGroupsOfConnectedUser());
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

        try {
            model.addAttribute("gitlabGroupUrl", gitlab.getGroupURI(classroom));
        }
        catch (GitLabApiException e){
            LOGGER.log(System.Logger.Level.ERROR, e.getMessage());
        }

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
    String joinClassroom(@PathVariable UUID classroomId, @ModelAttribute("user") ClassroomUser student, Model model, HttpSession session) {
        var classroom = this.classroomService.getClassroom(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        this.classroomService.joinClassroom(classroom, student);

        var redirect = session.getAttribute("redirect");
        if (redirect != null) {
            session.removeAttribute("redirect");
            return "redirect:" + redirect;
        }

        model.addAttribute("classroom", classroom);
        return "classrooms/joined";
    }

}

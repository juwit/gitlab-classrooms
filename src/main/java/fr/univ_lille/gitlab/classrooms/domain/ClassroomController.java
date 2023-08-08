package fr.univ_lille.gitlab.classrooms.domain;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomRepository;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomUserService;
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

    public ClassroomController(ClassroomRepository classroomRepository, ClassroomUserService classroomUserService) {
        this.classroomRepository = classroomRepository;
        this.classroomUserService = classroomUserService;
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

    @GetMapping("/{classroomId}/join")
    String showJoinClassroom(@PathVariable UUID classroomId, Model model){
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("classroom", classroom);
        return "classrooms/join";
    }

    @PostMapping("/{classroomId}/join")
    String joinClassroom(@PathVariable UUID classroomId, Authentication authentication, Model model){
        var classroom = this.classroomRepository.findById(classroomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var student = this.classroomUserService.getClassroomUser(authentication.getName());

        classroom.join(student);

        this.classroomRepository.save(classroom);

        model.addAttribute("classroom", classroom);
        return "classrooms/joined";
    }

}

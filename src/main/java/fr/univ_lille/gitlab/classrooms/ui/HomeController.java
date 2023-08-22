package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ClassroomService classroomService;

    public HomeController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    @RolesAllowed("TEACHER")
    public String getHomePage(Model model) {
        var classrooms = classroomService.getAllClassrooms();
        model.addAttribute("classrooms", classrooms);
        return "dashboard/teacher-dashboard";
    }

}

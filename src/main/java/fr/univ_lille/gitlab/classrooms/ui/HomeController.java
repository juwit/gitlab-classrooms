package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class HomeController {

    private ClassroomRepository classroomRepository;

    public HomeController(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @GetMapping
    public String getHomePage(Model model) {
        var classrooms = classroomRepository.findAllClassrooms();
        model.addAttribute("classrooms", classrooms);
        return "home";
    }

}

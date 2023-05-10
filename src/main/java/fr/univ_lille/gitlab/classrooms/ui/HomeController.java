package fr.univ_lille.gitlab.classrooms.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    record Classroom(String name){}

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("classrooms", List.of());
        return "home";
    }

}

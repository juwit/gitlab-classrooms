package fr.univ_lille.gitlab.classrooms.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

    @GetMapping("/new")
    String newClassroom(){
        return "classrooms/new";
    }

}

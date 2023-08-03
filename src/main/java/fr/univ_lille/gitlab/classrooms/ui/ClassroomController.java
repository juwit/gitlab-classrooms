package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.Classroom;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomRepository;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

    private ClassroomRepository classroomRepository;

    public ClassroomController(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @GetMapping("/new")
    String newClassroom(){
        return "classrooms/new";
    }

    @PostMapping("/new")
    String newClassroom(@RequestParam String classroomName){
        var classroom = new Classroom(UUID.randomUUID().toString(), classroomName, "");

        try {
            classroomRepository.saveClassroom(classroom);
        } catch (GitLabApiException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create Group in the Gitlab API");
        }

        return "redirect:/";
    }

}

package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomRepository;
import jakarta.annotation.security.RolesAllowed;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/")
public class HomeController {

    private ClassroomRepository classroomRepository;

    public HomeController(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @GetMapping
    @RolesAllowed("TEACHER")
    public String getHomePage(Model model) {
        try{
            var classrooms = classroomRepository.findAllClassrooms();
            model.addAttribute("classrooms", classrooms);
            return "home";
        }
        catch (GitLabApiException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to load Groups from the Gitlab API");
        }
    }

}

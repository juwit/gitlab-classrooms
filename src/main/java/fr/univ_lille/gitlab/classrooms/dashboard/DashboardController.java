package fr.univ_lille.gitlab.classrooms.dashboard;

import fr.univ_lille.gitlab.classrooms.classrooms.ClassroomService;
import fr.univ_lille.gitlab.classrooms.users.ClassroomUser;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashboardController {

    private final ClassroomService classroomService;

    public DashboardController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    @RolesAllowed("TEACHER")
    public String getDashboard(Model model, @ModelAttribute("user") ClassroomUser classroomUser) {
        if(classroomUser.isTeacher()){
            var classrooms = classroomService.getAllClassrooms();
            model.addAttribute("classrooms", classrooms);
            return "dashboard/teacher-dashboard";
        } else {
            var classrooms = classroomService.getAllJoinedClassrooms(classroomUser);
            model.addAttribute("classrooms", classrooms);
            return "dashboard/student-dashboard";
        }
    }

}

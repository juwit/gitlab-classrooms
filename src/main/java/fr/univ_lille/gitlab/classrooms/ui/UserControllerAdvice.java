package fr.univ_lille.gitlab.classrooms.ui;

import fr.univ_lille.gitlab.classrooms.domain.ClassroomUser;
import fr.univ_lille.gitlab.classrooms.domain.ClassroomUserService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.NoSuchElementException;

@ControllerAdvice
public class UserControllerAdvice {

    private final ClassroomUserService classroomUserService;

    public UserControllerAdvice(ClassroomUserService classroomUserService) {
        this.classroomUserService = classroomUserService;
    }

    @ModelAttribute
    void addUser(Model model, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            model.addAttribute("user", authentication.getPrincipal());
        }
    }

    /**
     * This model attribute provides a classroom user to the model, for the controller methods that need to retrieve the currently connected user.
     * @param authenticationPrincipal The current authentication principal
     * @return the ClassroomUser for the authenticationPrincipal
     */
    @ModelAttribute
    ClassroomUser classroomUser(Principal authenticationPrincipal){
        try{
            return this.classroomUserService.getClassroomUser(authenticationPrincipal.getName());
        }
        catch(NoSuchElementException e){
            return null;
        }
    }

}

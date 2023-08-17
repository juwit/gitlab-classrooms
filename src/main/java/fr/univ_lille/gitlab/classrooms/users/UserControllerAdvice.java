package fr.univ_lille.gitlab.classrooms.users;

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

    /**
     * This model attribute provides a classroom user to the model, for the controller methods that need to retrieve the currently connected user.
     * @param authenticationPrincipal The current authentication principal
     * @return the ClassroomUser for the authenticationPrincipal
     */
    @ModelAttribute("user")
    ClassroomUser classroomUser(Principal authenticationPrincipal){
        try{
            return this.classroomUserService.getClassroomUser(authenticationPrincipal.getName());
        }
        catch(NoSuchElementException e){
            return null;
        }
    }

}

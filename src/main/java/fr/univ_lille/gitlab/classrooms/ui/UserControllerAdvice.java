package fr.univ_lille.gitlab.classrooms.ui;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {

    @ModelAttribute
    void addUser(Model model, Authentication authentication){
        if(authentication.isAuthenticated() ){
            model.addAttribute("user", authentication.getPrincipal());
        }
    }

}

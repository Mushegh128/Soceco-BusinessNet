package am.hovall.webclient.controller.advice;

import am.hovall.common.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MainAdvice {

//    @ModelAttribute("currentUser")
//    public User currentUser(@AuthenticationPrincipal CurrentUser currentUser) {
//        if (currentUser == null) {
//            return new User();
//        }
//        return currentUser.getUser();
//    }

    @ModelAttribute
    public void currentUser(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            modelMap.addAttribute("user", currentUser.getUser());
        }

    }

}
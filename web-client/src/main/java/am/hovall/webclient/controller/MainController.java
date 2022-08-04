package am.hovall.webclient.controller;

import am.hovall.common.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String home(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap){
        if (currentUser != null){
            modelMap.addAttribute("user", currentUser.getUser());
        }
        return "index";
    }

    @GetMapping("about")
    public String about(){
        return "about";
    }

}

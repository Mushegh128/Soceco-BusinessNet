package am.hovall.webclient.controller;

import am.hovall.common.request.UserRequest;
import am.hovall.common.security.CurrentUser;
import am.hovall.common.service.RegionService;
import am.hovall.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RegionService regionService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginView(@AuthenticationPrincipal CurrentUser currentUser) {
        return "redirect:/about";
    }

    @GetMapping("/register")
    public String registerPage(ModelMap modelMap) {
        modelMap.addAttribute("regions", regionService.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRequest userRequest, @RequestParam("registerNumber") String registerNumber) {
            userService.registration(userRequest, registerNumber);
            return "redirect:/user/login";
    }
}



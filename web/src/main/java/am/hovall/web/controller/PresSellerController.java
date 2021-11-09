package am.hovall.web.controller;

import am.hovall.common.entity.PresSeller;
import am.hovall.common.exception.UserNotFoundException;
import am.hovall.common.service.CompanyService;
import am.hovall.common.service.PresSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/seller")
public class PresSellerController {

    private final PresSellerService presSellerService;
    private final CompanyService companyService;

    @GetMapping
    public String presSellers(ModelMap modelMap){
        modelMap.addAttribute("presSellers", presSellerService.findAll());
        return "presSellers";
    }

    @GetMapping("/modify/")
    public String seller(@RequestParam("id") Long id, ModelMap modelMap){
        modelMap.addAttribute("seller", presSellerService.findById(id).orElseThrow(UserNotFoundException::new));
        modelMap.addAttribute("companies", companyService.findAllByPresSellerId(id));
        return "seller";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute PresSeller presSeller){
        presSellerService.save(presSeller);
        return "redirect:/seller";
    }

    @GetMapping("/add")
    public String add(){
        return "addSeller";
    }

}

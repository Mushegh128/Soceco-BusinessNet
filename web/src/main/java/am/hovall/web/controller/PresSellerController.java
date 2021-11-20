package am.hovall.web.controller;

import am.hovall.common.request.CompanyRequest;
import am.hovall.common.request.PresSellerRequest;
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
    public String presSellers(ModelMap modelMap) {
        modelMap.addAttribute("presSellers", presSellerService.findAll());
        return "presSellers";
    }

    @GetMapping("/deactivatePresSeller/")
    public String deactivatePresSeller(@ModelAttribute CompanyRequest companyRequest) {
        companyService.deactivatePresSeller(companyRequest);
        return "redirect:/preSeller";
    }

    @GetMapping("/modify/")
    public String seller(@RequestParam("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("seller", presSellerService.findById(id));
        modelMap.addAttribute("companies", companyService.findAllByPresSellerId(id));
        return "seller";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute PresSellerRequest presSellerRequest) {
        presSellerService.save(presSellerRequest);
        return "redirect:/seller";
    }

    @GetMapping("/add")
    public String add() {
        return "addSeller";
    }

}

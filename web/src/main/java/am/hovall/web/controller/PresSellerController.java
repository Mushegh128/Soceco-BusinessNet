package am.hovall.web.controller;

import am.hovall.common.exception.PreSellerNotFoundException;
import am.hovall.common.request.PresSellerRequest;
import am.hovall.common.service.CompanyService;
import am.hovall.common.service.PresSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/preSeller")
public class PresSellerController {

    private final PresSellerService presSellerService;
    private final CompanyService companyService;

    @GetMapping
    public String presSellers(ModelMap modelMap) {
        modelMap.addAttribute("presSellers", presSellerService.findAll());
        return "presSellers";
    }

    @GetMapping("/modify/")
    public String seller(@RequestParam("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("preSeller", presSellerService.findById(id));
        return "editPreSellerPage";
    }

    @PostMapping("/modify")
    public String seller(@ModelAttribute PresSellerRequest presSellerRequest) {
        try {
            presSellerService.update(presSellerRequest);
        } catch (PreSellerNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/preSeller/singlePage/?id=" + presSellerRequest.getId();
    }

    @GetMapping("/singlePage/")
    public String singlePage(@RequestParam("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("preSeller", presSellerService.findById(id));
        modelMap.addAttribute("companies", companyService.findAllByPresSellerId(id));
        return "singlePreSellerPage";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute PresSellerRequest presSellerRequest) {
        presSellerService.save(presSellerRequest);
        return "redirect:/preSeller";
    }

    @GetMapping("/add")
    public String add() {
        return "addSeller";
    }

}

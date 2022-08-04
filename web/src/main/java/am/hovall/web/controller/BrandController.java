package am.hovall.web.controller;

import am.hovall.common.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping()
    public String findAll(ModelMap modelMap) {
        modelMap.addAttribute("brands", brandService.findAll());
        return "brand";
    }

    @PostMapping
    public String addBrand(@RequestParam String title) {
        try {
            brandService.save(title);
        } catch (RuntimeException e) {
            return "redirect:/error";
        }
        return "redirect:/brand";
    }

    @PostMapping("/modify")
    public void modifyProductGroup(@RequestParam("brandId") Long id, @RequestParam("brandTitle") String title) {
        brandService.modifyBrand(id, title);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        brandService.delete(id);
        return "redirect:/brand";
    }
}

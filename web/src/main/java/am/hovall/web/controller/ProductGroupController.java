package am.hovall.web.controller;

import am.hovall.common.service.ProductGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/productGroup")
@RequiredArgsConstructor
public class ProductGroupController {

    private final ProductGroupService productGroupService;

    @GetMapping()
    public String findAll(ModelMap modelMap) {
        modelMap.addAttribute("productGroups", productGroupService.findAll());
        return "productGroup";
    }

    @PostMapping
    public String addProductGroup(@RequestParam String title) {
        try {
            productGroupService.save(title);
        } catch (RuntimeException e) {
            return "redirect:/error";
        }
        return "redirect:/productGroup";
    }

    @PostMapping("/modify")
    public void modifyProductGroup(@RequestParam("groupId") Long id, @RequestParam("groupTitle") String title) {
        productGroupService.modifyProductGroup(id, title);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        productGroupService.delete(id);
        return "redirect:/productGroup";
    }
}

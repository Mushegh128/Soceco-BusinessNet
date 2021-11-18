package am.hovall.web.controller;

import am.hovall.common.entity.ProductCategory;
import am.hovall.common.service.ProductCategoryService;
import am.hovall.common.service.ProductGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productCategory")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;
    private final ProductGroupService productGroupService;

    @GetMapping
    public String findAll(ModelMap modelMap) {
        modelMap.addAttribute("productCategories", productCategoryService.findAll());
        modelMap.addAttribute("productGroups", productGroupService.findAll());
        return "productCategory";
    }

    @PostMapping
    public String addProductCategory(@ModelAttribute ProductCategory productCategory) {
        try {
            productCategoryService.addProductCategory(productCategory);
        } catch (RuntimeException e) {
            return "redirect:/error";
        }
        return "redirect:/productCategory";
    }

    @PostMapping("/modify")
    public void modifyProductCategory(@RequestParam("categoryId") Long id,
                                      @RequestParam("categoryTitle") String title,
                                      @RequestParam("groupTitle") String group) {
        productCategoryService.modifyProductCategory(id, title, group);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        productCategoryService.delete(id);
        return "redirect:/productCategory";
    }

}

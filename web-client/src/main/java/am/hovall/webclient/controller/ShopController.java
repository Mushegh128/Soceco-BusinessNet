package am.hovall.webclient.controller;

import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/shop")
@RequiredArgsConstructor
@Controller
public class ShopController {

    private final ProductService productService;

    @GetMapping
    public String shop(ModelMap modelMap){
        modelMap.addAttribute("allProducts", productService.getAllProducts(Pageable.unpaged()));
        return "shop";
    }

}

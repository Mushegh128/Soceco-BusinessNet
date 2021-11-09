package am.hovall.web.controller;

import am.hovall.common.service.ExcelService;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ExcelService excelService;

    @GetMapping
    public String getAllProducts(ModelMap modelMap){
        modelMap.addAttribute("products", productService.getAllProducts());
        return "product";
    }

    @PostMapping("/import")
    public String addFromExcel(@RequestParam("import") MultipartFile multipartFile){
        try {
            excelService.importProducts(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response){
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");
        ByteArrayInputStream inputStream;
        try {
            inputStream = excelService.exportProducts();
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException | NullPointerException e) {

        }
    }

    @GetMapping("/modify/")
    public String product(@RequestParam("id") Long id, ModelMap modelMap){
        modelMap.addAttribute("product", productService.findById(id));
        return "singleProduct";
    }

}

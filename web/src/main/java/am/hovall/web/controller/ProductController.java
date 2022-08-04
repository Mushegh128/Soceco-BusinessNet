package am.hovall.web.controller;

import am.hovall.common.exception.BarcodeRepeatException;
import am.hovall.common.exception.ProductNotFoundException;
import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;
import am.hovall.common.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;


@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ExcelService excelService;
    private final MadeInCountryService madeInCountryService;
    private final ProductCategoryService productCategoryService;
    private final BrandService brandService;

    @GetMapping
    public String getAllProducts(ModelMap modelMap) {
        modelMap.addAttribute("products", productService.getAllProducts(Pageable.unpaged()));
        modelMap.addAttribute("emptyProduct", new ProductRequest());
        modelMap.addAttribute("madeInCountrys", madeInCountryService.findAll());
        modelMap.addAttribute("productCategorys", productCategoryService.findAll());
        modelMap.addAttribute("brands", brandService.findAll());
        return "product";
    }

    @PostMapping("/import")
    public String addFromExcel(@RequestParam("import") MultipartFile multipartFile) {
        try {
            excelService.importProducts(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");
        ByteArrayInputStream inputStream;
        try {
            inputStream = excelService.exportProducts();
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/deactivate/")
    public String deactivateProduct(@RequestParam("id") Long id) {
        productService.deactivate(id);
        return "redirect:/products";
    }

    @GetMapping("/modify/{id}")
    public String product(@PathVariable("id") Long id, ModelMap modelMap) {
        ProductResponse byId = productService.findById(id);
        if (byId != null) {
            modelMap.addAttribute("product", byId);
            modelMap.addAttribute("madeInCountries", madeInCountryService.findAll());
            modelMap.addAttribute("brands", brandService.findAll());
            modelMap.addAttribute("productCategories", productCategoryService.findAll());
            return "singleProduct";
        }
        return "redirect:/";
    }

    @PostMapping("/modify/")
    public String product(@Valid @ModelAttribute ProductRequest productRequest, @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult) {
        ProductResponse productResponse;
        if (bindingResult.hasErrors()) {
            return "redirect:/modify/" + productRequest.getId();
        }
        try {
            productResponse = productService.update(productRequest);
            if (!multipartFile.isEmpty()) {
                productService.saveImage(multipartFile, productResponse.getId());
            }
        } catch (ProductNotFoundException | IOException e) {
            return "redirect:/products/";
        }
        return "redirect:/products/modify/" + productResponse.getId();
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductRequest productRequest, @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/products/";
        }
        try {
            ProductResponse productResponse = productService.add(productRequest);
            if (!multipartFile.isEmpty()) {
                productService.saveImage(multipartFile, productResponse.getId());
            }
        } catch (BarcodeRepeatException | IOException e) {
            return "redirect:/products/";
        }
        return "redirect:/products/";
    }

}

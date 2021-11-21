package am.hovall.web.controller;

import am.hovall.common.entity.CompanyType;
import am.hovall.common.entity.Discount;
import am.hovall.common.entity.PresSeller;
import am.hovall.common.exception.CompanyNotFoundException;
import am.hovall.common.request.CompanyRequest;
import am.hovall.common.response.CompanyResponse;
import am.hovall.common.response.PresSellerResponse;
import am.hovall.common.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyTypeService companyTypeService;
    private final ExcelService excelService;
    private final DiscountService discountService;
    private final PresSellerService presSellerService;

    @GetMapping
    private String companies(ModelMap modelMap) {
        List<CompanyResponse> companyResponses = companyService.findAll();
        List<CompanyType> companyTypes = companyTypeService.findAll();
        List<Discount> discounts = discountService.findAll();
        List<PresSellerResponse> presSellers = presSellerService.findAll();
        if (companyTypes != null && discounts != null) {
            modelMap.addAttribute("companyTypes", companyTypes);
            modelMap.addAttribute("discounts", discounts);
            modelMap.addAttribute("presSellers", presSellers);
            modelMap.addAttribute("companies", companyResponses);
        }
        return "companies";
    }

    @GetMapping("/sortByNameDesc")
    public String sortCompanyByNameDesc(ModelMap modelMap) {
        List<CompanyResponse> byOrderByNameDesc = companyService.findByOrderByNameDesc();
        modelMap.addAttribute("companies", byOrderByNameDesc);
        return "/companies";
    }

    @GetMapping("/sortByNameAsc")
    public String sortCompanyByNameAsc(ModelMap modelMap) {
        List<CompanyResponse> byOrderByNameAsc = companyService.findByOrderByNameAsc();
        modelMap.addAttribute("companies", byOrderByNameAsc);
        return "/companies";
    }

    @GetMapping("/searchByName")
    public String searchByName(@Param("name") String name, ModelMap modelMap) {
        try {
            CompanyResponse byName = companyService.findByName(name);
            modelMap.addAttribute("companies", byName);
        } catch (CompanyNotFoundException e) {
            return "/companies";
        }

        return "/companies";
    }


    @PostMapping("/import")
    public String addFromExcel(@RequestParam("import") MultipartFile multipartFile) {
        try {
            excelService.importCompanies(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/company";
    }


    @GetMapping("/modify/")
    public String company(@RequestParam("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("company", companyService.findById(id));
        List<CompanyType> companyTypes = companyTypeService.findAll();
        List<Discount> discounts = discountService.findAll();
        List<PresSellerResponse> presSellers = presSellerService.findAll();
        if (companyTypes != null && discounts != null) {
            modelMap.addAttribute("companyTypes", companyTypes);
            modelMap.addAttribute("discounts", discounts);
            modelMap.addAttribute("presSellers", presSellers);
        }
        return "editCompanyPage";
    }

    @PostMapping("/modify/")
    public String updateCompany(@ModelAttribute CompanyRequest companyRequest) throws CompanyNotFoundException {
        try {
            companyService.update(companyRequest);
        } catch (CompanyNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return "redirect:/company";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute CompanyRequest companyRequest) {
        companyService.saveCompany(companyRequest);
        return "redirect:/company";
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        List<CompanyType> companyTypes = companyTypeService.findAll();
        List<Discount> discounts = discountService.findAll();
        List<PresSellerResponse> presSellers = presSellerService.findAll();
        if (companyTypes != null && discounts != null) {
            modelMap.addAttribute("companyTypes", companyTypes);
            modelMap.addAttribute("discounts", discounts);
            modelMap.addAttribute("presSellers", presSellers);
        }
        return "company";
    }


}

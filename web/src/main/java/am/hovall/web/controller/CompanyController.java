package am.hovall.web.controller;

import am.hovall.common.response.CompanyResponse;
import am.hovall.common.service.CompanyService;
import am.hovall.common.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final ExcelService excelService;

    @GetMapping
    private String companies(ModelMap modelMap) {
        List<CompanyResponse> companyResponses = companyService.findAll();
        modelMap.addAttribute("companies", companyResponses);
        return "companies";
    }

    @PostMapping("/import")
    public String addFromExcel(@RequestParam("import") MultipartFile multipartFile){
        try {
            excelService.importCompanies(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/company";
    }

    @GetMapping("/modify/")
    public String company(@RequestParam("id") Long id, ModelMap modelMap){
        modelMap.addAttribute("company", companyService.findById(id));
        return "company";
    }

}

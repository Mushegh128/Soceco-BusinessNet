package am.hovall.rest.endpoint;

import am.hovall.common.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelParserEndpoint {

    private final ExcelService excelService;

    @PostMapping("/upload/products")
    public ResponseEntity<?> uploadProducts(@RequestBody MultipartFile file) {
        try {
            excelService.importProducts(file);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload/companies")
    public ResponseEntity<?> uploadCompanies(@RequestParam MultipartFile file) {
        try {
            excelService.importCompanies(file);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/download/products", produces = "application/octet-stream")
    public ResponseEntity<?> downloadProducts(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");
        ByteArrayInputStream inputStream;
        try {
            inputStream = excelService.exportProducts();
            IOUtils.copy(inputStream, response.getOutputStream());
            return ResponseEntity.ok().build();
        } catch (IOException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/download/orders", produces = "application/octet-stream")
    public ResponseEntity<?> downloadOrders(@RequestParam String orderStatus, @RequestParam long id, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=orders.xlsx");
        ByteArrayInputStream inputStream;
        try {
            inputStream = excelService.exportOrdersByStatus(orderStatus, id);
            IOUtils.copy(inputStream, response.getOutputStream());
            return ResponseEntity.ok().build();
        } catch (IOException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/download/companies", produces = "application/octet-stream")
    public ResponseEntity<?> downloadCompanies(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=companies.xlsx");
        ByteArrayInputStream inputStream;
        try {
            inputStream = excelService.exportCompanies();
            IOUtils.copy(inputStream, response.getOutputStream());
            return ResponseEntity.ok().build();
        } catch (IOException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

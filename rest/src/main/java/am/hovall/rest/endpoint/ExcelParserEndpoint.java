package am.hovall.rest.endpoint;

import am.hovall.common.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelParserEndpoint {

    private final ExcelService excelService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile[] files) {
        try {
            excelService.excelToProduct(files);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download/products")
    public ResponseEntity<?> exportProducts(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
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

    @GetMapping("/download/orders")
    public ResponseEntity<?> downloadOrders(@RequestParam String orderStatus,@RequestParam long id, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=orders.xlsx");
        ByteArrayInputStream inputStream;
        try {
            inputStream = excelService.exportOrdersByStatus(orderStatus,id);
            IOUtils.copy(inputStream, response.getOutputStream());
            return ResponseEntity.ok().build();
        } catch (IOException | NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

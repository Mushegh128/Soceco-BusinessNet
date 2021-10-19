package am.hovall.rest.endpoint;

import am.hovall.common.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

}

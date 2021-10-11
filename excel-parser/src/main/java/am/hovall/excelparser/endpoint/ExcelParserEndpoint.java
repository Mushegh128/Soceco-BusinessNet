package am.hovall.excelparser.endpoint;

import am.hovall.excelparser.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("http://localhost:8081")
@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelParserEndpoint {

    private final ExcelService excelService;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("files") MultipartFile[] files) {
//        for (MultipartFile file : files) {
//            if (ExcelParserConfig.hasExcelFormat(file)) {
        try {
            excelService.saveMultipleFiles(files);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
//            }
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}

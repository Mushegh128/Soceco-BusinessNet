package am.hovall.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

    void excelToProduct(MultipartFile[] files);
}

package am.hovall.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ExcelService {

    void excelToProduct(MultipartFile[] files);

    ByteArrayInputStream exportProducts() throws IOException;
}

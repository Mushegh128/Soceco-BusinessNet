package am.hovall.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ExcelService {

    void importProducts(MultipartFile file) throws Exception;

    void importCompanies(MultipartFile file) throws Exception;

    ByteArrayInputStream exportOrdersByStatus(String orderStatus, long id) throws IOException;

    ByteArrayInputStream exportProducts() throws IOException;

    ByteArrayInputStream exportCompanies() throws IOException;

}

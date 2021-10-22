package am.hovall.common.service;

import am.hovall.common.entity.OrderStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ExcelService {

    void excelToProduct(MultipartFile[] files);

    ByteArrayInputStream exportOrdersByStatus(String orderStatus,long id) throws IOException;
}

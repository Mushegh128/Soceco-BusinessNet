package am.hovall.excelparser.service;

import am.hovall.common.entity.Product;
import am.hovall.common.repository.ProductRepository;
import am.hovall.excelparser.config.ExcelParserConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final ProductRepository productRepository;
    private final ExcelParserConfig excelParserConfig;

    public void saveMultipleFiles(MultipartFile[] files) {
        List<Product> products = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                products.addAll(excelParserConfig.excelToProduct(file.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productRepository.saveAll(products);
    }

}

package am.hovall.rest.service;

import am.hovall.common.service.ExcelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class ExcelServiceImplTest {

    @Autowired
    private ExcelService excelService;
    private final ExcelService mock = mock(ExcelService.class);

    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Test
    void importProducts() throws Exception {
        MockMultipartFile file = new MockMultipartFile("products", "products.xlsx", TYPE,
                new ClassPathResource("products.xlsx").getInputStream());
        MockMultipartFile[] files = new MockMultipartFile[]{file};
        excelService.importProducts(files);
    }

    @Test
    void importProducts_if_file_type_is_wrong() throws Exception {
        MockMultipartFile file = new MockMultipartFile("products", "products.xlsx", TYPE + "t",
                new ClassPathResource("products.xlsx").getInputStream());
        MockMultipartFile[] files = new MockMultipartFile[]{file};
        doThrow(new Exception()).when(mock).importProducts(files);
    }

    @Test
    void importCompanies() throws Exception {
        MockMultipartFile file = new MockMultipartFile("companies", "companies.xlsx", TYPE,
                new ClassPathResource("companies.xlsx").getInputStream());
        excelService.importCompanies(file);
    }

    @Test
    void importCompanies_if_file_type_is_wrong() throws Exception {
        MockMultipartFile file = new MockMultipartFile("companies", "companies.xlsx", TYPE + "t",
                new ClassPathResource("companies.xlsx").getInputStream());
        doThrow(new Exception()).when(mock).importCompanies(file);

    }

    @Test
    void exportOrdersByStatus_inTime_wrong_status() throws IOException {
        doThrow(new NullPointerException()).when(mock).exportOrdersByStatus("SOLD_DEBBT", 1);
    }

    @Test
    void exportOrdersByStatus_when_company_not_found() throws IOException {
        doThrow(new NullPointerException()).when(mock).exportOrdersByStatus("SOLD_DEBT", 55);
    }

    @Test
    void exportOrdersByStatus_when_found() throws IOException {
        ByteArrayInputStream sold_debt = excelService.exportOrdersByStatus("SOLD_DEBT", 1);
        assertNotNull(sold_debt);
    }

    @Test
    void exportProducts_when_found() throws IOException {
        ByteArrayInputStream products = excelService.exportProducts();
        assertNotNull(products);
    }

}

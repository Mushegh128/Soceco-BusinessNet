package am.hovall.rest.endpoint;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ExcelParserEndpointTest {

    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    void uploadProducts() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "excelFilesForTest/products.xlsx", TYPE,
                new ClassPathResource("excelFilesForTest/products.xlsx").getInputStream());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/excel/upload/products")
                        .file(file)
                        .param("files", "1")
                )
                .andExpect(status().is(200));
    }

    @Test
    void uploadProducts_wrong_file_type() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "excelFilesForTest/products.xlsx", TYPE + "1",
                new ClassPathResource("excelFilesForTest/products.xlsx").getInputStream());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/excel/upload/products")
                        .file(file)
                        .param("files", "1")
                )
                .andExpect(status().is(500));
    }

    @Test
    void uploadProducts_if_file_is_empty() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "excelFilesForTest/emptyProducts.xlsx", TYPE + "1",
                new ClassPathResource("excelFilesForTest/emptyProducts.xlsx").getInputStream());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/excel/upload/products")
                        .file(file)
                        .param("files", "1")
                )
                .andExpect(status().is(500));
    }

    @Test
    void uploadCompanies() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "excelFilesForTest/companies.xlsx", TYPE,
                new ClassPathResource("excelFilesForTest/companies.xlsx").getInputStream());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/excel/upload/companies")
                        .file(file)
                        .param("file", "1")
                )
                .andExpect(status().is(200));
    }

    @Test
    void uploadCompanies_wrong_file_type() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "excelFilesForTest/companies.xlsx", TYPE + "1",
                new ClassPathResource("excelFilesForTest/companies.xlsx").getInputStream());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/excel/upload/companies")
                        .file(file)
                        .param("file", "1")
                )
                .andExpect(status().is(500));
    }

    @Test
    void uploadCompanies_if_file_is_empty() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "excelFilesForTest/emptyCompanies.xlsx", TYPE + "1",
                new ClassPathResource("excelFilesForTest/emptyCompanies.xlsx").getInputStream());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/excel/upload/companies")
                        .file(file)
                        .param("file", "1")
                )
                .andExpect(status().is(500));
    }

}
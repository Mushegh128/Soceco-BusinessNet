package am.hovall.common.service.impl;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.MadeInCountry;
import am.hovall.common.entity.Product;
import am.hovall.common.entity.ProductCategory;
import am.hovall.common.repository.BrandRepository;
import am.hovall.common.repository.MadeInCountryRepository;
import am.hovall.common.repository.ProductCategoryRepository;
import am.hovall.common.repository.ProductRepository;
import am.hovall.common.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private final ProductCategoryRepository productCategoryRepository;
    private final MadeInCountryRepository madeInCountryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    private boolean hasExcelFormat(MultipartFile file) {
        String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        return TYPE.equals(file.getContentType());
    }

    @Override
    public void excelToProduct(MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (!hasExcelFormat(file)) {
                try {
                    throw new FileUploadException();
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
            }
            try {
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rows = sheet.iterator();
                List<Product> products = new ArrayList<>();

                int rowNumber = 0;
                while (rows.hasNext()) {
                    Row currentRow = rows.next();
                    // skip header
                    if (rowNumber == 0) {
                        rowNumber++;
                        continue;
                    }
                    Iterator<Cell> cellsInRow = currentRow.iterator();
                    Product product = new Product();
                    product.setActive(true);
                    int cellIdx = 0;
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();
                        switch (cellIdx) {
                            case 0:
                                product.setBarcode((long) currentCell.getNumericCellValue());
                                break;
                            case 1:
                                product.setTitle(currentCell.getStringCellValue());
                                break;
                            case 2:
                                product.setDescription(currentCell.getStringCellValue());
                                break;
                            case 3:
                                product.setPrice(currentCell.getNumericCellValue());
                                break;
                            case 4:
                                product.setWeight(currentCell.getNumericCellValue());
                                break;
                            case 5:
                                MadeInCountry countryByTitle = madeInCountryRepository.findByTitle(currentCell.getStringCellValue().toLowerCase(Locale.ROOT));
                                if (countryByTitle != null) {
                                    product.setMadeInCountry(countryByTitle);
                                } else {
                                    MadeInCountry savedCountry = madeInCountryRepository.save(new MadeInCountry(currentCell.getStringCellValue().toLowerCase(Locale.ROOT)));
                                    product.setMadeInCountry(savedCountry);
                                }
                                break;
                            case 6:
                                ProductCategory byTitle = productCategoryRepository.findByTitle(currentCell.getStringCellValue().toLowerCase(Locale.ROOT));
                                if (byTitle != null) {
                                    product.setProductCategory(byTitle);
                                } else {
                                    ProductCategory savedProduct = productCategoryRepository.save(new ProductCategory(currentCell.getStringCellValue().toLowerCase(Locale.ROOT)));
                                    product.setProductCategory(savedProduct);
                                }
                                break;
                            case 7:
                                Brand brandFromDb = brandRepository.findByTitle(currentCell.getStringCellValue().toLowerCase(Locale.ROOT));
                                if (brandFromDb != null) {
                                    product.setBrand(brandFromDb);
                                } else {
                                    Brand savedBrand = brandRepository.save(new Brand(currentCell.getStringCellValue().toLowerCase(Locale.ROOT)));
                                    product.setBrand(savedBrand);
                                }
                                break;
                            default:
                                break;
                        }
                        cellIdx++;
                    }
                    products.add(product);
                }
                workbook.close();
                productRepository.saveAll(products);
            } catch (IOException e) {
                throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
            }
        }

    }
}

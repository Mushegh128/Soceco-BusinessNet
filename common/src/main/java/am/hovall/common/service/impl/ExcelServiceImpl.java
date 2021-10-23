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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final ProductCategoryRepository productCategoryRepository;
    private final MadeInCountryRepository madeInCountryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

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
                                long barcodeFromFile = (long) currentCell.getNumericCellValue();
                                Optional<Product> byBarcode = productRepository.findByBarcode(barcodeFromFile);
                                if (byBarcode.isPresent()) {
                                    product = byBarcode.get();
                                }
                                product.setBarcode(barcodeFromFile);
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
                                String madeInCountry = currentCell.getStringCellValue().toLowerCase(Locale.ROOT);
                                if (madeInCountry.isEmpty() || madeInCountry.isBlank()) {
                                    break;
                                }
                                MadeInCountry countryByTitle = madeInCountryRepository.findByTitle(madeInCountry);
                                if (countryByTitle != null) {
                                    product.setMadeInCountry(countryByTitle);
                                } else {
                                    MadeInCountry savedCountry = madeInCountryRepository.save(new MadeInCountry(madeInCountry));
                                    product.setMadeInCountry(savedCountry);
                                }
                                break;
                            case 6:
                                String productCategory = currentCell.getStringCellValue().toLowerCase(Locale.ROOT);
                                if (productCategory.isEmpty() || productCategory.isBlank()) {
                                    break;
                                }
                                ProductCategory byTitle = productCategoryRepository.findByTitle(productCategory);
                                if (byTitle != null) {
                                    product.setProductCategory(byTitle);
                                } else {
                                    ProductCategory savedProduct = productCategoryRepository.save(new ProductCategory(productCategory));
                                    product.setProductCategory(savedProduct);
                                }
                                break;
                            case 7:
                                String brand = currentCell.getStringCellValue().toLowerCase(Locale.ROOT);
                                if (brand.isEmpty() || brand.isBlank()) {
                                    break;
                                }
                                Brand brandFromDb = brandRepository.findByTitle(brand);
                                if (brandFromDb != null) {
                                    product.setBrand(brandFromDb);
                                } else {
                                    Brand savedBrand = brandRepository.save(new Brand(brand));
                                    product.setBrand(savedBrand);
                                }
                                break;
                            case 8:
                                product.setCreatedDateTime(LocalDateTime.parse(currentCell.getStringCellValue()));
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
                throw new RuntimeException("failed to parse Excel file: " + e.getMessage());
            }
        }
    }

    @Override
    public ByteArrayInputStream exportProducts() throws IOException {
        List<Product> productList = productRepository.findAll();
        if (productList == null) {
            throw new NullPointerException();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillBackgroundColor(GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);

        Row productsHeaderRow = sheet.createRow(0);
        productsHeaderRow.setRowStyle(cellStyle);

        Cell headerRowCell = productsHeaderRow.createCell(0);
        headerRowCell.setCellValue("ID");
        headerRowCell = productsHeaderRow.createCell(1);
        headerRowCell.setCellValue("Շտրիխկոդ");
        headerRowCell = productsHeaderRow.createCell(2);
        headerRowCell.setCellValue("Անվանում");
        headerRowCell = productsHeaderRow.createCell(3);
        headerRowCell.setCellValue("Նկարագրություն");
        headerRowCell = productsHeaderRow.createCell(4);
        headerRowCell.setCellValue("Գին");
        headerRowCell = productsHeaderRow.createCell(5);
        headerRowCell.setCellValue("Քաշ/քանակ");
        headerRowCell = productsHeaderRow.createCell(6);
        headerRowCell.setCellValue("Արտադրման Երկիր");
        headerRowCell = productsHeaderRow.createCell(7);
        headerRowCell.setCellValue("Կատեգորիա");
        headerRowCell = productsHeaderRow.createCell(8);
        headerRowCell.setCellValue("Ապրանքանիշը");
        headerRowCell = productsHeaderRow.createCell(9);
        headerRowCell.setCellValue("Արտադրման ամսաթիվը");

        int count = 1;
        for (Product product : productList) {
            Row productDataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell productDataCell = productDataRow.createCell(0);
            productDataCell.setCellValue(count);
            productDataCell = productDataRow.createCell(1);
            productDataCell.setCellValue(product.getBarcode());
            productDataCell = productDataRow.createCell(2);
            if (product.getTitle() != null) {
                productDataCell.setCellValue(product.getTitle());
            } else {
                productDataCell.setBlank();
            }
            productDataCell = productDataRow.createCell(3);
            if (product.getDescription() != null) {
                productDataCell.setCellValue(product.getDescription());
            } else {
                productDataCell.setBlank();
            }
            productDataCell = productDataRow.createCell(4);
            if (product.getProductCategory() != null) {
                productDataCell.setCellValue(product.getPrice());
            } else {
                productDataCell.setBlank();
            }
            productDataCell = productDataRow.createCell(5);
            if (product.getWeight() != 0) {
                productDataCell.setCellValue(product.getWeight());
            } else {
                productDataCell.setBlank();
            }
            productDataCell = productDataRow.createCell(6);
            if (product.getMadeInCountry() != null) {
                productDataCell.setCellValue(product.getMadeInCountry().getTitle());
            } else {
                productDataCell.setBlank();
            }
            productDataCell = productDataRow.createCell(7);
            if (product.getProductCategory() != null) {
                productDataCell.setCellValue(product.getProductCategory().getTitle());
            } else {
                productDataCell.setBlank();
            }
            productDataCell = productDataRow.createCell(8);
            if (product.getBrand() != null) {
                productDataCell.setCellValue(product.getBrand().getTitle());
            } else {
                productDataCell.setBlank();
            }
            productDataCell = productDataRow.createCell(9);
            if (product.getCreatedDateTime() != null) {
                productDataCell.setCellValue(product.getCreatedDateTime().toString());
            } else {
                productDataCell.setBlank();
            }
            count += 1;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}

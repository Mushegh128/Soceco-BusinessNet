package am.hovall.common.service.impl;

import am.hovall.common.entity.*;
import am.hovall.common.repository.*;
import am.hovall.common.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {

    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private final ProductCategoryRepository productCategoryRepository;
    private final MadeInCountryRepository madeInCountryRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

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
                throw new RuntimeException("failed to parse Excel file: " + e.getMessage());
            }
        }
    }

    @Override
    public ByteArrayInputStream exportOrdersByStatus(String orderStatus, long id) throws IOException {
        List<Order> orderList = orderRepository.findAllByCompanyIdAndOrderStatus(id, OrderStatus.valueOf(orderStatus));
        if (orderList == null) {
            throw new NullPointerException();
        }
        Workbook workbook = new XSSFWorkbook();

        // create sheets on workbook which every sheet name will be company name
        for (Order order : orderList) {
            if (workbook.getSheet(order.getCompany().getName() + "_" + order.getId()) == null) {
                workbook.createSheet(order.getCompany().getName() + "_" + order.getId());
            }
        }

        for (Sheet sheet : workbook) {
            for (Order order : orderList) {
                if (sheet.getSheetName().equals(order.getCompany().getName() + "_" + order.getId()) && sheet.getRow(0) == null) {

                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setAlignment(HorizontalAlignment.LEFT);

                    Row barcodeRow = sheet.createRow(0);
                    barcodeRow.setRowStyle(cellStyle);
                    Cell cell = barcodeRow.createCell(0);
                    cell.setCellValue("Ընկերության շտրիխկոդը");
                    cell = barcodeRow.createCell(3);
                    cell.setCellValue(order.getCompany().getBarcode());

                    Row nameRow = sheet.createRow(1);
                    cell = nameRow.createCell(0);
                    cell.setCellValue("Ընկերության անունը");
                    cell = nameRow.createCell(3);
                    cell.setCellValue(order.getCompany().getName());

                    Row addressRow = sheet.createRow(2);
                    cell = addressRow.createCell(0);
                    cell.setCellValue("Ընկերության հասցեն");
                    cell = addressRow.createCell(3);
                    cell.setCellValue(order.getCompany().getAddress());

                    Row regNumberRow = sheet.createRow(3);
                    regNumberRow.setRowStyle(cellStyle);
                    cell = regNumberRow.createCell(0);
                    cell.setCellValue("Ընկերության ՀՎՀՀ");
                    cell = regNumberRow.createCell(3);
                    cell.setCellValue(order.getCompany().getRegisterNumber());

                    Row presSellerRow = sheet.createRow(4);
                    cell = presSellerRow.createCell(0);
                    cell.setCellValue("Շուկայի Մենեջեր");
                    cell = presSellerRow.createCell(3);
                    cell.setCellValue(order.getCompany().getPresSeller().getName());

                    Row orderedDateRow = sheet.createRow(5);
                    cell = orderedDateRow.createCell(0);
                    cell.setCellValue("Երբ է պատվիրվել");
                    cell = orderedDateRow.createCell(3);
                    cell.setCellValue(order.getCreatedDateTime().toString());
                }
            }
        }

        // foreach loop on sheets, find sheet by order company name and add data
        for (Sheet createdSheet : workbook) {
            for (Order order : orderList) {
                if (createdSheet.getSheetName().equals(order.getCompany().getName() + "_" + order.getId())) {
                    // create product orders row
                    Row productOrderRow = createdSheet.createRow(createdSheet.getLastRowNum() + 2);

                    // set Ordered Product Data header companyRow style
                    CellStyle style = workbook.createCellStyle();
                    style.setFillBackgroundColor(GREY_25_PERCENT.getIndex());
                    style.setFillPattern(FillPatternType.BIG_SPOTS);
                    productOrderRow.setRowStyle(style);

                    Cell productOrderCell = productOrderRow.createCell(0);
                    productOrderCell.setCellValue("id");
                    productOrderCell = productOrderRow.createCell(1);
                    productOrderCell.setCellValue("Շտրիխկոդ");
                    productOrderCell = productOrderRow.createCell(2);
                    productOrderCell.setCellValue("Անվանում");
                    productOrderCell = productOrderRow.createCell(3);
                    productOrderCell.setCellValue("Քանակ");
                    productOrderCell = productOrderRow.createCell(4);
                    productOrderCell.setCellValue("Գին");
                    productOrderCell = productOrderRow.createCell(5);
                    productOrderCell.setCellValue("Գումարը");

                    double allAmount = 0;
                    int count = 1;
                    // insert product orders data with foreach loop
                    for (ProductOrder productOrder : order.getProductOrders()) {
                        Row productOrderDataRow = createdSheet.createRow(createdSheet.getLastRowNum() + 1);
                        productOrderDataRow.createCell(0).setCellValue(count);
                        productOrderDataRow.createCell(1).setCellValue(productOrder.getProduct().getBarcode());
                        productOrderDataRow.createCell(2).setCellValue(productOrder.getProduct().getTitle());
                        productOrderDataRow.createCell(3).setCellValue(productOrder.getCount());
                        productOrderDataRow.createCell(4).setCellValue(productOrder.getProduct().getPrice());
                        allAmount += productOrder.getCount() * productOrder.getProduct().getPrice();
                        productOrderDataRow.createCell(5).setCellValue(productOrder.getCount() * productOrder.getProduct().getPrice());
                        count += 1;
                    }

                    Row allAmountRow = createdSheet.createRow(createdSheet.getLastRowNum() + 1);
                    Cell cell = allAmountRow.createCell(0);
                    cell.setCellValue("Ընդհանուր գումարը");
                    cell = allAmountRow.createCell(5);
                    cell.setCellValue(allAmount);
                }
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }


    private boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}

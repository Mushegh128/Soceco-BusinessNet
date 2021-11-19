package am.hovall.common.service.impl;

import am.hovall.common.entity.*;
import am.hovall.common.repository.*;
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
    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;
    private final CompanyTypeRepository companyTypeRepository;
    private final PresSellerRepository presSellerRepository;
    private final DebtRepository debtRepository;

    @Override
    public void importProducts(MultipartFile file) throws Exception {

        if (hasExcelFormat(file)) {
            throw new FileUploadException();
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

    @Override
    public void importCompanies(MultipartFile file) throws Exception {
        if (hasExcelFormat(file)) {
            throw new FileUploadException();
        }
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rows = sheet.iterator();
            List<Company> companies = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Company company = new Company();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            long barcodeFromFile = (long) currentCell.getNumericCellValue();
                            Optional<Company> byBarcode = companyRepository.findByBarcode(barcodeFromFile);
                            if (byBarcode.isPresent()) {
                                company = byBarcode.get();
                            } else {
                                company.setBarcode(barcodeFromFile);
                            }
                            break;
                        case 1:
                            company.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            company.setAddress(currentCell.getStringCellValue());
                            break;
                        case 3:
                        } else {
                            DataFormat fmt = workbook.createDataFormat();
                            CellStyle cellStyle = workbook.createCellStyle();
                            cellStyle.setDataFormat(fmt.getFormat("@"));
                            currentCell.setCellStyle(cellStyle);
                            String regNumber;
                            CellType type = currentCell.getCellType();
                            if (type == CellType.NUMERIC) {
                                regNumber = String.valueOf(currentCell.getNumericCellValue());
                                company.setRegisterNumber(regNumber);
                            } else {
                                regNumber = currentCell.getStringCellValue();
                                if (regNumber.matches("^[0-9]+$")) {
                                    company.setRegisterNumber(regNumber);
                                }
                            }
                            break;
                        case 4:
                            company.setPhoneNumber(currentCell.getStringCellValue());
                            break;
                        case 5:
                            long companyTypeIdFromExcel = (long) currentCell.getNumericCellValue();
                            if (companyTypeIdFromExcel == 0) {
                                break;
                            }
                            Optional<CompanyType> companyTypeByName = companyTypeRepository.findById(companyTypeIdFromExcel);
                            if (companyTypeByName.isPresent()) {
                                company.setCompanyType(companyTypeByName.get());
                            }
                            break;
                        case 6:
                            long presSellerIdFromExcel = (long) currentCell.getNumericCellValue();
                            if (presSellerIdFromExcel == 0) {
                                break;
                            }
                            Optional<PresSeller> optionalPresSeller = presSellerRepository.findById(presSellerIdFromExcel);
                            if (optionalPresSeller.isPresent()) {
                                company.setPresSeller(optionalPresSeller.get());
                            }
                            break;
                        case 7:
                            long debtIdFromExcel = (long) currentCell.getNumericCellValue();
                            if (debtIdFromExcel == 0) {
                                break;
                            }
                            Optional<Debt> optionalDebt = debtRepository.findById(debtIdFromExcel);
                            if (optionalDebt.isPresent()) {
                                company.setDebt(optionalDebt.get());
                            }
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                companies.add(company);
            }
            workbook.close();
            companyRepository.saveAll(companies);
        } catch (IOException e) {
            throw new RuntimeException("failed to parse Excel file: " + e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream exportOrdersByStatus(String orderStatus, long id) throws IOException {
        boolean orderStatusIsNotPresent = Arrays.stream(OrderStatus.values()).anyMatch(status -> status.name().equals(orderStatus));
        if (!orderStatusIsNotPresent) {
            throw new NullPointerException();
        }
        List<Order> orderList = orderRepository.findAllByCompanyIdAndOrderStatus(id, OrderStatus.valueOf(orderStatus));
        if (orderList.isEmpty()) {
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
                    if (order.getCompany().getName() != null) {
                        cell.setCellValue(order.getCompany().getName());
                    } else {
                        cell.setBlank();
                    }
                    Row addressRow = sheet.createRow(2);
                    cell = addressRow.createCell(0);
                    cell.setCellValue("Ընկերության հասցեն");
                    cell = addressRow.createCell(3);
                    if (order.getCompany().getAddress() != null) {
                        cell.setCellValue(order.getCompany().getAddress());
                    } else {
                        cell.setBlank();
                    }

                    Row regNumberRow = sheet.createRow(3);
                    regNumberRow.setRowStyle(cellStyle);
                    cell = regNumberRow.createCell(0);
                    cell.setCellValue("Ընկերության ՀՎՀՀ");
                    cell = regNumberRow.createCell(3);
                    if (order.getCompany().getRegisterNumber() != null) {
                        cell.setCellValue(order.getCompany().getRegisterNumber());
                    } else {
                        cell.setBlank();
                    }

                    Row presSellerRow = sheet.createRow(4);
                    cell = presSellerRow.createCell(0);
                    cell.setCellValue("Շուկայի Մենեջեր");
                    cell = presSellerRow.createCell(3);
                    PresSeller presSeller = order.getCompany().getPresSeller();
                    if (presSeller != null) {
                        cell.setCellValue(order.getCompany().getPresSeller().getName());
                    } else {
                        cell.setBlank();
                    }

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


    @Override
    public ByteArrayInputStream exportProducts() throws IOException {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new NullPointerException();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        Row productsHeaderRow = sheet.createRow(sheet.getLastRowNum() + 1);
        productsHeaderRow.setRowStyle(returnHeaderColor(workbook));

        Cell headerRowCell = productsHeaderRow.createCell(0);
        headerRowCell.setCellValue("Շտրիխկոդ");
        headerRowCell = productsHeaderRow.createCell(1);
        headerRowCell.setCellValue("Անվանում");
        headerRowCell = productsHeaderRow.createCell(2);
        headerRowCell.setCellValue("Նկարագրություն");
        headerRowCell = productsHeaderRow.createCell(3);
        headerRowCell.setCellValue("Գին");
        headerRowCell = productsHeaderRow.createCell(4);
        headerRowCell.setCellValue("Քաշ/քանակ");
        headerRowCell = productsHeaderRow.createCell(5);
        headerRowCell.setCellValue("Արտադրման Երկիր");
        headerRowCell = productsHeaderRow.createCell(6);
        headerRowCell.setCellValue("Կատեգորիա");
        headerRowCell = productsHeaderRow.createCell(7);
        headerRowCell.setCellValue("Ապրանքանիշը");
        headerRowCell = productsHeaderRow.createCell(8);
        headerRowCell.setCellValue("Արտադրման ամսաթիվը");

        for (Product product : productList) {
            Row productDataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell productDataCell = productDataRow.createCell(0);

            productDataCell.setCellValue(product.getBarcode());
            productDataCell = productDataRow.createCell(1);

            if (product.getTitle() != null) {
                productDataCell.setCellValue(product.getTitle());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = productDataRow.createCell(2);
            if (product.getDescription() != null) {
                productDataCell.setCellValue(product.getDescription());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = productDataRow.createCell(3);
            if (product.getPrice() != 0) {
                productDataCell.setCellValue(product.getPrice());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = productDataRow.createCell(4);
            if (product.getWeight() != 0) {
                productDataCell.setCellValue(product.getWeight());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = productDataRow.createCell(5);
            if (product.getMadeInCountry() != null) {
                productDataCell.setCellValue(product.getMadeInCountry().getTitle());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = productDataRow.createCell(6);
            if (product.getProductCategory() != null) {
                productDataCell.setCellValue(product.getProductCategory().getTitle());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = productDataRow.createCell(7);
            if (product.getBrand() != null) {
                productDataCell.setCellValue(product.getBrand().getTitle());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = productDataRow.createCell(8);
            if (product.getCreatedDateTime() != null) {
                productDataCell.setCellValue(product.getCreatedDateTime().toString());
            } else {
                productDataCell.setBlank();
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    @Override
    public ByteArrayInputStream exportCompanies() throws IOException {
        List<Company> companyList = companyRepository.findAll();
        if (companyList.isEmpty()) {
            throw new NullPointerException();
        }
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Companies");

        Row companiesHeaderRow = sheet.createRow(0);
        companiesHeaderRow.setRowStyle(returnHeaderColor(workbook));

        Cell companiesHeaderRowCell = companiesHeaderRow.createCell(0);
        companiesHeaderRowCell.setCellValue("Շտրիխկոդ");
        companiesHeaderRowCell = companiesHeaderRow.createCell(1);
        companiesHeaderRowCell.setCellValue("Անվանում");
        companiesHeaderRowCell = companiesHeaderRow.createCell(2);
        companiesHeaderRowCell.setCellValue("Հասցե");
        companiesHeaderRowCell = companiesHeaderRow.createCell(3);
        companiesHeaderRowCell.setCellValue("ՀՎՀՀ");
        companiesHeaderRowCell = companiesHeaderRow.createCell(4);
        companiesHeaderRowCell.setCellValue("Հեռախոսահամար");
        companiesHeaderRowCell = companiesHeaderRow.createCell(5);
        companiesHeaderRowCell.setCellValue("Տիպ");
        companiesHeaderRowCell = companiesHeaderRow.createCell(6);
        companiesHeaderRowCell.setCellValue("Շ․Զ․Պ");
        companiesHeaderRowCell = companiesHeaderRow.createCell(7);
        companiesHeaderRowCell.setCellValue("Պարտք");

        for (Company company : companyList) {
            Row companyDataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell productDataCell = companyDataRow.createCell(0);
            productDataCell.setCellValue(company.getBarcode());
            productDataCell = companyDataRow.createCell(1);

            if (company.getName() != null) {
                productDataCell.setCellValue(company.getName());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = companyDataRow.createCell(2);
            if (company.getAddress() != null) {
                productDataCell.setCellValue(company.getAddress());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = companyDataRow.createCell(3);
            if (company.getRegisterNumber() != null) {
                productDataCell.setCellValue(company.getRegisterNumber());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = companyDataRow.createCell(4);
            if (company.getPhoneNumber() != null) {
                productDataCell.setCellValue(company.getPhoneNumber());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = companyDataRow.createCell(5);
            if (company.getCompanyType() != null) {
                productDataCell.setCellValue(company.getCompanyType().getId());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = companyDataRow.createCell(6);
            if (company.getPresSeller() != null) {
                productDataCell.setCellValue(company.getPresSeller().getId());
            } else {
                productDataCell.setBlank();
            }

            productDataCell = companyDataRow.createCell(7);
            if (company.getDebt() != null) {
                productDataCell.setCellValue(company.getDebt().getId());
            } else {
                productDataCell.setBlank();
            }

        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private CellStyle returnHeaderColor(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillBackgroundColor(GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
        return cellStyle;
    }

    private boolean hasExcelFormat(MultipartFile file) {
        return !TYPE.equals(file.getContentType());
    }
}

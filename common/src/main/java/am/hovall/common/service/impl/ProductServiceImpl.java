package am.hovall.common.service.impl;

import am.hovall.common.entity.Brand;
import am.hovall.common.util.CustomMultipartFile;
import am.hovall.common.entity.Product;
import am.hovall.common.entity.ProductCategory;
import am.hovall.common.repository.ProductRepository;
import am.hovall.common.service.ImageManipulatorService;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ImageManipulatorService imageManipulatorService;
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAllByCategory(ProductCategory productCategory) {
        return null;
    }

    @Override
    public List<Product> findAllByBrand(Brand brand) {
        return null;
    }

    @Override
    public List<Product> findAllByPriceRange(Double startPrice, Double endPrice) {
        return null;
    }

    @Override
    public Product add(Product map) {
        return null;
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public boolean deactivate(Long id) {
        return false;
    }

    @Override
    public void saveImage(MultipartFile file, Product product) throws IOException {
        String picUrl = product.getBarcode() + ".jpg";
        String png = product.getBarcode() + "_small_pic.png";
        CustomMultipartFile customMultipartFile = new CustomMultipartFile(file.getBytes(), png);
        String smallPicUrl = imageManipulatorService.compressImage(customMultipartFile, imageManipulatorService.returnFilePath(), png);
        file.transferTo(new File(imageManipulatorService.returnFilePath() + File.separator + picUrl));
        product.setPicUrl(picUrl);
        product.setSmallPicUrl(smallPicUrl);
        productRepository.save(product);
    }
}

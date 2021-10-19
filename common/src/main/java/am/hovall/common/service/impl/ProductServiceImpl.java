package am.hovall.common.service.impl;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Product;
import am.hovall.common.entity.ProductCategory;
import am.hovall.common.exception.ProductNotFoundException;
import am.hovall.common.repository.ProductRepository;
import am.hovall.common.service.ImageManipulatorService;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ImageManipulatorService imageManipulatorService;
    private final ProductRepository productRepository;

    @Value("${file.upload.dir}")
    public String FILES_PATH;

    @Override
    public List<Product> getAllProducts() {
        return null;
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
    public void saveImage(MultipartFile file, long id) throws IOException, ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }
        if (file == null) {
            throw new FileNotFoundException();
        }
        String picUrl = product.get().getBarcode() + ".jpg";
        String png = product.get().getBarcode() + "_small_pic.png";
        MultipartFile multipartFile = new MockMultipartFile(png, file.getBytes());
        String smallPicUrl = imageManipulatorService.compressImage(multipartFile, FILES_PATH, png);
        file.transferTo(new File(FILES_PATH + File.separator + picUrl));
        product.get().setPicUrl(picUrl);
        product.get().setSmallPicUrl(smallPicUrl);
        productRepository.save(product.get());
    }
}

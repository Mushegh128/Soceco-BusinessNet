package am.hovall.common.service.impl;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Product;
import am.hovall.common.entity.ProductCategory;
import am.hovall.common.exception.ProductNotFoundException;
import am.hovall.common.repository.ProductRepository;
import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;
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
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllByCategoryId(long id) {
        return productRepository.findAllByProductCategoryId(id);
    }

    @Override
    public List<Product> findAllByBrandId(long id) {
        return productRepository.findAllByBrandId(id);
    }

    @Override
    public List<Product> findAllByPriceRange(double startPrice, double endPrice) {
        return productRepository.findAllByPriceStartsAndPriceEnds(startPrice, endPrice);
    }

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deactivate(long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            return false;
        }
        byId.get().setActive(false);
        productRepository.save(byId.get());
        return true;
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

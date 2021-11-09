package am.hovall.common.service.impl;

import am.hovall.common.entity.Order;
import am.hovall.common.entity.Product;
import am.hovall.common.exception.ProductNotFoundException;
import am.hovall.common.mapper.ProductMapper;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ImageManipulatorService imageManipulatorService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Value("${file.upload.dir}")
    public String FILES_PATH;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllUnSynchronized() throws ProductNotFoundException {
        return productRepository.findAllUnSynchronized().
                stream().
                map(productMapper::toResponse).
                collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllProductsByBarcode(long barcode) throws ProductNotFoundException {
        return productRepository.findAllByBarcode(barcode).
                stream().
                map(productMapper::toResponse).
                collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByCategoryId(long id) {
        return productRepository.findAllByProductCategoryId(id).stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByBrandId(long id) {
        return productRepository.findAllByBrandId(id).stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByPriceRange(double startPrice, double endPrice) {
        return productRepository.findAllByPriceStartsAndPriceEnds(startPrice, endPrice).stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse add(ProductRequest productRequest) {
        final Product product = productMapper.toEntity(productRequest);
        final Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse update(ProductRequest productRequest) {
        final boolean existsById = productRepository.existsById(productRequest.getId());
        if (!existsById) {
            throw new ProductNotFoundException();
        }
        final Product product = productMapper.toEntity(productRequest);
        final Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
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

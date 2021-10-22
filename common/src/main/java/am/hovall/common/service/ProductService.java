package am.hovall.common.service;


import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts(int page);

    List<ProductResponse> findAllByCategoryId(long id, int page);

    List<ProductResponse> findAllByBrandId(long id, int page);

    List<ProductResponse> findAllByPriceRange(double startPrice, double endPrice);

    ProductResponse add(ProductRequest productRequest);

    ProductResponse update(ProductRequest productRequest);

    boolean deactivate(long id);

    void saveImage(MultipartFile file, long id) throws IOException;
}

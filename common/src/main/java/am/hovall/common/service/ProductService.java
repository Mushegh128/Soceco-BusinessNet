package am.hovall.common.service;


import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts(Pageable pageable);

    List<ProductResponse> findAllUnSynchronized();

    List<ProductResponse> getAllProductsByBarcode(long barcode);

    List<ProductResponse> findAllByCategoryId(long id, Pageable pageable);

    List<ProductResponse> findAllByBrandId(long id, Pageable pageable);

    List<ProductResponse> findAllByPriceRange(double startPrice, double endPrice,Pageable pageable);

    ProductResponse add(ProductRequest productRequest);

    ProductResponse update(ProductRequest productRequest);

    boolean deactivate(long id);

    void saveImage(MultipartFile file, long id) throws IOException;

    ProductResponse findById(Long id);
}

package am.hovall.common.mapper;

import am.hovall.common.entity.Product;
import am.hovall.common.mapper.config.BaseMapper;
import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMapper implements BaseMapper<Product, ProductRequest, ProductResponse> {
    private final ModelMapper mapper;

    @Override
    public Product toEntity(ProductRequest productRequest) {
        return mapper.map(productRequest, Product.class);
    }

    @Override
    public ProductResponse toResponse(Product product) {
        return mapper.map(product, ProductResponse.class);
    }
}

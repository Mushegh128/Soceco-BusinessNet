package am.hovall.rest.endpoints;

import am.hovall.common.model.entities.Product;
import am.hovall.common.services.ProductService;
import am.hovall.rest.response.MainResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainEndpoint {

    private final ProductService productService;
    private final ModelMapper modelMapper;


//    @GetMapping("/main")
//    public ResponseEntity<MainResponse> homePageData(){
//        List<Product> allProducts = productService.getAllProducts();
//
//    }

}

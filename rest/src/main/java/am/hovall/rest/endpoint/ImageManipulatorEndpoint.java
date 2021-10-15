package am.hovall.rest.endpoint;

import am.hovall.common.entity.Product;
import am.hovall.common.service.ImageManipulatorService;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class ImageManipulatorEndpoint {

    private final ProductService productService;
    private final ImageManipulatorService imageManipulatorService;

    @GetMapping(value = "/downloadImage/{image}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable("image") String imageUrl) {
        try {
            InputStream in = new FileInputStream(
                    imageManipulatorService.returnFilePath() + File.separator + imageUrl);
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/product/uploadImage/{id}")
    public ResponseEntity<?> uploadProductImage(@RequestParam("image") MultipartFile file,
                                                @PathVariable("id") long id) throws IOException {
        Product fromDb = productService.findById(id);
        if (fromDb != null) {
            if (!file.isEmpty()) {
                productService.saveImage(file, fromDb);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

}

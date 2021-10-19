package am.hovall.rest.endpoint;

import am.hovall.common.exception.ProductNotFoundException;
import am.hovall.common.service.ImageManipulatorService;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageManipulatorEndpoint {

    private final ProductService productService;
    private final ImageManipulatorService imageManipulatorService;

    @GetMapping(value = "/downloadImage/{image}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable("image") String imageUrl) {
        try {
            return IOUtils.toByteArray(imageManipulatorService.returnImage(imageUrl));
        } catch (IOException e) {
            return null;
        }
    }

    @PostMapping("/product/uploadImage/{id}")
    public ResponseEntity<?>
    uploadProductImage(@RequestParam("image") MultipartFile file,
                       @PathVariable("id") long id) throws IOException {
        try {
            productService.saveImage(file, id);
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}

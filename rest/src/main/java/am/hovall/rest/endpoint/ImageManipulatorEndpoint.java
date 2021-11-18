package am.hovall.rest.endpoint;

import am.hovall.common.service.ImageManipulatorService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image-manipulator")
public class ImageManipulatorEndpoint {

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

}
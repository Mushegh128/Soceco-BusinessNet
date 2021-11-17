package am.hovall.web.controller;

import am.hovall.common.service.ImageManipulatorService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class ImageManipulatorController {

    private final ImageManipulatorService imageManipulatorService;

    @GetMapping(value = "/downloadImage/")
    public void getImageAsByteArray(@RequestParam("image") String imageUrl, HttpServletResponse response) throws IOException {
        InputStream in = imageManipulatorService.returnImage(imageUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

}

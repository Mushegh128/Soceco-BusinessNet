package am.hovall.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageManipulatorService {

    String returnFilePath();

    void compressProductImage(BufferedImage image, String uploadPath, String extension);

    String compressImage(MultipartFile file, String uploadDir, String fileName) throws IOException;

}

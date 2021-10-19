package am.hovall.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface ImageManipulatorService {

    void compressProductImage(BufferedImage image, String uploadPath, String extension);

    String compressImage(MultipartFile file, String uploadDir, String fileName) throws IOException;

    InputStream returnImage(String imageUrl) throws FileNotFoundException;

}
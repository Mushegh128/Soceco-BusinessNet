package am.hovall.common.service.impl;

import am.hovall.common.service.ImageManipulatorService;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

@Service
public class ImageManipulatorServiceImpl implements ImageManipulatorService {

    @Value("${file.upload.dir}")
    public String FILES_PATH;

    @Override
    public String returnFilePath() {
        return FILES_PATH;
    }

    @Override
    public void compressProductImage(BufferedImage image, String uploadPath, String extension) {
        try {
            File compressedImageFile = new File(uploadPath);
            OutputStream outputStream = new FileOutputStream(compressedImageFile);
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extension);
            ImageWriter writer = writers.next();
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(imageOutputStream);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.3f);
            }
            writer.write(null, new IIOImage(image, null, null), param);
            outputStream.close();
            imageOutputStream.close();
            writer.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String compressImage(MultipartFile file, String uploadDir, String fileName) throws IOException {
        String uploadPath = uploadDir + fileName;
        File image = new File(uploadPath);
        file.transferTo(image);
        BufferedImage bi = ImageIO.read(file.getInputStream());
        BufferedImage resize = Scalr.resize(bi, 200, 150);
        compressProductImage(resize, uploadPath, "png");
        return fileName;
    }

    @Override
    public InputStream returnImage(String imageUrl) throws FileNotFoundException {
        return new FileInputStream(FILES_PATH + File.separator + imageUrl);
    }

}

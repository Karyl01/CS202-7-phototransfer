package com.example.controller;

import com.example.module.ImageItem;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageTransformer2BMP extends ImageTransformer {

    public ImageItem convertToBMP(ImageItem imageItem) throws IOException {
        javafx.scene.image.Image originalImage = imageItem.getImage();
        if (originalImage != null) {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(originalImage, null);
            if (bufferedImage != null) {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "bmp", os);
                ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
                BufferedImage bmpBufferedImage = ImageIO.read(is);

                // 创建新的 ImageItem 对象并返回
                ImageItem bmpImageItem = new ImageItem(bufferedImage, "bmp");
                this.showAlert("Successfully transform to BMP");
                bmpImageItem.saveImageToFile("C:\\Users\\admin\\Desktop\\phototransfer\\master\\src\\main\\java\\Pictures\\1.bmp");
                return bmpImageItem;



            } else {
                throw new IOException("Failed to convert image to BufferedImage.");
            }
        } else {
            throw new IllegalArgumentException("Original image is null.");
        }
    }
}
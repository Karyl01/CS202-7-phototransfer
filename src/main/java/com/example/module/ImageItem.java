package com.example.module;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageItem {
    private Image image;
    private String fileType;
    private String filePath;
    private double rotation;
    private double fileHeight;
    private double fileWidth;
    private long fileSize;

    public ImageItem(String filePath) throws IOException {
        this.filePath = filePath;
        loadImage(filePath);
        this.rotation = 0.0;
    }

    public ImageItem(BufferedImage bufferedImage, String fileType) {
        this.image = SwingFXUtils.toFXImage(bufferedImage, null);
        this.fileType = fileType;
        this.fileHeight = image.getHeight();
        this.fileWidth = image.getWidth();
        this.rotation = 0.0;
        // 文件路径和大小可能需要在其他地方设置
    }






    public ImageItem(Image image) {
        this.image = image;
    }

    private void loadImage(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + filePath);
        }
        BufferedImage bufferedImage = ImageIO.read(file);
        if (bufferedImage != null) {
            this.fileType = getFileExtension(file);
            this.fileSize = Files.size(Paths.get(filePath));
            this.image = new Image(file.toURI().toString());
            this.fileHeight = image.getHeight();
            this.fileWidth = image.getWidth();
        } else {
            // 处理图像加载失败的情况，例如设置默认图像或记录日志
            throw new IOException("Failed to load image from file: " + filePath);
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        } else {
            return "";
        }
    }

    public Image getImage() {
        return image;
    }

    public void saveImageToFile(String filePath) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        File outputFile = new File(filePath);

        // 确保父目录存在，如果不存在则创建
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        // 如果文件不存在，则创建文件
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        // 根据文件后缀名确定保存格式
        String format = getFileType().toUpperCase(); // 假设后缀名是大写的格式
        if (!ImageIO.write(bufferedImage, format, outputFile)) {
            throw new IOException("Error writing image to file: " + filePath);
        }
    }

    public void rotateImage(double angle) {
        this.rotation += angle;
    }

    public ImageView getImageView() {
        ImageView imageView = new ImageView(image);
        imageView.setRotate(rotation);
        return imageView;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public double getFileHeight() {
        return fileHeight;
    }

    public double getFileWidth() {
        return fileWidth;
    }

    public long getFileSize() {
        return fileSize;
    }
}

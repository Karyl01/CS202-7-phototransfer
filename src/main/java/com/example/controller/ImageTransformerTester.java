package com.example.controller;

import com.example.module.ImageItem;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ImageTransformerTester extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageTransformer2BMP imageTransformer = new ImageTransformer2BMP();
        try {
            ImageItem imageItem = new ImageItem("C:\\Users\\admin\\Desktop\\phototransfer\\master\\src\\main\\java\\Pictures\\1.png");

            ImageItem imageItem2BMP = imageTransformer.convertToBMP(imageItem);
            // 将转换后的图像保存到文件
            imageItem2BMP.saveImageToFile("C:\\path\\to\\save\\output.bmp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

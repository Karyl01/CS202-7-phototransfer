package com.example.view;

import com.example.controller.ImageTransformer;
import com.example.controller.ImageTransformer2BMP;
import com.example.module.ImageItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ImageTranscoderApp extends Application {
    public static ObservableList<String> folderFileNames = FXCollections.observableArrayList();
    public static ListView<String> listView = new ListView<>();

    public static ListView<String> translistView = new ListView<>();
    public static Image image;
    public static ImageView imageView = new ImageView();
    public static File folder = new File("imageFolder");
    public static File transfolder = new File("transFolder");
    public static int rotationAngle = 0;

    @Override
    public void start(Stage primaryStage) {
        // 创建舞台
        primaryStage.setTitle("图片转换软件");
        // 创建一个GridPane作为主布局(网格布局)
        GridPane gridPane = new GridPane();
        //初始化文件夹
        File folder = new File("imageFolder");
        System.out.println("imageFolder");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File transfolder = new File("transFolder");
        System.out.println("transFolder");
        if (!transfolder.exists()) {
            transfolder.mkdir();
        }

        // 创建一个场景并将其与舞台关联
        Scene scene = new Scene(gridPane, 800, 1200);
        primaryStage.setHeight(500);
        primaryStage.setWidth(1200);
        primaryStage.setScene(scene);
        // 组件排版，先行后列
        gridPane.add(Barbox.createToolbar(primaryStage, gridPane), 0, 0);
        gridPane.add(Barbox.createtransbar(), 1, 0);
        gridPane.add(Barbox.createimage(), 1, 1);
        gridPane.add(Barbox.createlist(), 0, 1);
        gridPane.setGridLinesVisible(true);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            // 删除文件夹中的所有文件
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete();
                    }
                }
            }
        });


//        // 创建ImageTransformer2BMP对象并调用其方法
//        ImageTransformer2BMP imageTransformer2BMP = new ImageTransformer2BMP();
//        try {
//            ImageItem imageItem = new ImageItem("C:\\Users\\admin\\Desktop\\phototransfer\\master\\src\\main\\java\\Pictures\\1.png");
//
//            ImageItem imageItem2BMP = imageTransformer2BMP.convertToBMP(imageItem);
//            //这里的保存文件到文件夹已经写在convertToBMP方法中了，还需要调整
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }




        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                File[] files = folder.listFiles();
                if (files != null) {
                    String filteredFileName = null;
                    for (File file : files) {
                        System.out.println("imageFolder\\" + newVal);
                        System.out.println(file.getName());
                        if (("imageFolder\\" + file.getName()).equals(newVal)) {
                            filteredFileName = file.getAbsolutePath();
                            break; // 找到第一个符合条件的文件即可退出循环
                        }
                    }
                    // 如果找到符合条件的文件，加载并返回图像
                    if (filteredFileName != null) {
                        image = new Image(new File(filteredFileName).toURI().toString());
                    }
                }
            }
            rotationAngle = 0;
            imageView.setRotate(rotationAngle);
            System.out.println(image.toString());
            imageView.setFitHeight(800);
            imageView.setFitWidth(1200);
            imageView.setImage(image);

        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
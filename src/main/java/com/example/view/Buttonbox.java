package com.example.view;

import com.example.network.Server.userStage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static com.example.view.ImageTranscoderApp.imageView;
import static com.example.view.ImageTranscoderApp.rotationAngle;

public class Buttonbox {


    public static Button createfileButton(Stage primaryStage, GridPane gridPane) {
        Button fileButton = new Button("文件(F)");
        fileButton.setStyle("-fx-background-color: transparent;-fx-background-radius: 0;");
        fileButton.setOnMouseEntered(e -> fileButton.setStyle("-fx-border-color: transparent; -fx-border-width: 0px;-fx-background-radius: 0;-fx-border-radius: 0;")); // 鼠标悬停时设置边框样式
        fileButton.setOnMouseExited(e -> fileButton.setStyle("-fx-background-color: transparent;-fx-background-radius: 0;")); // 鼠标移出时清除样式

        fileButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F) {
                openImage(primaryStage, gridPane);
            }
        });
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button clicked");
            }
        });
        fileButton.setOnAction(e -> openImage(primaryStage, gridPane));
        return fileButton;
    }

    private static List<String> openImage(Stage stage, GridPane gridPane) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (isImageFile(file)) {//判断是否为图片
                if (!ImageTranscoderApp.folder.exists()) {
                    ImageTranscoderApp.folder.mkdir();
                }
                // 将选中的文件拷贝到新建文件夹中
                if (isFileExistInFolder("imageFolder", file.getName())) {
                    try {
                        Path source = file.toPath();
                        Path target = new File(ImageTranscoderApp.folder, file.getName()).toPath();
                        Files.copy(source, target);
                        ImageTranscoderApp.folderFileNames.add(target.toString());//只能添加
                        Filelist.creatfilelist(ImageTranscoderApp.folderFileNames);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("警告");
                alert.setHeaderText("文件不是图片");
                alert.setContentText("请选择一个图片文件。");
                alert.showAndWait();
            }
        }
        return ImageTranscoderApp.folderFileNames;
    }

    // 判断文件是否为图片
    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".bmp");
    }

    //判断文件是否重复导入
    public static boolean isFileExistInFolder(String folderPath, String fileName) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().equals(fileName)) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("警告");
                        alert.setHeaderText("文件已存在");
                        alert.setContentText("文件 " + fileName + " 已经存在于文件夹 " + folderPath);
                        alert.showAndWait();
                        return false;
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText("文件夹不存在或不是有效的文件夹");
            alert.setContentText("文件夹 " + folderPath + " 不存在或不是一个有效的文件夹");
            alert.showAndWait();
        }
        return true;
    }


    public static Button createditButton() {
        Button editButton = new Button("编辑(D)");
        editButton.setStyle("-fx-background-color: transparent;");
        editButton.setOnMouseEntered(e -> editButton.setStyle("-fx-border-color: transparent; -fx-border-width: 0px;-fx-background-radius: 0;-fx-border-radius: 0;")); // 鼠标悬停时设置边框样式
        editButton.setOnMouseExited(e -> editButton.setStyle("-fx-background-color: transparent;")); // 鼠标移出时清除样式

        editButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {

            }
        });
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Edit clicked");
                //TODO 想想该实现什么
            }
        });

        return editButton;
    }


    public static Button createwindowButton() {
        Button windowButton = new Button("窗口(K)");
        windowButton.setStyle("-fx-background-color: transparent;");
        windowButton.setOnMouseEntered(e -> windowButton.setStyle("-fx-border-color: transparent; -fx-border-width: 0px;-fx-background-radius: 0;-fx-border-radius: 0;")); // 鼠标悬停时设置边框样式
        windowButton.setOnMouseExited(e -> windowButton.setStyle("-fx-background-color: transparent;")); // 鼠标移出时清除样式
        windowButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.K) {

            }
        });
        windowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("View clicked");
                //TODO 想想该实现什么
            }
        });
        return windowButton;
    }


    public static Button createotherButton() {
        Button otherButton = new Button("其他(E)");
        otherButton.setStyle("-fx-background-color: transparent;");
        otherButton.setOnMouseEntered(e -> otherButton.setStyle("-fx-border-color: transparent; -fx-border-width: 0px;-fx-background-radius: 0;-fx-border-radius: 0;")); // 鼠标悬停时设置边框样式
        otherButton.setOnMouseExited(e -> otherButton.setStyle("-fx-background-color: transparent;")); // 鼠标移出时清除样式
        otherButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {

            }
        });
        otherButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Else clicked");
                //TODO 想想该实现什么
            }
        });
        return otherButton;
    }
    public static Button userService(Stage primaryStage, GridPane gridPane) {
        Button userButton = new Button("用户");
        userButton.setStyle("-fx-background-color: transparent;");
        userButton.setOnMouseEntered(e -> userButton.setStyle("-fx-border-color: transparent; -fx-border-width: 0px;-fx-background-radius: 0;-fx-border-radius: 0;")); // 鼠标悬停时设置边框样式
        userButton.setOnMouseExited(e -> userButton.setStyle("-fx-background-color: transparent;")); // 鼠标移出时清除样式
        userButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("User clicked");
            }
        });
        userButton.setOnAction(e -> {
            Stage childStage = userStage.userService();
            childStage.setOnHidden(e1 -> childStage.setAlwaysOnTop(false));
        });

        return userButton;
    }

    public static Button createRotateRight() {
        Button RotateRight = new Button("右旋");
        RotateRight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("RRotate clicked");
                rotationAngle += 90;
                imageView.setRotate(rotationAngle);
            }
        });
        return RotateRight;
    }


    public static Button createRotateLeft() {
        Button RotateLeft = new Button("左旋");
        RotateLeft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("LRotate clicked");
                rotationAngle -= 90;
                imageView.setRotate(rotationAngle);
            }
        });
        return RotateLeft;
    }


    public static Button createamplify() {
        Button amplify = new Button("放大");
        amplify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button clicked");
                imageView.setScaleX(imageView.getScaleX() * 1.1);
                imageView.setScaleY(imageView.getScaleY() * 1.1);
            }
        });
        return amplify;
    }

    public static Button createreduce() {
        Button reduce = new Button("缩小");
        reduce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button clicked");
                imageView.setScaleX(imageView.getScaleX() / 1.1);
                imageView.setScaleY(imageView.getScaleY() / 1.1);
            }
        });
        return reduce;
    }

    public static Button createtrans() {
        Button trans = new Button("转码");
        trans.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("trans clicked");
            }
        });
        return trans;
    }

    public static ChoiceBox<String> creatconversionTypeChoiceBox() {
        ChoiceBox<String> conversionTypeChoiceBox = new ChoiceBox<>();//（多选栏）
        List<String> conversionTypes = Arrays.asList("JPG", "PNG", "GIF", "BMP", "TIFF");
        conversionTypeChoiceBox.getItems().addAll(conversionTypes);
        return conversionTypeChoiceBox;
    }
}
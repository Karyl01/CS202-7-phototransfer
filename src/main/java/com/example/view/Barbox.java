package com.example.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.*;
import java.util.Optional;

import static com.example.view.ImageTranscoderApp.*;

public class Barbox {

    public static HBox createToolbar(Stage primaryStage, GridPane gridPane) {
        HBox toolbar = new HBox(0); // 间距为10
        toolbar.getChildren().addAll(Buttonbox.createfileButton(primaryStage, gridPane), Buttonbox.createditButton(), Buttonbox.createwindowButton(), Buttonbox.createotherButton());
        toolbar.getChildren().addAll(Buttonbox.userService(primaryStage, gridPane));
        toolbar.setPadding(new Insets(1)); // 内边距
        return toolbar;
    }

    public static BorderPane createtransbar() {
        BorderPane borderPane=new BorderPane();
        borderPane.setMinWidth(900);
        HBox transbar = new HBox(0);
        transbar.setPadding(new Insets(1, 0, 0, 300));// 间距为10
        transbar.getChildren().addAll(Buttonbox.createRotateRight(), Buttonbox.createRotateLeft(), Buttonbox.createamplify(), Buttonbox.createreduce(), Buttonbox.createtrans(), Buttonbox.creatconversionTypeChoiceBox());
        borderPane.setLeft(transbar);
        return borderPane;
    }
    public static VBox createlist(){
        VBox list=new VBox(10);
        list.setMinHeight(900);
        list.setMinWidth(300);
        list.getChildren().addAll(createuntranslist(),createtranslist());
        return list;
    }

    public static BorderPane createimage() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(imageView);
        scrollPane.setHvalue(0.5); // 设置水平滚动条的位置为中间
        scrollPane.setVvalue(0.5); // 设置垂直滚动条的位置为中间
        scrollPane.setMinHeight(400);
        scrollPane.setMinWidth(1400);
        Slider  hSlider = new Slider();
        Slider vSlider = new Slider();

        imageView.setPreserveRatio(true);


        scrollPane.setPannable(true);  // Allow dragging
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        hSlider.setMin(-50);
        hSlider.setMax(50);
        vSlider.setMin(-50);
        vSlider.setMax(50);

        StackPane imagePane = new StackPane(scrollPane);
        imagePane.setAlignment(Pos.CENTER);


        StackPane sliderPane = new StackPane(hSlider, vSlider);
        sliderPane.setAlignment(Pos.CENTER);
        sliderPane.setOpacity(0); // Hide sliders initially

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(imagePane);
        borderPane.setBottom(sliderPane);

        return borderPane;
    }


    public static ScrollPane createuntranslist() {
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox(5);
        vBox.setMinHeight(400);
        scrollPane.setContent(vBox);
        scrollPane.setMaxHeight(450);

        // 监听文件夹中图片的变化
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path folderPath = Paths.get("imageFolder");
            folderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

            // 启动监听线程
            startWatchThread(watchService, vBox,folder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 初始加载文件夹中的图片并创建按钮
        updateButtons(vBox,folder);
        return scrollPane;
    }


    public static ScrollPane createtranslist() {
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox(5);
        vBox.setMinHeight(400);
        scrollPane.setContent(vBox);
        scrollPane.setMaxHeight(450);

        // 监听文件夹中图片的变化
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path folderPath = Paths.get("imageFolder");
            folderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

            // 启动监听线程
            startWatchThread(watchService, vBox,transfolder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 初始加载文件夹中的图片并创建按钮
        updateButtons(vBox,transfolder);
        return scrollPane;
    }

    // 启动监听线程方法
    private static void startWatchThread(WatchService watchService, VBox vBox,File folder) {
        Thread watchThread = new Thread(() -> {
            try {
                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE || event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                            Platform.runLater(() -> updateButtons(vBox,folder)); // 更新按钮放在主线程中执行
                        }
                    }
                    key.reset();
                }
            } catch (InterruptedException | ClosedWatchServiceException e) {
                e.printStackTrace();
            }
        });
        watchThread.setDaemon(true);
        watchThread.start();
    }

    // 更新按钮方法
    private static void updateButtons(VBox root,File folder) {
        root.getChildren().clear(); // 清空所有按钮
        // 获取文件夹中的所有图片文件
        File[] files = folder.listFiles((dir, name) -> {
            String lowercaseName = name.toLowerCase();
            return lowercaseName.endsWith(".png") || lowercaseName.endsWith(".jpg");
        });
        if (files != null) {
            // 创建并添加每个图片文件对应的按钮
            for (File file : files) {
                Image image = new Image(file.toURI().toString());
                ImageView imageView1 = new ImageView(image);
                double width = image.getWidth();
                double height = image.getHeight();
                // 计算长宽比例
                double aspectRatio = width / height;
                imageView1.setFitWidth(40*aspectRatio);
                imageView1.setFitHeight(40);
                Button mainButton = new Button(file.getName());
                mainButton.setStyle("-fx-background-color: transparent;");
                mainButton.setOnMouseEntered(e -> mainButton.setStyle("-fx-border-color: transparent; -fx-border-width: 0px;")); // 鼠标悬停时设置边框样式
                mainButton.setOnMouseExited(e -> mainButton.setStyle("-fx-background-color: transparent;")); // 鼠标移出时清除样式
                mainButton.setGraphic(imageView1); // 设置图像视图作为按钮的图形
                mainButton.setContentDisplay(ContentDisplay.TOP); // 设置图形在文字上方显示
                mainButton.setMaxHeight(40);
                mainButton.setMaxWidth(100);

                Button subButton = new Button("x");
                subButton.setStyle("-fx-background-color:transparent; -fx-shape: \"M 0 0 L 10 10 M 0 10 L 10 0\";");
                subButton.setOnMouseEntered(e -> subButton.setStyle("-fx-border-color: transparent; -fx-border-width: 0px;")); // 鼠标悬停时设置边框样式
                subButton.setOnMouseExited(e -> subButton.setStyle("-fx-background-color: transparent;")); // 鼠标移出时清除样式
                subButton.setPrefSize(10, 10);

                StackPane buttonPane = new StackPane();
                StackPane.setAlignment(subButton, javafx.geometry.Pos.TOP_RIGHT);//子按钮位于右上角

                buttonPane.getChildren().addAll(mainButton, subButton);

                root.getChildren().add(buttonPane);
                // 左键点击事件
                mainButton.setOnAction(e -> {
                    showclick(file);
                });

                // 右键点击事件
                subButton.setOnMouseClicked(e -> {
                    deleteclick(file.getName(),folder);
                });
            }
        }
    }


    private static void showclick(File select) {
        ImageTranscoderApp.image = new Image(select.toURI().toString());
        rotationAngle = 0;
        imageView.setRotate(rotationAngle);
        imageView.setImage(image);

        imageView.setScaleX(1.0);
        imageView.setScaleY(1.0);
        // 保持图片比例
        imageView.setPreserveRatio(true);
        // 设置平滑处理
        imageView.setSmooth(true);

    }

    public static void deleteclick(String fileName,File folder) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText("文件将被删除");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            delete(fileName,folder);
        }

    }
    private static void delete(String fileName,File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    if (file.delete()) {
                        System.out.println("文件 " + fileName + " 已成功删除");
                    } else {
                        System.out.println("无法删除文件 " + fileName);
                    }
                    return;
                }
            }
        }
    }
}
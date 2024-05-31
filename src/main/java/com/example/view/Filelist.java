package com.example.view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

public class Filelist {


    private static void refreshlist(GridPane gridPane, int columnIndex, int rowIndex) {
        // 获取所有子节点
        ObservableList<Node> children = gridPane.getChildren();
        // 遍历子节点列表
        for (Node node : children) {
            // 获取子节点的列索引和行索引
            int column = GridPane.getColumnIndex(node);
            int row = GridPane.getRowIndex(node);
            // 检查子节点的位置是否与指定的列索引和行索引匹配
            if (column == columnIndex && row == rowIndex) {
                // 移除匹配的子节点
                gridPane.getChildren().remove(node);
                break; // 如果只需要移除一个匹配的子节点，可以直接退出循环
            }
        }
    }

    public static void creatfilelist(ObservableList<String> list) {
        ImageTranscoderApp.listView.setItems(list);
    }

    public static ImageView chooseImage(File folder1) {
        ImageTranscoderApp.imageView.setFitWidth(200);
        ImageTranscoderApp.imageView.setFitHeight(200);
        return ImageTranscoderApp.imageView;
    }
}

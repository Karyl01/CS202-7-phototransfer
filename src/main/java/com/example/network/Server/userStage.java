package com.example.network.Server;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class userStage {
    public static Stage userService(){
        Label accountLabel = new Label("    账号 "), passLabel = new Label("    密码 ");
        TextField accountField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button login = new Button("登录"), register = new Button("注册");

        GridPane totalPane = new GridPane();
        totalPane.setVgap(17);
        totalPane.setHgap(17);
        GridPane.setHalignment(accountLabel, HPos.RIGHT);
        totalPane.add(new Label("请登录"),1,1);
        totalPane.add(accountLabel, 1, 2);

        GridPane.setHalignment(passLabel, HPos.RIGHT);
        totalPane.add(passLabel, 1, 3);

        GridPane.setHalignment(accountField, HPos.LEFT);
        totalPane.add(accountField, 2, 2);

        GridPane.setHalignment(passwordField, HPos.LEFT);
        totalPane.add(passwordField, 2, 3);

        GridPane.setHalignment(register, HPos.LEFT);
        totalPane.add(register, 2, 4);
        GridPane.setHalignment(login, HPos.RIGHT);
        totalPane.add(login, 2, 4);

        Scene userScene = new Scene(totalPane,290,260);
        Stage uSta = new Stage();
        uSta.setScene(userScene);
        uSta.setTitle("用户");
        uSta.setAlwaysOnTop(true);
        uSta.show();

        login.setOnAction(e -> {
            System.out.println("login click");
            String name = accountField.getText();
            String password = passwordField.getText();

        });
        register.setOnAction(e -> {
            System.out.println("register click");
            String name = accountField.getText();
            String password = passwordField.getText();

            if(org.example.Network.client.FileClient.registerUser(name, password) != 0){
                Alert ok = new Alert(Alert.AlertType.INFORMATION,"注册成功！");
                uSta.setAlwaysOnTop(false);
                ok.show();



            }else{
                Alert overUer = new Alert(Alert.AlertType.INFORMATION ,"用户已存在");
                uSta.setAlwaysOnTop(false);
                overUer.show();
            }
        });
        return uSta;
    }
}

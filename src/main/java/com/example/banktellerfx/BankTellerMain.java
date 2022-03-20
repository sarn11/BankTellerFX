package com.example.banktellerfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankTellerMain extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Sarn Banking");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

//    public static void openScene(Stage primaryStage) throws IOException {
//        VBox vbox = new VBox(30);
//        Scene openScene = new Scene(vbox, 500, 750);
//        primaryStage.setTitle("Opening an account");
//        primaryStage.setScene(openScene);
//        primaryStage.show();
//    }

    public static void main(String[] args) {
        launch();
    }
}
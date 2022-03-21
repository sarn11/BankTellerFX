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



    public static void main(String[] args) {
        launch();
    }
}
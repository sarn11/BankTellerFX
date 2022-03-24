package com.example.banktellerfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main class of the GUI that launches the program.
 * @author Tyler Sarno, Aum Pathak.
 */

public class BankTellerMain extends Application {
    /**
     * Method that starts the stage of the GUI.
     * @param primaryStage the first stage.
     * @throws IOException exception handling.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Sarn Banking");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }


    /**
     * Main method that launches the GUI.
     * @param args just standard arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
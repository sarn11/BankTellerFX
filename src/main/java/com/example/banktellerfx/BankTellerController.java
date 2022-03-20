package com.example.banktellerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class BankTellerController implements Initializable {

    @FXML
    private Label myLabel;
    @FXML
    private ChoiceBox<String> myChoiceBox;

    private String[] commands = {"O", "C", "D", "W", "P", "PT", "PI", "UB"};
    private String command;

    @FXML
    protected void getCommand(ActionEvent event) {
        command = myChoiceBox.getValue();
        //myLabel.setText(command);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myChoiceBox.getItems().addAll(commands);
        myChoiceBox.setOnAction(this::getCommand);
    }
}
package com.example.banktellerfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class BankTellerController {

    @FXML
    private TextArea myTextArea;
    @FXML
    private RadioButton oButton, cButton, dButton, wButton, pButton, ptButton, ubButton, piButton;


    public void getCommand(ActionEvent e) {
        if (oButton.isSelected()) {

        }
    }

}
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StoreDashboardController {

    @FXML
    void loginasuserOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserLoginInterface.fxml"))));
        s1.show();
    }

    @FXML
    void loginasadminOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminLoginInterface.fxml"))));
        s1.show();
    }

}

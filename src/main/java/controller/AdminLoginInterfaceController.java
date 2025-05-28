package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginInterfaceController {

    @FXML
    private TextField txtadminlogin;

    @FXML
    private PasswordField txtadminpassword;

    @FXML
    void adminloginOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddProductInterface.fxml"))));
        s1.show();
    }


}

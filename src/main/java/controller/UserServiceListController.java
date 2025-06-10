package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserServiceListController {

    @FXML
    void AddNewSupplierOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddSupplierInterface.fxml"))));
        s1.show();
    }

    @FXML
    void AddProductOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddProductInterface.fxml"))));
        s1.show();

    }

    @FXML
    void editEmployeeDetailsOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/editemployeeemailpasswordInterface.fxml"))));
        s1.show();
    }

    @FXML
    void RemoveProductOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddProductInterface.fxml"))));
        s1.show();
    }

    @FXML
    void StockUpdateOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddProductInterface.fxml"))));
        s1.show();
    }

}

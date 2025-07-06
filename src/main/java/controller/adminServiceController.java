package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class adminServiceController {

    @FXML
    void RegisternewUserOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddUserInterface.fxml"))));
        s1.show();

    }

    @FXML
    void UpdateEmployeedetailsOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/updateemployeedetailsInterface.fxml"))));
        s1.show();
    }

    @FXML
    void ViewEmployeedetailsOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UpdateCustomerdetails.fxml"))));
        s1.show();

    }

    public void GenarateReportOnAction(ActionEvent actionEvent) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ReportFormInterface.fxml"))));
        s1.show();
    }
}

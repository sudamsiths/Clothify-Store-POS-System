package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AddproductinterfaceController {

    public TextField txtidAdditem;
    public TextField txtitemname;
    public TextField txtsizeitems;
    public TextField txtpriceadditems;
    public TextField txtsupplyerid;
    public TextField txtqtyfield;
    @FXML
    private ImageView productImage;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;


    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Product Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            productImage.setImage(image);
        }
    }

    public void btnItemAddOnAction(ActionEvent actionEvent) {
    }

    public void btnShowItemsOnAction(ActionEvent actionEvent) throws IOException {
        Stage s1=new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ShowProductInterface.fxml"))));
        s1.show();
    }
}

package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;

public class ReportFormController {

    @FXML
    void GenarateemployeeReportOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign load = JRXmlLoader.load("src/main/resources/report/EmployeeDetails.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
        JasperExportManager.exportReportToPdfFile(jasperPrint, "EmployeeReport.pdf");
        JasperViewer.viewReport(jasperPrint, false);
    }

    @FXML
    void GenarateproductReportOnAction(ActionEvent event) {

    }

    @FXML
    void GenaratesalesReportOnAction(ActionEvent event) {

    }

    @FXML
    void GenaratesupplierReportOnAction(ActionEvent event) {

    }

}

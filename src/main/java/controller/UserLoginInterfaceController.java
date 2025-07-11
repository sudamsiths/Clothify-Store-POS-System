package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserLoginInterfaceController {

    public TextField txtFrogotEmail;
    public TextField txtotpcode;
    public Label lblEmailLabel;
    public JFXButton btnSendOTP;
    public JFXButton btnSubmitOTP;

    // Store the generated OTP
    private String generatedOTP;
    private String userEmail;

    @FXML
    private TextField txtloginemail;

    @FXML
    private PasswordField txtloginpassword;

    @FXML
    void createAccountOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CreateUserAccountInterface.fxml"))));
        s1.show();
    }

    @FXML
    void userforgotpwOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/FrogotPasswordInterface.fxml"))));
        s1.show();
    }

    @FXML
    void userloginOnAction(ActionEvent event) throws IOException, SQLException {
        logindashboard();
    }

    public void logindashboard() throws SQLException, IOException {
        String email = txtloginemail.getText();
        String password = txtloginpassword.getText();

        if (email.isEmpty() && password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter both email and password");
            alert.showAndWait();
            return;
        }

        if (authenticateUser(email, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText("Welcome!");
            alert.setContentText("Login successful for: " + email);
            alert.showAndWait();
            clearField();

            Stage s1 = new Stage();
            s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ServiceListuser.fxml"))));
            s1.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Authentication Error");
            alert.setContentText("Invalid email or password. Please try again.");
            alert.showAndWait();
        }
    }

    private boolean authenticateUser(String email, String password) throws SQLException {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } finally {
            // Close resources if needed
        }
    }

    public void clearField(){
        txtloginemail.clear();
        txtloginpassword.clear();
    }

    public void sendotpOnAction(ActionEvent actionEvent) {
        String forgotEmail = txtFrogotEmail.getText().trim();

        // Validate email input
        if (forgotEmail.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter your email address");
            return;
        }

        // Basic email validation
        if (!isValidEmail(forgotEmail)) {
            showAlert(Alert.AlertType.WARNING, "Invalid Email", "Please enter a valid email address");
            return;
        }

        // Generate 6-digit OTP
        generatedOTP = generateOTP();
        userEmail = forgotEmail;

        try {
            sendOTPEmail(forgotEmail, generatedOTP);

            // Hide email field and show OTP input field
            txtFrogotEmail.setVisible(false);
            lblEmailLabel.setVisible(false);
            btnSendOTP.setVisible(false);

            // Show OTP input field and submit button
            txtotpcode.setVisible(true);
            btnSubmitOTP.setVisible(true);

            showAlert(Alert.AlertType.INFORMATION, "OTP Sent",
                    "A 6-digit OTP has been sent to your email: " + forgotEmail);

        } catch (MessagingException e) {
            showAlert(Alert.AlertType.ERROR, "Email Error",
                    "Failed to send OTP. Please check your email and try again.");
            Logger.getLogger(UserLoginInterfaceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void otpsubmitOnAction(ActionEvent actionEvent) throws IOException {
        String enteredOTP = txtotpcode.getText().trim();

        if (enteredOTP.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter the OTP");
            return;
        }

        if (enteredOTP.equals(generatedOTP)) {
            showAlert(Alert.AlertType.INFORMATION, "OTP Verified",
                    "OTP verification successful! You can now proceed with password reset.");

            // Clear the OTP and reset the form
            clearOTPForm();

            Stage s1 = new Stage();
            s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/restpasswordController.fxml"))));
            s1.show();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } else {
            showAlert(Alert.AlertType.ERROR, "OTP Verification Failed",
                    "Invalid OTP. Please check the code sent to your email and try again.");
            txtotpcode.clear();
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate 6-digit number
        return String.valueOf(otp);
    }

    private void sendOTPEmail(String recipientEmail, String otp) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Replace with your email credentials
        String senderEmail = "sudamsiths@gmail.com"; // Replace with your email
        String senderPassword = "umxx dwid utdi atjx"; // Replace with your app password


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        Message message = prepareOTPMessage(session, senderEmail, recipientEmail, otp);
        Transport.send(message);
    }

    private Message prepareOTPMessage(Session session, String senderEmail, String recipientEmail, String otp) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{
                    new InternetAddress(recipientEmail)
            });
            message.setSubject("Your OTP Code - Password Reset");

            String emailBody = "Dear User,\n\n" +
                    "Your OTP code for password reset is: " + otp + "\n\n" +
                    "This code will expire in 10 minutes.\n" +
                    "If you didn't request this, please ignore this email.\n\n" +
                    "Best regards,\n" +
                    "Your Application Team";

            message.setText(emailBody);
            return message;
        } catch (Exception e) {
            Logger.getLogger(UserLoginInterfaceController.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearOTPForm() {
        txtFrogotEmail.clear();
        txtotpcode.clear();
        generatedOTP = null;
        userEmail = null;

        // Reset visibility
        txtFrogotEmail.setVisible(true);
        lblEmailLabel.setVisible(true);
        btnSendOTP.setVisible(true);
        txtotpcode.setVisible(false);
        btnSubmitOTP.setVisible(false);
    }

    // Original methods kept for backward compatibility
    private void sendEmail(String forgotEmail) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String sendEmail = forgotEmail;
        String password = "bfqq jymu jfrb odyz";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendEmail, password);
            }
        });
        Message message = prepareMessage(session, sendEmail, sendEmail, txtFrogotEmail.getText());
        if (message != null) {
            new Alert(Alert.AlertType.CONFIRMATION, "Email Send Successfully").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION, "Email Send Unsuccessfully").show();
        }
        Transport.send(message);
    }

    private Message prepareMessage(Session session, String sendEmail, String sendEmail1, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{
                    new InternetAddress(sendEmail1)
            });
            message.setSubject("Messages");
            message.setText(msg);
            return message;
        } catch (Exception e) {
            Logger.getLogger(UserLoginInterfaceController.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
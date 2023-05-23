package ma.ydev0.javajdbcgui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.ydev0.javajdbcgui.service.ServiceConnect;

import java.io.IOException;

public class LoginController {
  private static final ServiceConnect serviceConnect = new ServiceConnect();

  @FXML
  private Button btnLogin;

  @FXML
  private PasswordField pfPassword;

  @FXML
  private TextField tfUsername;

  @FXML
  void login(ActionEvent event) {
    String username = tfUsername.getText();
    String password = pfPassword.getText();
    Alert alert;
    if(username.isEmpty() || password.isEmpty()) {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error Message");
      alert.setHeaderText(null);
      alert.setContentText("Please fill all blank fields.");
      alert.show();
    } else if(serviceConnect.connect(username, password)) {
      alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Message");
      alert.setHeaderText(null);
      alert.setContentText("Login Success.");
      alert.showAndWait();
      switchToMainView();
    } else {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error Message");
      alert.setHeaderText(null);
      alert.setContentText("Wrong username or password.");
      alert.show();
    }
  }

  void switchToMainView() {
    try {
      btnLogin.getScene().getWindow().hide();
      Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

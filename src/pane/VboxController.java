/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pane;

import com.jfoenix.controls.JFXButton;
import dialogs.confirm_dialog;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import login.login;
import login.mycontroller;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class VboxController implements Initializable {

    @FXML
    private JFXButton Login;

    @FXML
    private JFXButton register;

    @FXML
    private JFXButton help;

    @FXML
    private JFXButton exite;

    @FXML
    private Text name;
    @FXML
    private ImageView image;

    @FXML
    private JFXButton logout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        javafx.scene.image.Image im1 = new javafx.scene.image.Image(getClass().getResourceAsStream("user.png"));
        image.setImage(im1);
        Login.getStyleClass().add("button");

    }

    public void action(ActionEvent event) throws IOException {

        if (event.getSource() == Login) {

            login login = new login("login");

        }
        if (event.getSource() == register) {
            login login = new login("sign");

        }
        if (event.getSource() == logout) {
            confirm_dialog c = new confirm_dialog("Exite account .... ?", "Are you sure to logout ? ", "ok / cancel ");

        }
        if (event.getSource() == help) {

        }
        if (event.getSource() == exite) {

//            confirm_dialog c = new confirm_dialog("Exite program", "Are you sure to exite ?", "Ok / cancel");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exite program");
            alert.setHeaderText("Are you sure to exite ?");
            alert.setContentText("Ok / cancel");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                // ... user chose CANCEL or closed the dialog
            }
//         System.exit(0);
        }

    }

    void user(String nname) {

        name.setText(nname);
        Login.setDisable(true);
        register.setDisable(true);

    }

    void cancel_logout() {

        logout.setDisable(true);

    }

}

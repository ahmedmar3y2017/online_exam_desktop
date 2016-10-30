/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pane;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        Login.getStyleClass().add("button");

    }

    public void action(ActionEvent event) throws IOException {

        if (event.getSource() == Login) {
  
            login login = new login("login");
       
            

        }
        if (event.getSource() == register) {
            login login = new login("sign");

        }
        if (event.getSource() == help) {

        }
        if (event.getSource() == exite) {

        }

    }

}

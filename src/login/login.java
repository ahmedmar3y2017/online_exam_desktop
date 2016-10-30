/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author programmer
 */
public class login {

    static Stage stage;

    public login(String type) throws IOException {
        stage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        FXMLLoader load = new FXMLLoader();
        Parent root = load.load(getClass().getResource("FXMLDocument.fxml").openStream());
        mycontroller user = (mycontroller) load.getController();

        if (type.equals("login")) {
            user.login_main();

        } else if (type.equals("sign")) {
            user.sign_main();
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add("login/style.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login Form");
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();

    }

}

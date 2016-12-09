/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web_view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.mycontroller;

/**
 *
 * @author programmer
 */
public class web {

    public static Stage stage;

    public web(String url) throws IOException {

        stage = new Stage();
        FXMLLoader load = new FXMLLoader();
        Parent root = load.load(getClass().getResource("web.fxml").openStream());
        WebController user = (WebController) load.getController();
        user.open(url);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("web_view/web.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Web View");
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();

    }

}

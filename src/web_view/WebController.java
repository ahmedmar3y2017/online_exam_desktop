/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web_view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class WebController implements Initializable {

    @FXML
    private WebView webview;

    @FXML
    private Button reload;

    @FXML
    private Button cancel;
    private WebEngine webv;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cancel.getStyleClass().add("button");
        reload.getStyleClass().add("button");
        javafx.scene.image.Image imageDecline1 = new javafx.scene.image.Image(getClass().getResourceAsStream("rel.png"));
        reload.setGraphic(new ImageView(imageDecline1));

        javafx.scene.image.Image imageDecline2 = new javafx.scene.image.Image(getClass().getResourceAsStream("cc.png"));
        cancel.setGraphic(new ImageView(imageDecline2));

        webv = webview.getEngine();

    }

    public void action(ActionEvent event) {

        if (event.getSource() == cancel) {

            web.stage.close();
//           webv.stage.
        } else {
            webv.reload();
        }

    }

    public void open(String url) {

        webv.load(url);

    }

}

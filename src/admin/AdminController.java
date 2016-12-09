/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import pane.FxmlController;
import pane.Pane;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class AdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane pane1;

    @FXML
    private ImageView image1;
    @FXML
    private Label label1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private ImageView image2;

    @FXML
    private Label label2;

    @FXML
    private AnchorPane pane3;

    @FXML
    private ImageView image3;

    @FXML
    private Label label3;

    @FXML
    private StackPane stack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        javafx.scene.image.Image im1 = new javafx.scene.image.Image(getClass().getResourceAsStream("courseee.png"));
        image1.setImage(im1);

        javafx.scene.image.Image im2 = new javafx.scene.image.Image(getClass().getResourceAsStream("q.png"));
        image2.setImage(im2);
        javafx.scene.image.Image im3 = new javafx.scene.image.Image(getClass().getResourceAsStream("l.png"));
        image3.setImage(im3);
//    
        pane1.getStyleClass().add("home");
        label1.getStyleClass().add("label1");
        pane2.getStyleClass().add("home");
        label2.getStyleClass().add("label1");
        pane3.getStyleClass().add("home");
        label3.getStyleClass().add("label1");
        try {
            Node ppp = FXMLLoader.load(getClass().getResource("/CourseAdmin/course.fxml"));
//            pane.getChildren().clear();
            stack.getChildren().add(ppp);
        } catch (IOException ex) {
            Logger.getLogger(FxmlController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void action(MouseEvent event) {

        if (event.getSource() == pane1) {
            try {
                Node ppp = FXMLLoader.load(getClass().getResource("/CourseAdmin/course.fxml"));
                stack.getChildren().clear();
                stack.getChildren().add(ppp);
            } catch (IOException ex) {
                Logger.getLogger(FxmlController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (event.getSource() == pane2) {
            try {
                Node ppp = FXMLLoader.load(getClass().getResource("/QuestionAdmin/question.fxml"));
                stack.getChildren().clear();
                stack.getChildren().add(ppp);
            } catch (IOException ex) {
                Logger.getLogger(FxmlController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (event.getSource() == pane3) {
            try {
                Node ppp = FXMLLoader.load(getClass().getResource("/LinkAdmin/link.fxml"));
                stack.getChildren().clear();
                stack.getChildren().add(ppp);
            } catch (IOException ex) {
                Logger.getLogger(FxmlController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

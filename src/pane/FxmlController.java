/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pane;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class FxmlController implements Initializable {

    @FXML
    private StackPane start;

    @FXML
    private AnchorPane home;

    @FXML
    private Label label1;

    @FXML
    private AnchorPane search;

    @FXML
    private Label label2;

    @FXML
    private AnchorPane grade;

    @FXML
    private Label label3;

    @FXML
    private AnchorPane admin;

    @FXML
    private Label label4;
    @FXML
    private AnchorPane about_us;

    @FXML
    private Label label5;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane exam;

    @FXML
    private Label exam_label;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // set images to images views
        javafx.scene.image.Image im1 = new javafx.scene.image.Image(getClass().getResourceAsStream("home.png"));
        image1.setImage(im1);

        javafx.scene.image.Image im2 = new javafx.scene.image.Image(getClass().getResourceAsStream("search.png"));
        image2.setImage(im2);

        javafx.scene.image.Image im3 = new javafx.scene.image.Image(getClass().getResourceAsStream("exam.png"));
        image3.setImage(im3);

        javafx.scene.image.Image im4 = new javafx.scene.image.Image(getClass().getResourceAsStream("grade.png"));
        image4.setImage(im4);

        javafx.scene.image.Image im5 = new javafx.scene.image.Image(getClass().getResourceAsStream("admin.png"));
        image5.setImage(im5);

        javafx.scene.image.Image im6 = new javafx.scene.image.Image(getClass().getResourceAsStream("about.png"));
        image6.setImage(im6);

        // set style to labels and pane
        home.getStyleClass().add("home");
        label1.getStyleClass().add("label1");

        search.getStyleClass().add("home");
        label2.getStyleClass().add("label1");

        grade.getStyleClass().add("home");
        label3.getStyleClass().add("label1");

        admin.getStyleClass().add("home");
        label4.getStyleClass().add("label1");

        about_us.getStyleClass().add("home");
        label5.getStyleClass().add("label1");

        exam.getStyleClass().add("home");
        exam_label.getStyleClass().add("label1");

        try {
            VBox v = FXMLLoader.load(getClass().getResource("vbox.fxml"));
            v.setStyle("vbox.css");
            drawer.setSidePane(v);
        } catch (IOException ex) {
            Logger.getLogger(FxmlController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HamburgerBackArrowBasicTransition hab2 = new HamburgerBackArrowBasicTransition(hamburger);
        hab2.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            hab2.setRate(hab2.getRate() * -1);

            hab2.play();
            if (drawer.isShown()) {
                drawer.close();
            } else {

                drawer.open();

            }

        });

//        home.setStyle("-fx-border-color: red ; -fx-border-width: 1px ; -fx-background-color: red ; ");
        try {
            Node ppp = FXMLLoader.load(getClass().getResource("start.fxml"));
//            pane.getChildren().clear();
            start.getChildren().add(ppp);
        } catch (IOException ex) {
            Logger.getLogger(FxmlController.class.getName()).log(Level.SEVERE, null, ex);
        }

        exam.setDisable(true);
        grade.setDisable(true);
        admin.setDisable(true);

        exam.setStyle("-fx-border-color: red ; -fx-border-width: 1px;");
        grade.setStyle("-fx-border-color: red ; -fx-border-width: 1px;");
        admin.setStyle("-fx-border-color: red ; -fx-border-width: 1px;");

//        javafx.scene.image.Image r3 = new javafx.scene.image.Image(getClass().getResourceAsStream("error.png"));
//        image3.setImage(r3);
//
//        javafx.scene.image.Image r4 = new javafx.scene.image.Image(getClass().getResourceAsStream("error.png"));
//        image4.setImage(r4);
//
//        javafx.scene.image.Image r5 = new javafx.scene.image.Image(getClass().getResourceAsStream("error.png"));
//        image5.setImage(r5);
    }

    public void action(ActionEvent e) throws IOException {

    }

    public void mouse_Event(MouseEvent ee) throws IOException {

        if (ee.getSource() == home) {
            System.out.println("home");

            Node p = FXMLLoader.load(getClass().getResource("start.fxml"));
            start.getChildren().clear();

            start.getChildren().add(p);

        }

        if (ee.getSource() == search) {
            System.out.println("search");

            Node p = FXMLLoader.load(getClass().getResource("/search/search.fxml"));
            start.getChildren().clear();

            start.getChildren().add(p);

        }
        if (ee.getSource() == grade) {
            System.out.println("grade");

            Node p = FXMLLoader.load(getClass().getResource("/grade/grade.fxml"));
            start.getChildren().clear();

            start.getChildren().add(p);

        }
        if (ee.getSource() == admin) {
            System.out.println("admin");

            Node p = FXMLLoader.load(getClass().getResource("/admin/admin.fxml"));
            start.getChildren().clear();

            start.getChildren().add(p);

        }
        if (ee.getSource() == about_us) {
            System.out.println("about_us");

            Node p = FXMLLoader.load(getClass().getResource("/about_us/about_us.fxml"));
            start.getChildren().clear();

            start.getChildren().add(p);

        }
        if (ee.getSource() == exam) {
            System.out.println("exam");

            Node p = FXMLLoader.load(getClass().getResource("/exam/exam.fxml"));
            start.getChildren().clear();

            start.getChildren().add(p);

        }
    }

    public void user_privilage() {

        exam.setDisable(false);
        grade.setDisable(false);

        exam.setStyle("-fx-border-color: white ;");
        grade.setStyle("-fx-border-color: white ;");

    }

    public void admin_privilage() {

        exam.setDisable(false);
        grade.setDisable(false);
        admin.setDisable(false);

        exam.setStyle("-fx-border-color: white ;");
        grade.setStyle("-fx-border-color: white ;");
        admin.setStyle("-fx-border-color: white ;");

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.database;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;
import pane.Pane;
import pane.start;

/**
 *
 * @author Amira
 */
public class mycontroller implements Initializable {

    @FXML
    private TabPane mainpane;

    @FXML
    private Tab btn_log;

    @FXML
    private AnchorPane pane1;

    @FXML
    private Label email1_label;

    @FXML
    private Label pass1_label;

    @FXML
    private JFXTextField txt_email1;

    @FXML
    private JFXPasswordField txt_pass1;

    @FXML
    private Button ok_btn;

    @FXML
    private Button cancel_btn;

    @FXML
    private Label label_email_exption;

    @FXML
    private Label label_pass_exption;

    @FXML
    private Tab sign_btn;

    @FXML
    private Label name_lable;

    @FXML
    private Label email2_label;

    @FXML
    private Label pass2_label;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_email2;

    @FXML
    private PasswordField txt_pass2;

    @FXML
    private Button signup_btn;

    @FXML
    private Button cancelup_btn;

    @FXML
    private Label label_name_exption;

    @FXML
    private Label label_email2_exption;

    @FXML
    private Label label_pass2_exption;

    @FXML
    private PasswordField txt_confirm;

    @FXML
    private Label label_confirm_exiption;
    @FXML
    private Label label_box1;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;
    @FXML
    private ComboBox<String> combobox1;
    ObservableList<String> List = FXCollections.observableArrayList("user", "Admin");

    ObservableList<String> List2 = FXCollections.observableArrayList("user", "Admin");

    public void control(ActionEvent e) {
        clear_login();
        // check if the fields is empty
        if (txt_email1.getText().equals("") || txt_pass1.getText().equals("") || combobox1.getSelectionModel().getSelectedItem() == null) {
// if the login email is empty
            if (txt_email1.getText().equals("")) {

                label_email_exption.setText("Email is required");
                txt_email1.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

            }
// if the password email is empty
            if (txt_pass1.getText().equals("")) {
                label_pass_exption.setText("Password is required");
                txt_pass1.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

            }// if the box1 is empty

            if (combobox1.getSelectionModel().getSelectedItem() == null) {
                label_box1.setText("please select ..... ?");

            }

        } // else if the fielid is not empty
        else {

            // if email is not a form 
            if (!txt_email1.getText().contains("@")) {
                label_email_exption.setText("Error ... ex/ ...example@yahoo.com");
                txt_email1.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

            } // id all Done
            else {
// this is part special database check Email and password 
                String type = combobox1.getSelectionModel().getSelectedItem();
                String email1 = txt_email1.getText();
                String pass1 = txt_pass1.getText();

                if (type.equals("admin")) {

                    System.out.println("Admin");

                } 
                // if this is user 
                else {
                    try {
                        database data = new database();
                        if (data.check_user_login(email1, pass1)) {

                            String name = data.get_name(email1);
                            
                            login.stage.close();
                            Pane.s.close();
                            try {
                                start s=new start("user",name);
                            } catch (IOException ex) {
                                Logger.getLogger(mycontroller.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            push_notification("corr.png", "Done Login .... ", "Hello  mr/ms : " + name);
                        } else {
                            push_notification("error.png", "Error .... ", "This account not Exist \n\n Please sign up .....");
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(mycontroller.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(mycontroller.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        }

    }

    public void cancel(ActionEvent e) {
        login.stage.close();
    }

    public void control2(ActionEvent e) throws IOException {
        clear_sign();
        if (txt_email2.getText().equals("") || txt_name.getText().equals("") || txt_pass2.getText().equals("") || txt_confirm.getText().equals("")) {

            if (txt_name.getText().equals("")) {

                label_name_exption.setText("UserName is required .....");
                txt_name.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

            }

            if (txt_email2.getText().equals("")) {

                label_email2_exption.setText("Email is required .....");
                txt_email2.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

            }

            if (txt_pass2.getText().equals("")) {

                label_pass2_exption.setText("Password is required .....");
                txt_pass2.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

            }
            if (txt_confirm.getText().equals("")) {

                label_confirm_exiption.setText("Confirm Password is required .....");
                txt_confirm.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

            }

        } else {

            if (!txt_email2.getText().contains("@") || !(txt_pass2.getText().equals(txt_confirm.getText()))) {

                if (!txt_email2.getText().contains("@")) {
                    label_email2_exption.setText("Invaid Email : ex ... @yahoo");
                    txt_email2.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

                }
                if (!(txt_pass2.getText().equals(txt_confirm.getText()))) {
                    label_confirm_exiption.setText("Pssword , confirm not matched ......");
                    txt_confirm.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

                }

            } else {

                try {
                    // ------------------ insert user ------------------------------

                    String name = txt_name.getText();
                    String email = txt_email2.getText();
                    String pass = txt_pass2.getText();
// insert into user table database
                    database data = new database();

                    if (data.check_user(email)) {
                        push_notification("error.png", "Error .... ", "This Email already Used .... !");

                    } else {
                        data.insert_user(name, email, pass);
                        clear_sign();
                        login.stage.close();

                        push_notification("corr.png", "Done ... ", "Done sign up \n Hello mr/ms :" + name);

                    }

//                    new dialogs.dialog(Alert.AlertType.INFORMATION, "Login successfully ... ", "Done Sign up : mr/ms " + name);
                } catch (SQLException ex) {
                    new dialogs.dialog(Alert.AlertType.ERROR, "Error", ex.getMessage());

                } catch (ClassNotFoundException ex) {
                    new dialogs.dialog(Alert.AlertType.ERROR, "Error", ex.getMessage());
                }

            }

        }

    }

    public void cancel2(ActionEvent e) {
        login.stage.close();

    }

    public void login_main() {

        // make login is the main 
        mainpane.getSelectionModel().select(btn_log);
        sign_btn.setDisable(true);
    }

    public void sign_main() {

        // make login is the main 
        mainpane.getSelectionModel().select(sign_btn);
        btn_log.setDisable(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        File file = new File("src/change.png");
//        javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
        image1.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("ami.png")));
        image2.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("change.png")));

        combobox1.setItems(List);

        // make login is the main 
        mainpane.getSelectionModel().select(btn_log);

        // button styles 
        ok_btn.getStyleClass().add("button");
        cancel_btn.getStyleClass().add("button");
        //box styles
        combobox1.getStyleClass().add("box");
        // set button image
        javafx.scene.image.Image imageDecline = new javafx.scene.image.Image(getClass().getResourceAsStream("nnot.png"));

        cancel_btn.setGraphic(new ImageView(imageDecline));

        javafx.scene.image.Image imageDecline3 = new javafx.scene.image.Image(getClass().getResourceAsStream("nnot.png"));

        cancelup_btn.setGraphic(new ImageView(imageDecline3));

        javafx.scene.image.Image imageDecline2 = new javafx.scene.image.Image(getClass().getResourceAsStream("login.png"));

        ok_btn.setGraphic(new ImageView(imageDecline2));

        javafx.scene.image.Image imageDecline4 = new javafx.scene.image.Image(getClass().getResourceAsStream("sign.png"));

        signup_btn.setGraphic(new ImageView(imageDecline4));

        txt_email1.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // if not focused 
                if (!newValue) {
                    if (!txt_email1.getText().equals("")) {
                        if (txt_email1.getText().contains("@")) {

                            label_email_exption.setText("");
                            txt_email1.setStyle("-fx-focus-color: transparent;");
                        } else {
                            label_email_exption.setText("Error ... ex/ ...example@yahoo.com");
                            txt_email1.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

                        }

                    } else if (txt_email1.getText().equals("")) {

                        label_email_exption.setText("Email is required");
                        txt_email1.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

                    }

                }
            }
        });
        txt_pass1.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // if not focused 
                if (!newValue) {

                    if (!txt_pass1.getText().equals("")) {
                        label_pass_exption.setText("");
                        txt_pass1.setStyle("-fx-focus-color: transparent;");

                    } else {

                        label_pass_exption.setText("Password is required");
                        txt_pass1.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");

                    }

                }
            }
        });

    }

    public void clear_sign() {

        label_email2_exption.setText("");
        label_pass2_exption.setText("");
        label_name_exption.setText("");
        label_confirm_exiption.setText("");
        txt_name.setStyle("-fx-border-color: silver ; -fx-border-width: 1px ;");
        txt_email2.setStyle("-fx-border-color: silver ; -fx-border-width: 1px ;");
        txt_pass2.setStyle("-fx-border-color: silver ; -fx-border-width: 1px ;");
        txt_confirm.setStyle("-fx-border-color: silver ; -fx-border-width: 1px ;");

    }

    public void clear_login() {
        label_email_exption.setText("");
        label_pass_exption.setText("");
        label_box1.setText("");

        txt_email1.setStyle("-fx-border-color: silver ; -fx-border-width: 1px ;");
        txt_pass1.setStyle("-fx-border-color: silver ; -fx-border-width: 1px ;");

    }

    public void push_notification(String image, String title, String text) {

        Notifications noti = Notifications.create().
                title(title)
                .text(text)
                .position(Pos.TOP_RIGHT)
                .graphic(new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream(image))))
                .hideAfter(Duration.seconds(4));
        noti.darkStyle();
        noti.show();

    }
}

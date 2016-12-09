/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import database.database;
import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class SearchController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView image;
    @FXML
    private JFXTextField input;
    
    @FXML
    private Label detail;
    
    @FXML
    private JFXButton search;
    
    @FXML
    private VBox vbox;
    @FXML
    private AnchorPane pane;
    
    @FXML
    private Text text;
    
    @FXML
    private Text youtube;
//    ObservableList<String> l = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        detail.setTextAlignment(TextAlignment.CENTER);
        // search button image

        youtube.setVisible(false);
        text.setVisible(false);
        javafx.scene.image.Image imageDecline = new javafx.scene.image.Image(getClass().getResourceAsStream("eee.png"));
        search.setGraphic(new ImageView(imageDecline));
        
        javafx.scene.image.Image im = new javafx.scene.image.Image(getClass().getResourceAsStream("ss.png"));
//        search.setGraphic(new ImageView(im));
        image.setImage(im);

        // set style to button
        search.getStylesheets().add("button");
        detail.setVisible(false);
//        l.addAll("ahmed", "aaa", "mohamed", "mmmm");
        // auto com[lete course from database 
        try {
            database d = new database();
            TextFields.bindAutoCompletion(input, d.course_re());
        } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void action(ActionEvent event) throws SQLException, ClassNotFoundException {
        database data = new database();
        
        if (event.getSource() == search) {
            if (input.getText().equals("")) {
                new dialogs.dialog(Alert.AlertType.ERROR, "Search Empty", "ENter the course name");
                
            } else {
                if (data.exist(input.getText())) {

//                    new dialogs.dialog(Alert.AlertType.INFORMATION, "Done", "Done");
                    String det = "";
                    int course_id = 0;
                    String time = "";
                    String ffinal = "";
                    String min_grade = "";
                    
                    ResultSet resultSet = data.get_all_course(input.getText());
                    
                    if (resultSet.next()) {
                        det = resultSet.getString("description");
                        course_id = resultSet.getInt("id");
                        time = resultSet.getString("time");
                        ffinal = resultSet.getString("final");
                        min_grade = resultSet.getString("min_grade");
                        
                    }
                    
                    List<String> list = data.get_links(course_id);
                    List<String> link_name = data.get_links_name(course_id);
//                    System.out.println(link_name);
                    int counter = list.size();
                    vbox.getChildren().clear();
                    for (int i = 0; i < counter; i++) {
                        Hyperlink link = new Hyperlink(link_name.get(i));
                        link.setFont(new Font("Cambria", 25));
                        link.setTooltip(new Tooltip(list.get(i)));
                        link.setGraphic(new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("y.png"))));
                        System.out.println(link.getTooltip().getText());
                        link.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (event.getSource() == link) {
                                    try {
                                        if (netIsAvailable()) {

//                                            Desktop.getDesktop().browse(new URI(link.getText()));
                                            web_view.web w = new web_view.web(link.getText());
                                            
                                        } else {
                                            
                                            push_notification("error.png", "Check Internet ...", "Check internet connection ...");
                                            
                                        }
                                    } catch (IOException ex) {
                                        Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                
                            }
                        });
                        youtube.setVisible(true);

//                        link.setText("ahmed");
                        vbox.getChildren().add(link);
                    }
                    
                    text.setVisible(true);
                    detail.setVisible(true);
                    
                    detail.setText("The course take time : " + time + " ,\n Th final is  : " + ffinal + " \n and min_grade  : " + min_grade + "\n Details is : " + det);
                } else {
                    new dialogs.dialog(Alert.AlertType.ERROR, "Error", "Course Not found");
                    
                }
                
            }
            
        }
        
    }
    
    private static boolean netIsAvailable() throws IOException {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
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

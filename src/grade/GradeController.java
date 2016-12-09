/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grade;

import database.database;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import login.login;
import login.mycontroller;

/**
 * FXML Controller class
 *
 * @author programmer
 */
public class GradeController implements Initializable {

    /**
     * Initializes the controller class.
     */
//    @FXML
//    private Label text;
    @FXML
    private Label text;
    @FXML
    private VBox grade_vbox;
    @FXML
    private ImageView image;

   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//System.out.println(+"++++++++++++++++++++++++++++++++++++++++++");
        database data = null;
        try {
            data = new database();

        } catch (SQLException ex) {
            Logger.getLogger(GradeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GradeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String yy = mycontroller.getEemail();

        try {
            text.setText(data.get_user_name(yy));

            int id = data.select_user_id(yy);

            ResultSet re = data.select_certification_info(id);
//            if (re.next() == true) {
            while (re.next()) {

                Label name = new Label("Course Name :  " + re.getString("name"));
                Label grade = new Label("Your Grades :  " + String.valueOf(re.getInt("grade")));

                Label date = new Label("Date :  " + re.getDate("date").toString());
                Label dd = new Label("---------------------------------------------------------------------");
                name.setFont(new Font(30));
                grade.setFont(new Font(25));
                date.setFont(new Font(25));
//                name.setStyle("-fx-text-fill: #000 !important; -fx-highlight-text-fill: #000 !important; -fx-font-family: Arial");
                grade_vbox.getChildren().addAll(name, grade, date, dd);

            }

//            } else {
//
//                Label nn = new Label("Not Exam in course yet .....");
//                nn.setFont(new Font(40));
//
//                grade_vbox.getChildren().addAll(nn);
//
//            }
        } catch (SQLException ex) {
            Logger.getLogger(GradeController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        text.setText(yy);

        javafx.scene.image.Image imageDecline = new javafx.scene.image.Image(getClass().getResourceAsStream("g.png"));

        image.setImage(imageDecline);

    }

//    public void set() {
//
//        System.out.println("Done");
//    
//    }
    public void set() {

        System.out.println("Done");

    }

}

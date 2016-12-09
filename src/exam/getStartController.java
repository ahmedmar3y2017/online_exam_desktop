package exam;

import com.jfoenix.controls.JFXComboBox;
import database.database;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import login.mycontroller;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class getStartController implements Initializable {

    @FXML
    private JFXComboBox<String> courseCom;
    @FXML
    private Label name, time, Final, min, desc, error;
    @FXML
    private Button startBtn;
    @FXML
    private AnchorPane startPane;

    Connection conn = database.getConnection();
    Statement stat = null;
    public static String n = "";
    public static int id = 0;
    public static int totalTime = 0;
    public static int firstID = 0;

    public static int min_grade;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startBtn.setDisable(true);
        //Select courses names and put them in ComboBox
        try {
            stat = conn.createStatement();
            ResultSet result = stat.executeQuery("select name from course");
            while (result.next()) {
                courseCom.getItems().add(result.getString("name"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        ///////////////////////////////
        //Action ComboBox-choices
        courseCom.setOnAction((ActionEvent e) -> {

            n = courseCom.getValue();
            startBtn.setDisable(false);
            name.setText(n);
            //Course Information
            try {
                conn = database.getConnection();
                stat = conn.createStatement();
                ResultSet result = stat.executeQuery("select * from course where name='" + n + "'");
                while (result.next()) {
                    time.setText(result.getString(3) + " Minutes");
                    Final.setText(result.getString(4) + " Points");
                    min.setText(result.getString(5) + " Points");
                    desc.setText(result.getString(6));
                    id = result.getInt("id");
                    totalTime = result.getInt("time");

                    min_grade = result.getInt("min_grade");

                }
                ///////Course questions availability////////////
                result = stat.executeQuery("select * from question where course_id=" + id);
                if (result.next()) {
                    result.last();
                    int num = result.getRow();
                    result.first();
                    firstID = result.getInt(1);
                    if (num < 10) {
                        startBtn.setDisable(true);
                        error.setText("There is no enought questions Now");
                    } else {
                        error.setText("");
                    }

                } else {

                    startBtn.setDisable(true);
                    error.setText("There is no questions Now for this course");
                }

            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        });

    }

    /**
     *
     * @throws IOException
     */
    //Change the scene
    public void change() throws IOException, JRException, SQLException, ClassNotFoundException {
        // System.out.println("aHMED");

        database data = new database();

        int user_id = data.select_user_id(mycontroller.getEemail());

        if (data.check_certificate(user_id, id)) {

            new dialogs.dialog(Alert.AlertType.INFORMATION, "Warning ...", "You have this course certificate ");

        } else {

            Node p = FXMLLoader.load(getClass().getResource("exam.fxml"));
            startPane.getChildren().clear();
            startPane.getChildren().add(p);

        }
//

//
//        }
    }
}

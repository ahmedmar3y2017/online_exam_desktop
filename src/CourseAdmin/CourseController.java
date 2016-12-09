/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CourseAdmin;

//import LinkAdmin.datalinks;
import LinkAdmin.links;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.database;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class CourseController implements Initializable {

    // courses
    @FXML
    private TableView course_table;
    @FXML
    private TableColumn<course, Integer> id;
    @FXML
    private TableColumn<course, String> name;
    @FXML
    private TableColumn<course, String> time;
    @FXML
    private TableColumn<course, String> finall;
    @FXML
    private TableColumn<course, String> min_grade;

    @FXML
    private TableColumn<course, String> discription;

    @FXML
    private JFXButton btn_clear;

    @FXML
    private JFXButton btn_add;
    @FXML
    private JFXButton btn_remove;
    @FXML
    private JFXButton btn_update;
    @FXML
    private JFXTextField txt_filter;
    @FXML
    private JFXTextField id_coursetxt;
    @FXML
    private JFXTextField name_coursetxt;
    @FXML
    private JFXTextField discription_txt;
    @FXML
    private JFXTextField final_coursetxt;
    @FXML
    private JFXComboBox time_coursecombo;
    @FXML
    private JFXTextField mingrade_txt;
    @FXML
    private JFXComboBox admin_compobox1;
@FXML
    void key_type(KeyEvent event) {

    
     if (!Character.isDigit(event.getCharacter().charAt(0))) {
        
            event.consume();

        } }
    ////////////////
    ObservableList<course> list = FXCollections.observableArrayList();
    FilteredList<course> filterddata = new FilteredList<>(list, e -> true);

    @FXML
    public void search_courses() {
        txt_filter.textProperty().addListener((ObservableValue, oldvalue, newvalue) -> {
            filterddata.setPredicate((Predicate<course>) course -> {

                if (newvalue == null || newvalue.isEmpty()) {
                    return true;

                }
                String lowercasefilter = newvalue.toLowerCase();
                if (course.getName().toLowerCase().contains(lowercasefilter)) {
                    return true;
                }

                return false;
            });

        });
        SortedList<course> sorteddata = new SortedList<>(filterddata);

        sorteddata.comparatorProperty().bind(course_table.comparatorProperty());
        course_table.setItems(sorteddata);

    }

//    @FXML
    public void action_course_table(ActionEvent event) throws SQLException, ClassNotFoundException {
        database dc = new database();
        String name = name_coursetxt.getText();
        String finall = final_coursetxt.getText();
        String min = mingrade_txt.getText();
        String dis = discription_txt.getText();
//                String minlength = mingrade_txt.getText();
//                        String finallength = final_coursetxt.getText();

//String adm=admin_compobox1.getSelectionModel().getSelectedItem().toString();
        String arr[] = {name, finall, min, dis};
        String arr2[] = {"please enter course name ,", " final grade requird,", "minimum_grade requird ", " discription requerd , "};
        String message = "";
        ////////////////////////////to min _finalll valid
        String arr_valid[] = {finall, min};
        String arr2_message[] = {" final grade must not be more than 3 character ,", "minimum_grade must not be more than 3 character  "};
        String message2 = "";
        Vector vv = dc.validation();
        //insert 
        if (event.getSource() == btn_add) {

            if (admin_compobox1.getSelectionModel().getSelectedItem() == null) {

                new dialogs.dialog(Alert.AlertType.NONE, "empty", "please select the admin first");
            } else if (vv.contains(name)) {
                new dialogs.dialog(Alert.AlertType.NONE, "Contai name", "the course exsist before");

//                JOptionPane.showMessageDialog(null, " the course exsist before ");
            } else if (name.equals("") || finall.equals("") || min.equals("") || dis.equals("") || time_coursecombo.getSelectionModel().getSelectedItem() == null) {

                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("")) {
                        message += arr2[i];

                    }
                    if (time_coursecombo.getSelectionModel().getSelectedItem() == null) {
                        message += " select time";
                    }

                }
                new dialogs.dialog(Alert.AlertType.NONE, "Error", message);

                //validate min_finall
            } else if (finall.length() > 3 || min.length() > 3) {

                for (int i = 0; i < arr_valid.length; i++) {
                    if (arr_valid[i].length() > 3) {
                        message2 += arr2_message[i];

                    }

                }
                new dialogs.dialog(Alert.AlertType.NONE, "Error", message2);

            } else {

                System.out.println("insert ++++++++++++++++++");

                dc.insert_course(name, time_coursecombo, finall, min, dis, admin_compobox1);
                System.out.println("Done ++++++++++++++++++");

// refresh table
//                if (course_table.getItems().removeAll(list)) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    list.remove(i);
                }
//select all item from database and set it in list
                List<course> ll = dc.select_admin_courses(admin_compobox1);
                for (course ui : ll) {
                    int r = ui.getId();
                    String t = ui.getName();
                    String o = ui.getTime();
                    String s = ui.getFinall();
                    String v = ui.getMin_grade();
                    String w = ui.getDiscription();

                    list.add(new course(r, t, o, s, v, w));
                }
//                    course_table.getItems().add(list);
                course_table.setItems(list);
                new dialogs.dialog(Alert.AlertType.NONE, "Done", "Added success");

//remove
            }
        } else if (event.getSource() == btn_remove) {
            try {
                int id = 0;
                if (!id_coursetxt.getText().equals("")) {
                    id = Integer.parseInt(id_coursetxt.getText());
                }
                String idtext = id_coursetxt.getText();
                if (idtext.equals("")) {
                    new dialogs.dialog(Alert.AlertType.NONE, "Select row", "please select the row u want to deleted");

//                    JOptionPane.showMessageDialog(null, "please select the row u want to deleted");
                } else {
                    //      System.out.println("deleted " + id);

                    dc.delete(id);
//refresh table
                    // if (course_table.getItems().removeAll(list)) {
                    //delete the last list 
                    for (int i = list.size() - 1; i >= 0; i--) {
                        list.remove(i);
                    }
                    List<course> ll = dc.select_admin_courses(admin_compobox1);
                    for (course ui : ll) {
                        int r = ui.getId();
                        String t = ui.getName();
                        String o = ui.getTime();
                        String s = ui.getFinall();
                        String v = ui.getMin_grade();
                        String w = ui.getDiscription();
//add new list
                        list.add(new course(r, t, o, s, v, w));
                    }
                    System.out.println(list.toString());
//
                    course_table.setItems(list);
                    new dialogs.dialog(Alert.AlertType.NONE, "D", "DElete successfully");

                    //}
                }
            } catch (Exception e) {

                new dialogs.dialog(Alert.AlertType.NONE, "", e.getMessage());

            }

        } else if (event.getSource() == btn_update) {
            String nameu = name_coursetxt.getText();
            String finallu = final_coursetxt.getText();
            String minu = mingrade_txt.getText();
            String disu = discription_txt.getText();
            int id = 0;
            String valid_update[] = {name, finall, min, dis};
            String valid_message[] = {"please enter course name ,", " final grade requird,", "minimum_grade requird ", " discription requerd , "};
            String message3 = "";
            if (!id_coursetxt.getText().equals("")) {
                id = Integer.parseInt(id_coursetxt.getText());

            }
            String idtxt = id_coursetxt.getText();
            if (idtxt.equals("")) {
                new dialogs.dialog(Alert.AlertType.NONE, "", "please select the course u want to updated");

//                JOptionPane.showMessageDialog(null, "please select the course u want to updated");

            } else if (nameu.equals("") || finallu.equals("") || minu.equals("") || disu.equals("") || time_coursecombo.getSelectionModel().getSelectedItem() == null) {

                for (int i = 0; i < valid_update.length; i++) {
                    if (valid_update[i].equals("")) {
                        message3 += valid_message[i];

                    }
                    if (time_coursecombo.getSelectionModel().getSelectedItem() == null) {
                        message += "select time";
                    }

                }
//                JOptionPane.showMessageDialog(null, message3);
                new dialogs.dialog(Alert.AlertType.NONE, "",  message3);

            } else if (finall.length() > 3 || min.length() > 3) {

                for (int i = 0; i < arr_valid.length; i++) {
                    if (arr_valid[i].length() > 3) {
                        message2 += arr2_message[i];

                    }

                }
//                JOptionPane.showMessageDialog(null, message2);
                new dialogs.dialog(Alert.AlertType.NONE, "",  message2);

            } else {
                dc.update(id, time_coursecombo, finallu, minu, disu, admin_compobox1);
                System.out.println("+++++++++");
                //if (course_table.getItems().removeAll(list)) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    list.remove(i);
                }
                List<course> ll = dc.select_admin_courses(admin_compobox1);
                for (course ui : ll) {
                    int r = ui.getId();
                    String t = ui.getName();
                    String o = ui.getTime();
                    String s = ui.getFinall();
                    String v = ui.getMin_grade();
                    String w = ui.getDiscription();

                    list.add(new course(r, t, o, s, v, w));
                }
                System.out.println(list.toString());
//
                course_table.setItems(list);
            }
            //}
        }
    }

    @FXML
    public void action_clear(ActionEvent e) {
        if (e.getSource() == btn_clear) {
            id_coursetxt.setText("");
            name_coursetxt.setText("");
            final_coursetxt.setText("");
            mingrade_txt.setText("");
            time_coursecombo.setValue(null);
            discription_txt.setText("");
            admin_compobox1.setValue(null);
        }

    }

    @FXML
    public void action_courses_admin() throws SQLException, ClassNotFoundException {
        database dc = new database();

        //  if (link_table.getItems().removeAll(listlink)) {
        for (int i = list.size() - 1; i >= 0; i--) {
            list.remove(i);
        }
// table.getItems().addAll(list);
        List<course> ll = dc.select_admin_courses(admin_compobox1);

        list.addAll(ll);

        course_table.setItems(list);
//            System.out.println(ll.size());

        //}
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database dc = null;
        try {
            dc = new database();
        } catch (SQLException ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //    ObservableList<String> listcoursename =(ObservableList<String>) dc.select_courses_name();
        // TextFields.bindAutoCompletion(txt_filter,listcoursename);
        id_coursetxt.setDisable(true);
        //intilize admin_combo
        ObservableList<String> lis = (ObservableList<String>) dc.select_admin();
        admin_compobox1.setItems(lis);
//        admin_compobox1.setValue(lis.get(1));

// initialize times combo
        time_coursecombo.getItems().addAll("10", "15", "20", "25", "30", "45", "60", "90", "120");

        //intialize table_course
//        List<course> li = dc.course_selectall();
//        for (course ob : li) {
//            int id = ob.getId();
//            String uu = ob.getName();
//            String pp = ob.getTime();
//            String v = ob.getFinall();
//            String m = ob.getMin_grade();
//            String dis = ob.getDiscription();
//
//            list.add(new course(id, uu, pp, v, m, dis));
//
//        }
        id.setCellValueFactory(new PropertyValueFactory<course, Integer>("id"));

        name.setCellValueFactory(new PropertyValueFactory<course, String>("name"));

        time.setCellValueFactory(new PropertyValueFactory<course, String>("time"));
        finall.setCellValueFactory(new PropertyValueFactory<course, String>("finall"));
        min_grade.setCellValueFactory(new PropertyValueFactory<course, String>("min_grade"));
        discription.setCellValueFactory(new PropertyValueFactory<course, String>("discription"));

        course_table.getItems().addAll(list);

        // put the row in text field
        course_table.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (!course_table.getSelectionModel().isEmpty()) {

                    course c = (course) course_table.getSelectionModel().getSelectedItem();
                    id_coursetxt.setText(String.valueOf(c.getId()));
                    name_coursetxt.setText(c.getName());
                    time_coursecombo.setValue(c.getTime());
                    final_coursetxt.setText(c.getFinall());
                    mingrade_txt.setText(c.getMin_grade());
                    discription_txt.setText(c.getDiscription());
                }

            }
        });

    }

}

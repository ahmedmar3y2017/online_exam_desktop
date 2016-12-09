/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionAdmin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.database;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class QuestionController implements Initializable {

    @FXML
    private TableView question_table;
    @FXML
    private TableColumn<question, Integer> qid;
    @FXML
    private TableColumn<question, String> question;
    @FXML
    private TableColumn<question, String> correct;
    @FXML
    private TableColumn<question, String> answer;
    @FXML
    private JFXButton btn_clear1;
    @FXML
    private JFXButton btn_show1;
    @FXML
    private JFXButton btn_add1;
    @FXML
    private JFXButton btn_delete;
    @FXML
    private JFXButton btn_update1;
    @FXML
    private JFXTextField question_filter;
    @FXML
    private JFXTextField id_questiontxt;
    @FXML
    private JFXTextField correct_questiontxt;
    @FXML
    private JFXTextArea question_questiontxt;
    @FXML
    private JFXTextField answer_questiontxt;

    ObservableList<question> list2 = FXCollections.observableArrayList();

    @FXML
    public void question_table(ActionEvent event) throws Exception {

        database dq = new database();

        String question = question_questiontxt.getText();
        String correct = correct_questiontxt.getText();
        String answer = answer_questiontxt.getText();
        String coursename = question_filter.getText();

        String arr[] = {question, correct, answer};
        String arr2[] = {"please enter question,", "please enter correct,", "please enter answer"};
        String message = "";
        Vector vv = dq.question_validation();

        //question_insert
        String filter = question_filter.getText();

        if (event.getSource() == btn_add1) {
            if (question_filter.getText().equals("")) {

//            JOptionPane.showMessageDialog(null, "plz select the course first");
                new dialogs.dialog(Alert.AlertType.NONE, "", "plz select the course first");
            } else if (vv.contains(question)) {
//                JOptionPane.showMessageDialog(null, "question exit before");
                new dialogs.dialog(Alert.AlertType.NONE, "", "question exit before");

            } else if (question.equals("") || correct.equals("") || answer.equals("")) {
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("")) {
                        message += arr2[i];
                    }
                }
                new dialogs.dialog(Alert.AlertType.NONE, "", message);

            } else if (!answer_questiontxt.getText().contains("$")) {
//                JOptionPane.showMessageDialog(null, "plz u should put '$' between answers ");
                new dialogs.dialog(Alert.AlertType.NONE, "", "plz u should put '$' between answers ");

            } else {
                dq.insert_question(question, correct, answer, coursename);
                // System.out.println("+++++++");    
                ////Refresh table***************
                for (int i = list2.size() - 1; i >= 0; i--) {
                    list2.remove(i);
                }
                List<question> ql = dq.select_question(filter);
                for (question qi : ql) {
                    int id = qi.getId();
                    String quest = qi.getQuestion();
                    String corr = qi.getCorrect();
                    String ans = qi.getAnswer();

                    list2.add(new question(id, quest, corr, ans));
                }
                question_table.setItems(list2);
                //System.out.println(list2.toString());
//                JOptionPane.showMessageDialog(null, "added successfuly");
                new dialogs.dialog(Alert.AlertType.NONE, "", "added successfuly");

                ////////////remove from table*************
            }
        } else if (event.getSource() == btn_delete) {

            try {
                int id = 0;

                if (!id_questiontxt.getText().equals("")) {
                    id = Integer.parseInt(id_questiontxt.getText());
                }

                String ques_id = id_questiontxt.getText();

                if (ques_id.equals("")) {
//                    JOptionPane.showMessageDialog(null, "please select question you want to delete");
                    new dialogs.dialog(Alert.AlertType.NONE, "", "please select question you want to delete");

                } else {
                    dq.delete_question(id);

                    ////Refresh table***************
                    for (int i = list2.size() - 1; i >= 0; i--) {
                        list2.remove(i);
                    }
                    List<question> ql = dq.select_question(filter);
                    for (question qi : ql) {
                        int que_id = qi.getId();
                        String quest = qi.getQuestion();
                        String corr = qi.getCorrect();
                        String ans = qi.getAnswer();

                        list2.add(new question(que_id, quest, corr, ans));
                    }
                    question_table.setItems(list2);
//                    JOptionPane.showMessageDialog(null, "deleted successfuly");
                    new dialogs.dialog(Alert.AlertType.NONE, "", "deleted successfuly");

                }
            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(null, ex);
                new dialogs.dialog(Alert.AlertType.NONE, "", ex.getMessage());

            }

        } else if (event.getSource() == btn_update1) {
            int id = 0;
            int ii = 0;

            if (!id_questiontxt.getText().equals("")) {
                Integer.parseInt(id_questiontxt.getText());
                String idtxt = id_questiontxt.getText();
                if (idtxt.equals("")) {
                    JOptionPane.showMessageDialog(null, "please select question first");

                    new dialogs.dialog(Alert.AlertType.NONE, "", "please select question first");

                }
            }
            String questio = question_questiontxt.getText();
            String co = correct_questiontxt.getText();
            String an = answer_questiontxt.getText();
            String course = question_filter.getText();
            String valid_update[] = {question, correct, answer};
            String valid_message[] = {"please enter question,", "please enter correct,", "please enter answer"};
            String message2 = "";

            if (question_filter.getText().equals("")) {
//                JOptionPane.showMessageDialog(null, "please select the course u want to update");

                new dialogs.dialog(Alert.AlertType.NONE, "", "please select the course u want to update");

            } else if (questio.equals("") || co.equals("") || an.equals("")) {
                for (int i = 0; i < valid_update.length; i++) {
                    if (valid_update[i].equals("")) {
                        message2 += valid_message[i];
                    }
                }
//                JOptionPane.showMessageDialog(null, message2);
                new dialogs.dialog(Alert.AlertType.NONE, "", message2);

            } else {
                dq.update_question(id, question, correct, answer, course);
                new dialogs.dialog(Alert.AlertType.NONE, "",  "updated succefully");

//                JOptionPane.showMessageDialog(null, "updated succefully");
            }
            ////Refresh table***************
            for (int i = list2.size() - 1; i >= 0; i--) {
                list2.remove(i);
            }
            List<question> ql = dq.select_question(filter);
            for (question qi : ql) {
                int i = qi.getId();
                String quest = qi.getQuestion();
                String corr = qi.getCorrect();
                String ans = qi.getAnswer();

                list2.add(new question(i, quest, corr, ans));
            }
            question_table.setItems(list2);

        } else if (event.getSource() == btn_show1) {
            dq.show_question(filter);
            for (int i = list2.size() - 1; i >= 0; i--) {
                list2.remove(i);
            }
            List<question> ql = dq.select_question(filter);
            for (question qi : ql) {
                int i = qi.getId();
                String quest = qi.getQuestion();
                String corr = qi.getCorrect();
                String ans = qi.getAnswer();

                list2.add(new question(i, quest, corr, ans));
            }
            question_table.setItems(list2);

        }

    }

    @FXML
    public void action_clear2(ActionEvent e) {
        if (e.getSource() == btn_clear1) {
            id_questiontxt.setText("");
            question_questiontxt.setText("");
            correct_questiontxt.setText("");
            answer_questiontxt.setText("");

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        database dq = null;
        try {
            dq = new database();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // ObservableList<question> listquestion = (ObservableList<question>) dq.select_course();
        ObservableList<String> liss = (ObservableList<String>) dq.select_course();
        TextFields.bindAutoCompletion(question_filter, liss);
        id_questiontxt.setDisable(true);

        ///initialize question_table
//        List<question> qi=dq.select_question();
//        
//        for(question ob :qi){
//            int i =ob.getId();
//            String q=ob.getQuestion();
//            String c = ob.getCorrect();
//            String a = ob.getAnswer();
//            
//            list2.add(new question(i,q,c,a));
//            
//        }
//     
        qid.setCellValueFactory(new PropertyValueFactory<question, Integer>("id"));
        question.setCellValueFactory(new PropertyValueFactory<question, String>("question"));
        correct.setCellValueFactory(new PropertyValueFactory<question, String>("correct"));
        answer.setCellValueFactory(new PropertyValueFactory<question, String>("answer"));

        //question_table.getItems().addAll(list2);
        // link_table.setOnMouseClicked(new EventHandler<MouseEvent>()
        question_table.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (!question_table.getSelectionModel().isEmpty()) {

                    question q = (question) question_table.getSelectionModel().getSelectedItem();
                    id_questiontxt.setText(String.valueOf(q.getId()));
                    question_questiontxt.setText(q.getQuestion());
                    correct_questiontxt.setText(q.getCorrect());
                    answer_questiontxt.setText(q.getAnswer());
                }
            }
        });

    }

}

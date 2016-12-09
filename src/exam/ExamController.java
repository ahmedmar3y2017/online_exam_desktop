/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam;

import com.jfoenix.controls.JFXRadioButton;
import database.database;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
//import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import login.mycontroller;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import pane.start;

public class ExamController implements Initializable {
//FXML variables

    @FXML
    private Text question;
    @FXML
    private VBox vbox;
    @FXML
    private JFXRadioButton[] choices_arr = new JFXRadioButton[4];
    @FXML
    private Label qnum;
    @FXML
    private ProgressIndicator timeCalc;
    @FXML
    private Button nextBtn, backBtn, submitBtn;
    @FXML
    private ProgressBar progress;

    //System variables
    private static Connection con = database.getConnection();
    private static Statement stat = null;
    static int id = getStartController.id; //seleted id of course
    static int total = getAll(); //Handle total number of questions
    static int currq = 0; //Current question = currq+1
    static String answer = "";
    static String userans = ""; //userAnswers
    static int degree = 0;
    double time = 0.0;
    static double totalTime = getStartController.totalTime;
    static int F_ID = getStartController.firstID;
    static Integer[] arr = new Integer[10];
    Integer[][] choices = new Integer[10][5]; //Random array for random display for answers
    static String[] user = new String[10];    //Total answers of the ueser
    static String[] correctAns = new String[10]; //total correct answers
    static String[] randAnswers = new String[4];

    static StringTokenizer st;
    Thread t;
    ExamController exam;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(id);
        System.out.println(total);
        /////////////////////////////
        Integer[] testarr = randQ(total);

        for (int i = 0; i < 10; i++) {
            arr[i] = testarr[i];
        }
        ////////////////////////////
        ///////Enter random answers for questions/////////
        for (int i = 0; i < 10; i++) {
            choices[i][0] = arr[i];
            //make shufle on array
            Integer[] a = randQ(4);
            System.arraycopy(a, 0, choices[i], 1, 4);
        }

        /*
         for (int i = 0; i < 10; i++) {
         System.out.print(arr[i] + " ");
         }
         System.out.println("------------------------------------");
         for (int i = 0; i < 10; i++) {
         for (int j = 0; j < 5; j++) {
         System.out.print(choices[i][j] + " ");
         }
         System.out.println("");
         }
         */
        ////////////////////////////////////
        submitBtn.setDisable(true);

        t = new Thread() {
            @Override
            public void run() {
                while (time < 1) {

                    timeCalc.setProgress(time);
                    try {

                        sleep(1000);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                    time += (.016 / total); //totalTime
                    if (time >= 1) {
                        try {
                            nextBtn.setDisable(true);
                            backBtn.setDisable(true);
//                        JOptionPane.showMessageDialog(null, "Finish\nYour degree is : " + degree);

                            new dialogs.dialog(Alert.AlertType.WARNING, "Time Finish ... ", "Finish\nYour degree is : " + degree);
//                        database data = new database();
//
//                        new start("user", data.get_user_name(mycontroller.getEemail()), mycontroller.getEemail());

                            if (degree * 10 > getStartController.min_grade) {

                                try {
                                    String email = mycontroller.getEemail();

                                    database data = new database();
                                    int user_id = data.select_user_id(email);

                                    data.insert_certified(user_id, id, new Date(new java.util.Date().getTime()), degree, 1);

                                    new dialogs.dialog(Alert.AlertType.INFORMATION, "Complete", "Finish\nYour degree is : " + degree);
                                } catch (SQLException ex) {
                                    Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                new dialogs.dialog(Alert.AlertType.ERROR, "Failed", "Your Exam is : " + degree + "\nRestart Exam ... ");

                            }
                            database data = new database();

                            new start("user", data.get_user_name(mycontroller.getEemail()), mycontroller.getEemail());
//                        System.exit(0);
                        } catch (SQLException ex) {
                            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }

        };
        t.start();
        //////////////////////////////////////////
        qnum.setText((currq + 1) + "- ");
        try {
            String q = getQuestion(currq);
            question.setText(q + " ?");

            // answer = getRightAnswer(currq);
            stat = con.createStatement();
            // ResultSet result1 = stat.executeQuery("select * from question where id=" + ((arr[qnum])+F_ID) + " and course_id=" + id);
            ResultSet result = stat.executeQuery("select * from question where id=" + ((arr[currq]) + F_ID) + " and course_id=" + id);
            ToggleGroup group = new ToggleGroup();

            vbox.getChildren().clear();

            /*  while (result.next()) {
             for (int i = 0; i < 4; i++) {
             choices_arr[i] = new JFXRadioButton(result.getString(choices[currq][i + 1] + 3));
             choices_arr[i].setToggleGroup(group);
             vbox.getChildren().add(choices_arr[i]);
             choices_arr[i].setOnAction((e) -> {
             try {
             JFXRadioButton radio = (JFXRadioButton) e.getSource();
             userans = radio.getText();
             } catch (Exception ex) {
             ex.printStackTrace();
             }
             });

             }
             }*/
            while (result.next()) {
                int n = 0;
                randAnswers[n++] = result.getString("correct");
                st = new StringTokenizer(result.getString("answers"), "&");
                while (st.hasMoreTokens()) {
                    randAnswers[n++] = st.nextToken().trim();
                }

                for (int i = 0; i < 4; i++) {
                    //  choices_arr[i] = new JFXRadioButton(result.getString(choices[currq][i + 1] + 3));
                    choices_arr[i] = new JFXRadioButton(randAnswers[choices[currq][i + 1]]);
                    choices_arr[i].setToggleGroup(group);
                    vbox.getChildren().add(choices_arr[i]);

                    choices_arr[i].setOnAction((e) -> {
                        try {
                            JFXRadioButton radio = (JFXRadioButton) e.getSource();
                            // userans = radio.getText();
                            user[currq] = radio.getText();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });

                }
            }
            //Close Connection 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ///////////////////////////////////////////////////////////////////
        progress.setProgress(((currq + 1) / 10.0));
        if (currq == 0) {
            backBtn.setDisable(true);
        } else {
            backBtn.setDisable(false);
        }

    }

    public static int getAll() {
        int n = 0;
        try {
            stat = con.createStatement();
            ResultSet result = stat.executeQuery("select * from question where course_id=" + id);
            while (result.next()) {
                n++;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return n;
    }

    public static String getRightAnswer(int qnum) throws SQLException {
        stat = con.createStatement();
        ResultSet result1 = stat.executeQuery("select correct from question where id=" + ((arr[qnum]) + F_ID) + " and course_id=" + id);
        while (result1.next()) {
            answer = result1.getString("correct");
        }
        return answer;
    }

    public static Integer[] randQ(int n) {

        Integer[] arr = new Integer[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));

        return arr;
    }

    public static String getQuestion(int qnum) throws SQLException {

        String ans = "";
        stat = con.createStatement();
        ResultSet result = stat.executeQuery("select * from question where id=" + (((arr[qnum])) + F_ID) + " and course_id=" + id);
        // System.out.println((int)arr[qnum]+1);
        while (result.next()) {
            ans = result.getString("question");
        }

        return ans;
    }

    /////////////////--Update the View--/////////////////
    public void refresh() {

        qnum.setText((currq + 1) + "- ");
        try {
            String q = getQuestion(currq);
            question.setText(q + " ?");

            // answer = getRightAnswer(currq);
            stat = con.createStatement();
            ResultSet result = stat.executeQuery("select * from question where id=" + ((arr[currq]) + F_ID) + " and course_id=" + id);
            ToggleGroup group = new ToggleGroup();

            vbox.getChildren().clear();

            while (result.next()) {
                int n = 0;
                randAnswers[n++] = result.getString("correct");
                st = new StringTokenizer(result.getString("answers"), "&");
                while (st.hasMoreTokens()) {
                    randAnswers[n++] = st.nextToken().trim();
                }
                for (int i = 0; i < 4; i++) {
                    //choices_arr[i] = new JFXRadioButton(result.getString(choices[currq][i + 1] + 3));
                    choices_arr[i] = new JFXRadioButton(randAnswers[choices[currq][i + 1]]);
                    choices_arr[i].setToggleGroup(group);
                    vbox.getChildren().add(choices_arr[i]);

                    choices_arr[i].setOnAction((e) -> {
                        try {
                            JFXRadioButton radio = (JFXRadioButton) e.getSource();
                            // userans = radio.getText();
                            user[currq] = radio.getText();
                        } catch (Exception ex) {
                            System.out.println(ex.toString());
                        }
                    });

                }
            }
            //Close Connection 
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        ///////////////////////////////////////////////////////////////////
        progress.setProgress(((currq + 1) / 10.0));
        if (currq == 0) {
            backBtn.setDisable(true);
        } else {
            backBtn.setDisable(false);
        }
    }

    public void nextClick() {

        try {
            correctAns[currq] = getRightAnswer(currq);
            ///////////////////////////////
            // user[currq] = userans;

            if (currq == 8) {
                //t.stop();
                nextBtn.setDisable(true);
                submitBtn.setDisable(false);
                // JOptionPane.showMessageDialog(null, "Finish\nYour degree is : " + degree);
                // System.exit(0);
            }
            currq++;
            refresh();

            if (user[currq] != null) {
                for (int i = 0; i < 4; i++) {
                    if (choices_arr[i].getText().equals(user[currq])) {
                        choices_arr[i].setSelected(true);
                        return;
                    }

                }
            }
            //   userans = null;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void backClick() {
        currq--;
        refresh();

        for (int i = 0; i < 4; i++) {
            if (choices_arr[i].getText().equals(user[currq])) {
                choices_arr[i].setSelected(true);
                return;
            }

        }

    }

    public void submitClick() {

        try {
            correctAns[currq] = getRightAnswer(currq);

            for (int i = 0; i < 10; i++) {
                System.out.println(correctAns[i] + "/" + user[i]);
            }

            for (int i = 0; i < 10; i++) {
                if (correctAns[i].equals(user[i])) {
                    degree++;
                }
            }
//            JOptionPane.showMessageDialog(null, "Finish\nYour degree is : " + degree);

            int dd = degree * 10;
            System.out.println(getStartController.min_grade);
            if (dd > getStartController.min_grade) {

                String email = mycontroller.getEemail();

                database data = new database();
                int user_id = data.select_user_id(email);

                data.insert_certified(user_id, id, new Date(new java.util.Date().getTime()), degree, 1);

                new dialogs.dialog(Alert.AlertType.INFORMATION, "Complete", "Finish\nYour degree is : " + degree);

                HashMap hash = new HashMap();
                hash.put("name", data.get_user_name(email));
                hash.put("grade", degree);
                hash.put("course_name", getStartController.n);

                try {

                    JasperReport c = JasperCompileManager.compileReport("src\\exam\\report3.jrxml");
                    JasperPrint print = JasperFillManager.fillReport(c, hash, database.getConnection());

                    JasperViewer.viewReport(print, false);
//            JasperPrintManager.printReport(print, false);

                    /*      String fileName= "src\\report\\myReport.jrxml";
                     File file = new File(fileName);
                     JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file);
                     JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash,connection());
                     JasperViewer.viewReport(jasperPrint,false);
                     */
                } catch (JRException e) {
                    e.printStackTrace();
                }

            } else {
                new dialogs.dialog(Alert.AlertType.ERROR, "Failed", "Your Exam is : " + degree + "\nRestart Exam ... ");
            }
            database data = new database();

            new start("user", data.get_user_name(mycontroller.getEemail()), mycontroller.getEemail());

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

}

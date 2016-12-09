/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import CourseAdmin.course;
import LinkAdmin.links;
import QuestionAdmin.question;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author programmer
 */
public class database {

    Connection conn;
    Statement statement;
    ResultSet resultSet;
    PreparedStatement p;
    String username = "root";
    String password = "root";
    String url = "jdbc:mysql://localhost:3306/online_exam";
    boolean f = false;

    public database() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        conn = (Connection) DriverManager.getConnection(url, username, password);

        f = true;
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
//        conn = (Connection) ;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_exam", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return conn;
    }

    public boolean re() {

        return f;

    }

    //------------------------------------------ login methods  ----------------------------------------
    //--------------------------- sign up ----------------------------------------
    public void insert_user(String username, String email, String password, FileInputStream f, long lenght) throws SQLException {

        p = conn.prepareStatement("insert into `user` (`name`,`email`,`pass`,`image`) values (?,?,?,?)");

        p.setString(1, username);
        p.setString(2, email);
        p.setString(3, password);
        p.setBinaryStream(4, f, (int) lenght);

        p.execute();

    }

    public void insert_admin(String username, String email, String password) throws SQLException {

        p = conn.prepareStatement("insert into `admin` (`name`,`email`,`pass`) values (?,?,?)");

        p.setString(1, username);
        p.setString(2, email);
        p.setString(3, password);

        p.execute();

    }

    public boolean check_user(String email) throws SQLException {
        boolean f = false;
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select `email` from `user` where `email`='" + email + "' ");
        if (resultSet.next()) {
            f = true;

        }

        return f;

    }

//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        database d=new database();
//        System.out.println(d.re());
//        
//    }
    public boolean check_user_login(String email1, String pass1) throws SQLException {

        boolean f = false;
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select `email` , `pass` from `user` where `email`='" + email1 + "' and `pass`='" + pass1 + "' ");
        if (resultSet.next()) {
            f = true;

        }

        return f;

    }

    public String get_name(String email1) throws SQLException {

        String name = "";
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select `name` from `user` where `email`='" + email1 + "'");
        if (resultSet.next()) {

            name = resultSet.getString("name");
        }

        return name;

    }

    public String get_admin_name(String email1) throws SQLException {

        String name = "";
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select `name` from `admin` where `email`='" + email1 + "'");
        if (resultSet.next()) {

            name = resultSet.getString("name");
        }

        return name;

    }

    public List<String> course_re() throws SQLException {
        ArrayList l = new ArrayList();
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select name from `course` ");

        while (resultSet.next()) {
            String nname = resultSet.getString("name");

            l.add(nname);

        }
        return l;
    }

    public boolean exist(String text) throws SQLException {
        boolean ff = false;
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select * from `course` where name='" + text + "'");

        if (resultSet.next()) {

            ff = true;

        }
        return ff;
    }

    public ResultSet get_all_course(String text) throws SQLException {

        statement = conn.createStatement();
        resultSet = statement.executeQuery("select * from `course` where name='" + text + "'");

        return resultSet;
    }

    public List<String> get_links(int course_id) throws SQLException {
        List<String> list = new ArrayList<>();
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select * from `links` where course_id='" + course_id + "'");

        while (resultSet.next()) {

            list.add(resultSet.getString("links"));
        }

        return list;
    }

    public List<String> get_links_name(int course_id) throws SQLException {
        List<String> list = new ArrayList<>();
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select * from `links` where course_id='" + course_id + "'");

        while (resultSet.next()) {

            list.add(resultSet.getString("link_name"));
        }

        return list;
    }

    //--------------------------- retrieve image for user  ---------------------------
    public byte[] retrieve_image(String email) throws SQLException {
        byte barr[] = null;
        PreparedStatement ps = conn.prepareStatement("select * from user where email=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {//now on 1st row  

            Blob b = rs.getBlob("image");//2 means 2nd column data  

            barr = b.getBytes(1, (int) b.length());  //1 means first image  

        }
        return barr;

    }

    //------------------------------------- course admin -------------------
    public List<course> course_selectall() {

        ArrayList<course> list = new ArrayList<>();
        try {
            p = conn.prepareStatement("select id,name,time,final,min_grade,description from course");
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String time = resultSet.getString("time");
                String finall = resultSet.getString("final");
                String min_grade = resultSet.getString("min_grade");
                String disc = resultSet.getString("description");

                course u = new course(id, name, time, finall, min_grade, disc);
                list.add(u);

            }

            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    public List<String> select_admin() {
        ObservableList lis = FXCollections.observableArrayList();
        try {
            p = conn.prepareStatement("select name from admin ");
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                String ad = resultSet.getString("name");
                lis.add(ad);
            }
            return lis;

        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, ee);
            return null;
        }

    }

    public List<String> select_courses_name() {
        ObservableList lis = FXCollections.observableArrayList();
        try {
            p = conn.prepareStatement("select name from course ");
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                String ad = resultSet.getString("name");
                lis.add(ad);
            }
            return lis;

        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, ee);
            return null;
        }

    }

    public void insert_course(String name, ComboBox<String> time, String finall, String min_grade, String discription, ComboBox<String> admin_id) {
        try {

            p = conn.prepareStatement("insert into course (name,time,final,min_grade,description,admin_id)values(?,?,?,?,?,(select id from admin where name=?))");
            //  prm.setInt(1, id);
            p.setString(1, name);
            p.setString(2, time.getSelectionModel().getSelectedItem());
            p.setString(3, finall);
            p.setString(4, min_grade);
            p.setString(5, discription);
            p.setString(6, admin_id.getSelectionModel().getSelectedItem());

            p.execute();

        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }

    public void delete(int id) throws SQLException, ClassNotFoundException {

        p = conn.prepareStatement("delete from course where id=?");

        p.setInt(1, id);

        p.executeUpdate();

    }

    public void update(int id, ComboBox<String> time, String finall, String min_grade, String discription, ComboBox<String> admin_id) throws SQLException, ClassNotFoundException {

        try {
            p = conn.prepareStatement("update `course` set time=?,final=?,min_grade=?,description=?,admin_id=(select id from admin where name=?) where id=?");
            //  prm.setString(1, name);
            p.setString(1, time.getSelectionModel().getSelectedItem());
            p.setString(2, finall);
            p.setString(3, min_grade);
            p.setString(4, discription);
            p.setString(5, admin_id.getSelectionModel().getSelectedItem());
            p.setInt(6, id);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        p.executeUpdate();

    }

    public List<course> select_admin_courses(ComboBox<String> cc) {

        ArrayList<course> list = new ArrayList<>();
        try {
            p = conn.prepareStatement("select id,name ,time,final,min_grade,description from course where admin_id =(select id from admin where admin.name = ? )");
            p.setString(1, cc.getSelectionModel().getSelectedItem());

            resultSet = p.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String time = resultSet.getString("time");
                String finall = resultSet.getString("final");
                String min_grade = resultSet.getString("min_grade");
                String discription = resultSet.getString("description");

                course ud = new course(id, name, time, finall, min_grade, discription);
                list.add(ud);

            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Vector<String> validation() {

        ObservableList lis = FXCollections.observableArrayList();
        try {
            p = conn.prepareStatement("select name from course ");

            Vector v = new Vector();
            resultSet = p.executeQuery();
            while (resultSet.next()) {

                v.add(resultSet.getString("name"));

            }
            return v;

        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, ee);
            return null;
        }

    }

    //------------------------------------------- link admin -------------------
    public List<String> lselect_courses_name() {
        ObservableList lis = FXCollections.observableArrayList();
        try {
            p = conn.prepareStatement("select name from course ");
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                String ad = resultSet.getString("name");
                lis.add(ad);
            }
            return lis;

        } catch (Exception ee) {
            JOptionPane.showMessageDialog(null, ee);
            return null;
        }

    }

    public List<links> selectall_links(ComboBox<String> cc) {

        ArrayList<links> list = new ArrayList<>();
        try {
            p = conn.prepareStatement("select course.id,course.name,links.links,links.link_name FROM course inner JOIN links on name=(?)and course.id=links.course_id");
            p.setString(1, cc.getSelectionModel().getSelectedItem());
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String links = resultSet.getString("links");
                String linkname = resultSet.getString("link_name");

                links ud = new links(id, name, links, linkname);
                list.add(ud);

            }

            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    public List<links> selectall() {

        ArrayList<links> list = new ArrayList<>();
        try {
            p = conn.prepareStatement("select course.id,course.name,links.links,links.link_name FROM course inner JOIN links on course.id=links.course_id");
            // prm.setString(1,cc.getSelectionModel().getSelectedItem());
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String links = resultSet.getString("links");
                String linkname = resultSet.getString("link_name");

                links ud = new links(id, name, links, linkname);
                list.add(ud);

            }

            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        datalinks d =new datalinks();
//     //   d.delete("javaoop","a3a");
//       // d.insert_links("koko", "a6a", "aa");
//                System.out.println("+++++");
//     //System.out.println(d.selectall_links().size());
//    }

    public void insert_links(ComboBox<String> uname, String links, String linkname) {

        try {

            p = conn.prepareStatement("insert into links values ((select id from course where name=? ),?,?)");

            p.setString(1, uname.getSelectionModel().getSelectedItem());

            p.setString(2, links);
            p.setString(3, linkname);

            p.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public List<links> select_course_links(ComboBox<String> course) {

        ArrayList<links> list = new ArrayList<>();
        try {
            p = conn.prepareStatement("select course.id,course.name,links.links,links.link_name FROM course inner JOIN links on course.id=links.course_id and course.name=?");
            p.setString(1, course.getSelectionModel().getSelectedItem());
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String links = resultSet.getString("links");
                String linkname = resultSet.getString("link_name");

                links ud = new links(id, name, links, linkname);
                list.add(ud);

            }

            return list;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public void delete(ComboBox<String> combo, String links) throws SQLException, ClassNotFoundException {
        p = conn.prepareStatement("delete from links where course_id=(select id from course where name= ?) and links= ?");
        p.setString(1, combo.getSelectionModel().getSelectedItem());
        p.setString(2, links);

        p.executeUpdate();

    }
//--------------------------------------- question admin ------------------------------

    public List<String> select_course_name() {
        ObservableList liss = FXCollections.observableArrayList();

        try {
            p = conn.prepareStatement("select name from course ");
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                liss.add(name);
            }
            return liss;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }

    }

    public List<question> select_question(String txtfitlr) {

        ObservableList<question> list2 = FXCollections.observableArrayList();

        try {
            p = conn.prepareStatement("select id,question,correct,answers from question where course_id=(select id from course where name=? ) ");
            p.setString(1, txtfitlr);
            resultSet = p.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String question = resultSet.getString("question");
                String correct = resultSet.getString("correct");
                String answer = resultSet.getString("answers");
                question q = new question(id, question, correct, answer);
                list2.add(q);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
        return list2;

    }

    public void insert_question(String question, String correct, String answers, String coursename) {
        ArrayList<question> list2 = new ArrayList<>();

        try {
            p = conn.prepareStatement("insert into question (question,correct,answers,course_id)values(?,?,?,(select id from course where name=?))");
            p.setString(1, question);
            p.setString(2, correct);
            p.setString(3, answers);
            p.setString(4, coursename);

            p.execute();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);
        }
    }

//    
//    
//    public  void main(String[] args) throws SQLException, ClassNotFoundException {
//     dataquestion fd = new dataquestion();
//     List<question> tt= fd.select_question("koko");
////        f.insert_question("fff","fff", "dgd", "oll")
//     System.out.println(tt);
//          System.out.println("+++++++++");
//
////        
//        
//    }
    public void delete_question(int id) {

        try {
            p = conn.prepareStatement("delete from question where id=?");
            p.setInt(1, id);

            p.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public List<String> select_course() {
        ObservableList course_list = FXCollections.observableArrayList();

        try {
            p = conn.prepareStatement("select name from course");

            resultSet = p.executeQuery();

            while (resultSet.next()) {
                String co = resultSet.getString("name");
                course_list.add(co);
            }
            return course_list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }

    }

    public int update_question(int id, String question, String correct, String answers, String course_id) {
        int i = 0;
        try {

            p = conn.prepareStatement("update `question` set question=?,correct=?,answers=?,course_id=(select id from course where name=?) where id =? ");

            p.setString(1, question);
            p.setString(2, correct);
            p.setString(3, answers);
            p.setString(4, course_id);
            p.setInt(5, id);

            i = p.executeUpdate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return i;
    }

    ///////////////show question///////////////
    public List<question> show_question(String txt_filter) {

        ObservableList course_list = FXCollections.observableArrayList();
        try {
            p = conn.prepareStatement("select question.id,question,correct,answers from question ,course where question.course_id=course.id and name= ? ");
            p.setString(1, txt_filter);

            resultSet = p.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String question = resultSet.getString("question");
                String correct = resultSet.getString("correct");
                String answer = resultSet.getString("answers");
                question q = new question(id, question, correct, answer);
                course_list.add(q);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return course_list;

    }

    public Vector<String> question_validation() {
        ObservableList list = FXCollections.observableArrayList();

        try {
            p = conn.prepareStatement("select question from question ");

            Vector v = new Vector();
            resultSet = p.executeQuery();
            while (resultSet.next()) {
                v.add(resultSet.getString("question"));
            }
            return v;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }

    }

    public boolean check_admin_login(String email1, String pass1) throws SQLException {

        boolean f = false;
        statement = conn.createStatement();
        resultSet = statement.executeQuery("select `email` , `pass` from `admin` where `email`='" + email1 + "' and `pass`='" + pass1 + "' ");
        if (resultSet.next()) {
            f = true;

        }

        return f;

    }
    //---------------------------------- grade -------------------------

    public ResultSet select_certification_info(int id) throws SQLException {

        p = conn.prepareStatement("select certification.date , certification.grade , course.name from `user` inner join certification inner join course on certification.user_id=" + id + " and course.id=certification.course_id and user.id=" + id + "");
//        p.setInt(1, id);
        System.out.println("++++++++++++++++++++++++++++++++");
        resultSet = p.executeQuery();

        return resultSet;

    }

    public int select_user_id(String yy) throws SQLException {
        int id = 0;
        p = conn.prepareStatement("select id from `user` where email=?");
        p.setString(1, yy);
//        p.setInt(1, id);
        System.out.println("++++++++++++++++++++++++++++++++");
        resultSet = p.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        return id;
    }

    public String get_user_name(String yy) throws SQLException {
        String nname = null;
        p = conn.prepareStatement("select name from `user` where email=?");
        p.setString(1, yy);
//        p.setInt(1, id);
        System.out.println("++++++++++++++++++++++++++++++++");
        resultSet = p.executeQuery();
        while (resultSet.next()) {
            nname = resultSet.getString("name");
        }
        return nname;
    }

    public void insert_certified(int user_id, int id, Date date, int degree, int i) throws SQLException {

        p = conn.prepareStatement("insert into `certification` values (?,?,?,?,?)");

        p.setInt(1, user_id);
        p.setInt(2, id);
        p.setDate(3, date);
        p.setInt(4, degree);
        p.setInt(5, i);

        p.execute();

    }

    public boolean check_certificate(int user_id, int id) throws SQLException {

        p = conn.prepareStatement("select user_id,course_id from `certification` where user_id=? and course_id=?");
        p.setInt(1, user_id);
        p.setInt(2, id);

        resultSet = p.executeQuery();
        if (resultSet.next()) {

            return true;

        }
        return false;
    }

}

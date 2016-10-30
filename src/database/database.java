/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public boolean re() {

        return f;

    }

    //------------------------------------------ login methods  ----------------------------------------
    //--------------------------- sign up ----------------------------------------
    public void insert_user(String username, String email, String password) throws SQLException {

        p = conn.prepareStatement("insert into `user` (`name`,`email`,`pass`) values (?,?,?)");

        p.setString(1, username);
        p.setString(2, email);
        p.setString(3, password);

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
}

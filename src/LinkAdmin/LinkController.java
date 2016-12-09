/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkAdmin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.database;
import java.net.URL;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class LinkController implements Initializable {

    @FXML
    private TableView link_table;
    @FXML
    private TableColumn<links, Integer> cid;
    @FXML
    private TableColumn<links, String> cname;
    @FXML
    private TableColumn<links, String> link;
    @FXML
    private TableColumn<links, String> linkname;

    @FXML
    private JFXButton btn_showlinks;

    @FXML
    private JFXButton btn_addlink;
    @FXML
    private JFXButton btn_deletelink;
    @FXML
    private JFXButton btn_updatelink;
    @FXML
    private JFXButton btn_clearlink;
    @FXML
    private JFXTextField id_linktxt;
//
//    @FXML
//    private JFXComboBox name_course_link;
    @FXML
    private JFXTextField url_coursetxt2;

    @FXML
    private JFXTextField linkname_txt;

    @FXML
    private JFXComboBox courses_compobox;

    ObservableList<links> listlink = FXCollections.observableArrayList();

    @FXML
    public void actioncombo(ActionEvent event) throws SQLException, ClassNotFoundException {

        database dl = new database();

        //  if (link_table.getItems().removeAll(listlink)) {
        for (int i = listlink.size() - 1; i >= 0; i--) {
            listlink.remove(i);
        }
// table.getItems().addAll(list);
        List<links> ll = dl.selectall_links(courses_compobox);
        System.out.println(ll.size());
        listlink.addAll(ll);

        link_table.setItems(listlink);
//            System.out.println(ll.size());

        //}
    }

    @FXML
    void action_link_table(ActionEvent event) throws SQLException, ClassNotFoundException {
        //insert links
        database dl = new database();
        String links = url_coursetxt2.getText();
        String linkname = linkname_txt.getText();
        // String name=name_course_link.getSelectionModel().getSelectedItem().toString();
        String txt[] = {links, linkname};
        String arr2[] = {"please enter url ,", "  linkname reqierd "};
        String message = " ";
        if (event.getSource() == btn_addlink) {
            if (courses_compobox.getSelectionModel().getSelectedItem() == null) {
                new dialogs.dialog(Alert.AlertType.NONE, "",  "please select course first ");

//                JOptionPane.showMessageDialog(null, "please select course first ");
            } else if (links.equals("") || linkname.equals("") || courses_compobox.getSelectionModel().getSelectedItem() == null) {
                for (int i = 0; i < txt.length; i++) {
                    if (txt[i].equals("")) {
                        message += arr2[i];
                    }
                    if (courses_compobox.getSelectionModel().getSelectedItem() == null) {

                        message += " select course name";
                        break;
                    }

                }
                JOptionPane.showMessageDialog(null, message);
            } else {

                dl.insert_links(courses_compobox, links, linkname);
                System.out.println("===============================");
                //  if (link_table.getItems().removeAll(listlink)) {
                for (int i = listlink.size() - 1; i >= 0; i--) {
                    listlink.remove(i);
                }
//select all item from database and set it in list
                List<links> lll = dl.select_course_links(courses_compobox);
                for (links ui : lll) {
                    int r = ui.getId();
                    String t = ui.getName();
                    String o = ui.getLink();
                    String s = ui.getLinkname();

                    listlink.add(new links(r, t, o, s));
                }
                System.out.println(listlink.toString());
//add lst to table
                link_table.setItems(listlink);

            //}
            }
        } else if (event.getSource() == btn_deletelink) {

            String urllink = url_coursetxt2.getText();
            System.out.println("+++++++++");
            dl.delete(courses_compobox, urllink);
            System.out.println("----------------");
            //  if (link_table.getItems().removeAll(listlink)) {
            for (int i = listlink.size() - 1; i >= 0; i--) {
                listlink.remove(i);
            }
//select all item from database and set it in list
            List<links> lll = dl.select_course_links(courses_compobox);
            for (links ui : lll) {
                int r = ui.getId();
                String t = ui.getName();
                String o = ui.getLink();
                String s = ui.getLinkname();

                listlink.add(new links(r, t, o, s));
            }
            System.out.println(listlink.toString());
//add lst to table
            link_table.setItems(listlink);

            //}
        } else if (event.getSource() == btn_clearlink) {

            id_linktxt.setText("");
            courses_compobox.setValue("");
            url_coursetxt2.setText("");
            linkname_txt.setText("");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        database dl = null;
        try {
            dl = new database();
        } catch (SQLException ex) {
            Logger.getLogger(LinkController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LinkController.class.getName()).log(Level.SEVERE, null, ex);
        }

        link_table.setEditable(true);
        id_linktxt.setDisable(true);
        try {
            initatialize_combobox();
        } catch (SQLException ex) {
            Logger.getLogger(LinkController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LinkController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //intialize link table
        List<links> lii = dl.selectall();
        for (links ob : lii) {
            int id = ob.getId();
            String uu = ob.getName();
            String pp = ob.getLink();
            String t = ob.getLinkname();

            listlink.add(new links(id, uu, pp, t));

        }

        cid.setCellValueFactory(new PropertyValueFactory<links, Integer>("id"));

        cname.setCellValueFactory(new PropertyValueFactory<links, String>("name"));

        link.setCellValueFactory(new PropertyValueFactory<links, String>("link"));
        linkname.setCellValueFactory(new PropertyValueFactory<links, String>("linkname"));

        link_table.getItems().addAll(listlink);
        // set raw in text
        link_table.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (!link_table.getSelectionModel().isEmpty()) {

                    links l = (links) link_table.getSelectionModel().getSelectedItem();

                    id_linktxt.setText(String.valueOf(l.getId()));
                    courses_compobox.setValue(l.getName());
                    url_coursetxt2.setText(l.getLink());
                    linkname_txt.setText(l.getLinkname());
                }
            }

        });

    }

    public void initatialize_combobox() throws SQLException, ClassNotFoundException {

        database dl = new database();
        ObservableList<String> list2 = (ObservableList<String>) dl.lselect_courses_name();
// //intializ combo box   
        courses_compobox.setItems(list2);
        courses_compobox.setItems(list2);

    }

}

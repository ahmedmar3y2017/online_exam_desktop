/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pane;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import login.mycontroller;
//import static pane.Pane.s;

/**
 *
 * @author programmer
 */
public class start {

    public static Stage s = new Stage();
//    public static String eee = "";

    public start() throws IOException {
        Stage s = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml.fxml"));
        Region contentRootRegion = (Region) loader.load();

        //Set a default "standard" or "100%" resolution
        double origW = 960;
        double origH = 540;

        //If the Region containing the GUI does not already have a preferred width and height, set it.
        //But, if it does, we can use that setting as the "standard" resolution.
        if (contentRootRegion.getPrefWidth() == Region.USE_COMPUTED_SIZE) {
            contentRootRegion.setPrefWidth(origW);
        } else {
            origW = contentRootRegion.getPrefWidth();
        }

        if (contentRootRegion.getPrefHeight() == Region.USE_COMPUTED_SIZE) {
            contentRootRegion.setPrefHeight(origH);
        } else {
            origH = contentRootRegion.getPrefHeight();
        }

        //Wrap the resizable content in a non-resizable container (Group)
        Group group = new Group(contentRootRegion);
        //Place the Group in a StackPane, which will keep it centered
        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(group);

        s.setTitle("Home");
        //Create the scene initally at the "100%" size
        Scene scene = new Scene(rootPane, origW, origH);
        scene.getStylesheets().add("pane/style.css");
        //Bind the scene's width and height to the scaling parameters on the group
        group.scaleXProperty().bind(scene.widthProperty().divide(origW));
        group.scaleYProperty().bind(scene.heightProperty().divide(origH));
        //Set the scene to the window (stage) and show it
        s.setScene(scene);
        s.show();

    }

    public start(String type, String name, String email) throws IOException {
        FxmlController.name = name;
        FxmlController.email = email;
//        eee = email;
//        Stage s = new Stage();
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml.fxml"));
        FXMLLoader load = new FXMLLoader();
        Parent root = load.load(getClass().getResource("fxml.fxml").openStream());
        FxmlController user = (FxmlController) load.getController();

        if (type.equals("user")) {
            user.user_privilage();

            System.out.println("Done1++++++++++++++++++++++++++++++++++++++");

        } else if (type.equals("admin")) {
            user.admin_privilage();
        }

//        Region contentRootRegion = (Region) loader.load();
//
//        //Set a default "standard" or "100%" resolution
//        double origW = 960;
//        double origH = 540;
//
//        //If the Region containing the GUI does not already have a preferred width and height, set it.
//        //But, if it does, we can use that setting as the "standard" resolution.
//        if (contentRootRegion.getPrefWidth() == Region.USE_COMPUTED_SIZE) {
//            contentRootRegion.setPrefWidth(origW);
//        } else {
//            origW = contentRootRegion.getPrefWidth();
//        }
//
//        if (contentRootRegion.getPrefHeight() == Region.USE_COMPUTED_SIZE) {
//            contentRootRegion.setPrefHeight(origH);
//        } else {
//            origH = contentRootRegion.getPrefHeight();
//        }
//
//        //Wrap the resizable content in a non-resizable container (Group)
//        Group group = new Group(contentRootRegion);
//        //Place the Group in a StackPane, which will keep it centered
//        StackPane rootPane = new StackPane();
//        rootPane.getChildren().add(group);
//
//        s.setTitle("Home");
//        //Create the scene initally at the "100%" size
//        Scene scene = new Scene(rootPane, origW, origH);
//        scene.getStylesheets().add("pane/style.css");
//        //Bind the scene's width and height to the scaling parameters on the group
//        group.scaleXProperty().bind(scene.widthProperty().divide(origW));
//        group.scaleYProperty().bind(scene.heightProperty().divide(origH));
//        //Set the scene to the window (stage) and show it
        Scene scene = new Scene(root);
        scene.getStylesheets().add("pane/style.css");

        s.setTitle("Welcome");
        s.setResizable(false);
        s.setScene(scene);
        s.show();

    }

}

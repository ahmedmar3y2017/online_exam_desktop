/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialogs;

import javafx.scene.control.Alert;

/**
 *
 * @author programmer
 */
public class dialog {

    public dialog(Alert.AlertType type, String title, String content) {

        Alert dialog = new Alert(type);
        dialog.setResizable(false);

        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);
        dialog.showAndWait();
    }
}

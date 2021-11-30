package main.util;

import javafx.scene.control.Alert;

public class QuickAlert {

    public static void error(String poruka) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(poruka);
        alert.showAndWait();
    }

    public static void success(String poruka) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspjeh");
        alert.setHeaderText("Uspjeh");
        alert.setContentText(poruka);
        alert.showAndWait();
    }
}

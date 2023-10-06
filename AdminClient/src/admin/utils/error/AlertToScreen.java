package admin.utils.error;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AlertToScreen {


    public static void showErrorDialog(Exception exception) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("An error occurred:");
        alert.setContentText(exception.getMessage());

        // Create expandable Exception content
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();


        // Set expandable Exception into the dialog pane

        alert.showAndWait();
    }
    public static void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("An error occurred:");
        alert.setContentText(errorMessage);

        // Create expandable Exception content
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String exceptionText = sw.toString();


        // Set expandable Exception into the dialog pane

        alert.showAndWait();
    }
}

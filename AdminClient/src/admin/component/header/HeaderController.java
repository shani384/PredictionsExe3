package admin.component.header;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class HeaderController {

    @FXML
    private GridPane gridPaneHeader;
    @FXML
    private Button ButtonNewExec;

    @FXML
    private Button ButtonResults;

    @FXML
    private Button ButtonQueueManage;

    @FXML
    private void onNewExecutionClicked(ActionEvent event) {
        // Handle the "Management" button click here
        System.out.println("Management button clicked");
    }

    @FXML
    private void onResultsClicked(ActionEvent event) {
        // Handle the "Allocations" button click here
        System.out.println("Allocations button clicked");
    }

    @FXML
    private void onQueueManagementClicked(ActionEvent event) {
        // Handle the "Executions History" button click here
        System.out.println("Executions History button clicked");
    }
}
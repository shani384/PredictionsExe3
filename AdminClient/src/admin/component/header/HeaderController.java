package admin.component.header;

import admin.component.mainapp.AppMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class HeaderController {

    @FXML
    private GridPane gridPaneHeader;
    @FXML
    private Button ButtonManagement;

    @FXML
    private Button ButtonAllocations;

    @FXML
    private Button ButtonExecutionHistory;

    private AppMainController appMainController;

    @FXML
    private void onManagementClicked(ActionEvent event) {
        // Handle the "Management" button click here
        System.out.println("Management button clicked");
        appMainController.onManagementChosen();
    }

    @FXML
    private void onAllocationsClicked(ActionEvent event) {
        // Handle the "Allocations" button click here
        System.out.println("Allocations button clicked");
    }

    @FXML
    private void onExecutionHistoryClicked(ActionEvent event) {
        // Handle the "Executions History" button click here
        System.out.println("Executions History button clicked");
    }

    public void setMainController(AppMainController appMainController) {
        this.appMainController = appMainController;
    }
}
package client.component.header;

import client.component.mainapp.AppMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HeaderController {

    @FXML
    private Button ButtonDetails;

    @FXML
    private Button ButtonNewExec;

    @FXML
    private Button ButtonResults;

    @FXML
    private Button ButtonQueueManage;

    @FXML
    public Label nameLabel;
    private AppMainController appMainController;

    @FXML
    private void onDetailsClicked(ActionEvent event) {
        // Handle the "Simulation Details" button click here
        // You can add code to show simulation details or perform other actions
    }

    @FXML
    private void onNewExecutionClicked(ActionEvent event) {
        // Handle the "Request" button click here
        // You can add code to initiate a new execution or perform other actions
    }

    @FXML
    private void onResultsClicked(ActionEvent event) {
        // Handle the "Execution" button click here
        // You can add code to show execution results or perform other actions
    }

    @FXML
    private void onQueueManagementClicked(ActionEvent event) {
        // Handle the "Results" button click here
        // You can add code to manage results or perform other actions
    }

    public void setMainController(AppMainController appController) {
        this.appMainController = appController;
    }


    // You can add additional methods and logic as needed
}

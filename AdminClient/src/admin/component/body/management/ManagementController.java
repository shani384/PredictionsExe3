package admin.component.body.management;

import admin.component.mainapp.AppMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ManagementController {

    private AppMainController appMainController;

    @FXML
    public void initialize() {

    }
    public void onClickLoadFile(ActionEvent event) {
    }

    public void loadDataFromServer() {
        //ask for data from server
    }

    public void setMainController(AppMainController appMainController) {
        this.appMainController = appMainController;
    }
}

package admin.component.body.management;

import admin.component.body.management.threadpool.QueueManagementView;
import admin.component.mainapp.AppMainController;
import admin.utils.inputFields.LabelTextBox;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ManagementController {

    @FXML private VBox queueManagement;

    @FXML private QueueManagementView threadPoolComponentController;
    private AppMainController appMainController;

    private SimpleStringProperty filePath;

    private Stage primaryStage;

    @FXML private Label LabelFilePath;
    private SimpleBooleanProperty isFileSelected;

    public ManagementController(){
        this.filePath = new SimpleStringProperty("");
        isFileSelected = new SimpleBooleanProperty(false);
    }
    @FXML
    public void initialize() {
        LabelFilePath.textProperty().bind(filePath);
        if (threadPoolComponentController != null){
            threadPoolComponentController.updateThreadInfo();
        }
    }

    @FXML
    private void onClickLoadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Simulation XML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null)
            return;
        String absolutePath = selectedFile.getAbsolutePath();
        try {
            if (absolutePath != filePath.getValue()) {
                filePath.set(absolutePath);
                isFileSelected.set(true);
                appMainController.readWorld();
            }
        }
        catch (IllegalArgumentException | IOException e) {
            filePath.set(e.getMessage());
            isFileSelected.set(false);
        }
    }

    public void loadDataFromServer() {
        //ask for data from server
    }

    public void setMainController(AppMainController appMainController) {
        this.appMainController = appMainController;
    }
    public String getFilePath(){
        return filePath.get();
    }

    public void getThreadInfo() {

    }
}

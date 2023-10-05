package client.component.body.simulationDetails.component.action.proximityreplace;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.ProximityDTO;
import DTOManager.impl.actionDTO.ReplaceDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class ProximityReplaceDetailsController {

    @FXML
    public Label LabelActionType;
    @FXML
    public Label LabelKillOrSource;
    @FXML
    public Label LabelCreateOrTarget;
    @FXML
    public Label LabelModeOrDepth;
    @FXML
    public Label KillOrSource;
    @FXML
    public Label ModeOrDepth;
    @FXML
    public Label CreateOrTarget;
    @FXML
    public ListView<String> ListViewActions;
    @FXML
    public GridPane gridPane;
    private SimpleStringProperty actionType;
    private SimpleStringProperty killOrSourceTitle;
    private SimpleStringProperty createOrTargetTitle;
    private SimpleStringProperty modeOrDepthTitle;
    private SimpleStringProperty killOrSourceValue;
    private SimpleStringProperty createOrTargetValue;
    private SimpleStringProperty modeOrDepthValue;
    private ObservableList<String> actions;

    public ProximityReplaceDetailsController() {
        this.actionType = new SimpleStringProperty("");
        this.killOrSourceTitle = new SimpleStringProperty("");
        this.createOrTargetTitle = new SimpleStringProperty("");
        this.modeOrDepthTitle = new SimpleStringProperty("");
        this.killOrSourceValue = new SimpleStringProperty("");
        this.createOrTargetValue = new SimpleStringProperty("");
        this.modeOrDepthValue = new SimpleStringProperty("");
        actions = FXCollections.observableArrayList();
    }
    @FXML
    private void initialize(){
        LabelActionType.textProperty().bind(actionType);
        LabelKillOrSource.textProperty().bind(killOrSourceTitle);
        LabelCreateOrTarget.textProperty().bind(createOrTargetTitle);
        LabelModeOrDepth.textProperty().bind(modeOrDepthTitle);
        KillOrSource.textProperty().bind(killOrSourceValue);
        CreateOrTarget.textProperty().bind(createOrTargetValue);
        ModeOrDepth.textProperty().bind(modeOrDepthValue);
        ListViewActions.setItems(actions);
    }
    public void setProximityReplaceDetails(ActionDTO actionDTO){
        if(actionDTO instanceof ProximityDTO){
            actionType.set(actionDTO.getActionType());
            killOrSourceTitle.set("Source Entity:");
            killOrSourceValue.set(actionDTO.getMainEntity().getName());
            createOrTargetTitle.set("Target Entity:");
            createOrTargetValue.set(((ProximityDTO) actionDTO).getTargetEntity().getName());
            modeOrDepthTitle.set("Depth:");
            modeOrDepthValue.set(((ProximityDTO) actionDTO).getOfExpression());
            for (ActionDTO actionDTO1: ((ProximityDTO) actionDTO).getActions()){
                actions.add(actionDTO1.getActionType());
            }
        }else if(actionDTO instanceof ReplaceDTO){
            actionType.set(actionDTO.getActionType());
            killOrSourceTitle.set("Kill Entity:");
            killOrSourceValue.set(actionDTO.getMainEntity().getName());
            createOrTargetTitle.set("Create Entity:");
            createOrTargetValue.set(((ReplaceDTO)actionDTO).getCreateEntity().getName());
            modeOrDepthTitle.set("Mode:");
            modeOrDepthValue.set(((ReplaceDTO) actionDTO).getMode());
            gridPane.getChildren().remove(ListViewActions);
        }
    }
}

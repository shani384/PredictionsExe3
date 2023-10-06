package client.component.body.simulationDetails.component.action.setkill;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.SetDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SetKillDetailsController {
    @FXML public Label LabelValueTitle;
    @FXML public Label LabelPropertyTitle;
    @FXML public Label LabelProperty;
    @FXML public Label LabelValue;
    @FXML public Label LabelMainEntity;
    @FXML public Label LabelSetKill;

    private SimpleStringProperty valueTitle;
    private SimpleStringProperty propertyTitle;
    private SimpleStringProperty property;
    private SimpleStringProperty value;
    private SimpleStringProperty mainEntity;
    private SimpleStringProperty actionType;
    public SetKillDetailsController(){
        this.valueTitle = new SimpleStringProperty("");
        this.propertyTitle = new SimpleStringProperty("");
        this.property = new SimpleStringProperty("");
        this.value = new SimpleStringProperty("");
        this.mainEntity = new SimpleStringProperty("");
        this.actionType = new SimpleStringProperty("");
    }
    @FXML
    private void initialize(){
        LabelSetKill.textProperty().bind(actionType);
        LabelPropertyTitle.textProperty().bind(propertyTitle);
        LabelMainEntity.textProperty().bind(mainEntity);
        LabelProperty.textProperty().bind(property);
        LabelValueTitle.textProperty().bind(valueTitle);
        LabelValue.textProperty().bind(value);
    }
    public void setSetKillDetails(ActionDTO actionDTO){
        if (actionDTO instanceof SetDTO){
            actionType.set(actionDTO.getActionType());
            propertyTitle.set("Property:");
            property.set(((SetDTO) actionDTO).getProperty());
            valueTitle.set("Value:");
            value.set(((SetDTO) actionDTO).getValue());
            mainEntity.set(actionDTO.getMainEntity().getName());
        }else{
            actionType.set(actionDTO.getActionType());
            propertyTitle.set("");
            property.set("");
            valueTitle.set("");
            value.set((""));
            mainEntity.set(actionDTO.getMainEntity().getName());
        }
    }
}

package client.component.body.simulationDetails.component.action.increasedecrease;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.DecreaseDTO;
import DTOManager.impl.actionDTO.IncreaseDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class IncreaseDecreaseDetailsController {
    @FXML public Label LabelProperty;
    @FXML public Label LabelByExpression;
    @FXML public Label LabelMainEntity;
    @FXML public Label LabelActionType;

    private SimpleStringProperty property;
    private SimpleStringProperty byExpression;
    private SimpleStringProperty mainEntity;
    private SimpleStringProperty actionType;

    public IncreaseDecreaseDetailsController(){
        this.actionType = new SimpleStringProperty("");
        this.mainEntity = new SimpleStringProperty("");
        this.byExpression = new SimpleStringProperty("");
        this.property = new SimpleStringProperty("");
    }
    @FXML
    private void initialize(){
        LabelProperty.textProperty().bind(property);
        LabelActionType.textProperty().bind(actionType);
        LabelMainEntity.textProperty().bind(mainEntity);
        LabelByExpression.textProperty().bind(byExpression);
    }
    public void setIncreaseDecreaseDetails(ActionDTO actionDTO){
        if (actionDTO instanceof IncreaseDTO){
            actionType.set("Increase");
            mainEntity.set(actionDTO.getMainEntity().getName());
            property.set(((IncreaseDTO) actionDTO).getProperty());
            byExpression.set(((IncreaseDTO) actionDTO).getByExpression());
        }else{
            actionType.set("Decrease");
            mainEntity.set(actionDTO.getMainEntity().getName());
            property.set(((DecreaseDTO) actionDTO).getProperty());
            byExpression.set(((DecreaseDTO) actionDTO).getByExpression());
        }
    }
}

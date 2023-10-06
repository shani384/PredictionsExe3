package client.component.body.simulationDetails.component.action.condition;

import DTOManager.impl.actionDTO.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConditionDetailsController {
    @FXML
    public Label LabelOpOrLogTitle;
    @FXML
    public Label LabelProperty;
    @FXML
    public Label LabelOpOrLog;
    @FXML
    public Label LabelMainEntity;
    @FXML
    public Label LabelValueTitle;
    @FXML
    public Label LabelValue;
    @FXML
    public Label LabelType;
    @FXML
    public Label LabelThen;
    @FXML
    public Label LabelElse;
    @FXML
    public Label LabelPropertyTitle;
    private SimpleStringProperty property;
    private SimpleStringProperty opOrLogTitle;
    private SimpleStringProperty mainEntity;
    private SimpleStringProperty valueTitle;
    private SimpleStringProperty value;
    private SimpleStringProperty type;
    private SimpleIntegerProperty numOfThenActions;
    private SimpleIntegerProperty numOfElseActions;
    private SimpleStringProperty propertyTitle;
    private SimpleStringProperty opOrLog;

    public ConditionDetailsController() {
        this.property = new SimpleStringProperty("");
        this.opOrLogTitle = new SimpleStringProperty("");
        this.mainEntity = new SimpleStringProperty("");
        this.value = new SimpleStringProperty("");
        this.type = new SimpleStringProperty("");
        this.numOfThenActions = new SimpleIntegerProperty();
        this.numOfElseActions = new SimpleIntegerProperty();
        this.propertyTitle = new SimpleStringProperty("");
        this.opOrLog = new SimpleStringProperty("");
        this.valueTitle = new SimpleStringProperty("");

    }

    @FXML
    private void initialize() {
        LabelType.textProperty().bind(type);
        LabelMainEntity.textProperty().bind(mainEntity);
        LabelThen.textProperty().bind(numOfThenActions.asString());
        LabelElse.textProperty().bind(numOfElseActions.asString());
        LabelPropertyTitle.textProperty().bind(propertyTitle);
        LabelProperty.textProperty().bind(property);
        LabelOpOrLogTitle.textProperty().bind(opOrLogTitle);
        LabelOpOrLog.textProperty().bind(opOrLog);
        LabelValueTitle.textProperty().bind(valueTitle);
        LabelValue.textProperty().bind(value);
    }

    public void setConditionDetails(ConditionActionDTO conditionActionDTO) {
        mainEntity.set(conditionActionDTO.getMainEntity().getName());
        numOfThenActions.set(conditionActionDTO.getNumOfThenActions());
        numOfElseActions.set(conditionActionDTO.getNumOfElseActions());
        if (conditionActionDTO.getConditionDTO() instanceof SingleConditionDTO) {
            type.set("Single");
            propertyTitle.set("Property:");
            property.set(((SingleConditionDTO) conditionActionDTO.getConditionDTO()).getProperty());
            opOrLogTitle.set("Operator:");
            opOrLog.set(((SingleConditionDTO) conditionActionDTO.getConditionDTO()).getOperator());
            valueTitle.set("Value:");
            value.set(((SingleConditionDTO) conditionActionDTO.getConditionDTO()).getValue());
        } else if (conditionActionDTO.getConditionDTO() instanceof MultipleConditionDTO) {
            type.set("Multiply");
            propertyTitle.set("");
            property.set("");
            opOrLogTitle.set("Logical:");
            opOrLog.set(((MultipleConditionDTO) (conditionActionDTO.getConditionDTO())).getLogical());
            valueTitle.set("");
            value.set("");
        }
    }
}

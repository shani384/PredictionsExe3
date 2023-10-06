package client.component.body.simulationDetails.component.action.calculation;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.CalculationDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CalculationDetailsController {


    @FXML public Label LabelProperty;
    @FXML public Label LabelArg1;
    @FXML public Label LabelMainEntity;
    @FXML public Label LabelArg2;
    @FXML public Label LabelType;
    private SimpleStringProperty type;
    private SimpleStringProperty property;
    private SimpleStringProperty mainEntity;
    private SimpleStringProperty arg1;
    private SimpleStringProperty arg2;

    public CalculationDetailsController(){
        this.type = new SimpleStringProperty("");
        this.property = new SimpleStringProperty("");
        this.mainEntity = new SimpleStringProperty("");
        this.arg1 = new SimpleStringProperty("");
        this.arg2 = new SimpleStringProperty("");
    }
    @FXML
    private void initialize(){
        LabelType.textProperty().bind(type);
        LabelProperty.textProperty().bind(property);
        LabelMainEntity.textProperty().bind(mainEntity);
        LabelArg1.textProperty().bind(arg1);
        LabelArg2.textProperty().bind(arg2);
    }
    public void setCalculationDetails(CalculationDTO calculationDTO){
        type.set(calculationDTO.getCalculationType());
        property.set(calculationDTO.getProperty());
        mainEntity.set(calculationDTO.getMainEntity().getName());
        arg1.set(calculationDTO.getArg1());
        arg2.set(calculationDTO.getArg2());
    }
}

package client.component.body.simulationDetails.component.property;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import sun.font.TrueTypeFont;

import java.awt.*;

public class PropertyDetailsController {


    @FXML public Label LabelPropertyTitle;
    @FXML public Label LabelPropertyName;
    @FXML public Label LabelPropertyType;
    @FXML public Label LabelPropertyRange;
    @FXML public Label LabelIsRandom;
    @FXML
    public Label LabelRangeOrInit;

    private SimpleStringProperty propertyTitle;
    private SimpleStringProperty propertyName;
    private SimpleStringProperty propertyType;
    private SimpleStringProperty propertyRange;
    private SimpleStringProperty rangeOrInit;
    private SimpleStringProperty isRandom;
    private WorldDTO world;

    public PropertyDetailsController(){
        this.propertyTitle = new SimpleStringProperty("");
        this.propertyName = new SimpleStringProperty("");
        this.propertyType = new SimpleStringProperty("");
        this.propertyRange = new SimpleStringProperty("");
        this.isRandom = new SimpleStringProperty("");
        this.rangeOrInit = new SimpleStringProperty("");
    }
    @FXML
    private void initialize(){
        LabelPropertyTitle.textProperty().bind(propertyTitle);
        LabelPropertyName.textProperty().bind(propertyName);
        LabelPropertyType.textProperty().bind(propertyType);
        LabelPropertyRange.textProperty().bind(propertyRange);
        LabelIsRandom.textProperty().bind(isRandom);
        LabelRangeOrInit.textProperty().bind(rangeOrInit);
    }
    public void setPropertyDetails(TreeItem<String> item) {
        EntityDefinitionDTO entityDefinitionDTO;
        PropertyDefinitionDTO propertyDefinitionDTO;
        if (item.getParent().getParent() != null && item.getParent().getParent().getValue().equals("Entities")) {
            entityDefinitionDTO = world.getEntityDefinitionDTOByName(item.getParent().getValue());
            propertyDefinitionDTO = entityDefinitionDTO.getPropertyDefinitionDTOByName(item.getValue());
            propertyTitle.set("Entity Property");
            if (propertyDefinitionDTO.getRandomInitializer().toString().equals("true")){
                isRandom.set("Property is random initialize");
            }else{
                isRandom.set("Property isn't random initialize");
                rangeOrInit.set("Init Value:");
                propertyRange.set(propertyDefinitionDTO.getValue().toString());
            }
        }
        else{
            propertyDefinitionDTO = world.getEnvVarDTOByName(item.getValue());
            propertyTitle.set("Environment Variable");
        }
        propertyName.set(propertyDefinitionDTO.getName());
        propertyType.set(propertyDefinitionDTO.getPropertyType());
        if (propertyDefinitionDTO.getFrom() != null && propertyDefinitionDTO.getTo() != null) {
            rangeOrInit.set("Range:");
            propertyRange.set(propertyDefinitionDTO.getFrom() + " - " + propertyDefinitionDTO.getTo());
        }
    }
    public void setWorld(WorldDTO worldDTO){
        this.world = worldDTO;
    }
}

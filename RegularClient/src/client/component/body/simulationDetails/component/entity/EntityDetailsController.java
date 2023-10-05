package client.component.body.simulationDetails.component.entity;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;

public class EntityDetailsController {


    @FXML public Label LabelEntityName;
    @FXML public Label LabelPopulation;
    @FXML public ListView<String> ListViewProperties;
    private SimpleStringProperty entityName;
    private SimpleIntegerProperty population;
    private ObservableList<String> properties;
    private WorldDTO world;

    public EntityDetailsController(){
        this.entityName = new SimpleStringProperty("");
        this.population = new SimpleIntegerProperty();
        properties = FXCollections.observableArrayList();
    }
    @FXML
    private void initialize(){
        LabelEntityName.textProperty().bind(entityName);
        LabelPopulation.textProperty().bind(population.asString());
        ListViewProperties.setItems(properties);
    }

    public void setEntityDetails(TreeItem<String> item) {
        EntityDefinitionDTO entityDefinitionDTO = world.getEntityDefinitionDTOByName(item.getValue());
        entityName.set(entityDefinitionDTO.getName());
        population.set(entityDefinitionDTO.getPopulation());
        for (PropertyDefinitionDTO propertyDefinitionDTO:entityDefinitionDTO.getPropertiesDTO()){
            properties.add(propertyDefinitionDTO.getName());
        }
    }
    public void setWorld(WorldDTO worldDTO){
        this.world = worldDTO;
    }
}


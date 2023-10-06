package client.component.body.simulationDetails.component.rule;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.RuleDTO;
import DTOManager.impl.WorldDTO;
import DTOManager.impl.actionDTO.ActionDTO;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;

public class RuleDetailsController {
    @FXML
    public Label LabelRuleName;
    @FXML
    public Label LabelTicks;
    @FXML
    public ListView<String> ListViewActions;
    @FXML
    public Label LabelProbability;
    private SimpleStringProperty ruleName;
    private SimpleIntegerProperty ticks;
    private SimpleFloatProperty probability;
    private ObservableList<String> actions;
    private WorldDTO world;

    public RuleDetailsController() {
        this.ruleName = new SimpleStringProperty("");
        this.ticks = new SimpleIntegerProperty();
        this.probability = new SimpleFloatProperty();
        actions = FXCollections.observableArrayList();
    }
    @FXML
    private void initialize(){
       LabelRuleName.textProperty().bind(ruleName);
       LabelTicks.textProperty().bind(ticks.asString());
       LabelProbability.textProperty().bind(probability.asString("%.2f"));
       ListViewActions.setItems(actions);
    }

    public void setRuleDetails(TreeItem<String> item) {
        RuleDTO ruleDTO = world.getRuleDTOByName(item.getValue());
        ruleName.set(ruleDTO.getName());
        ticks.set(ruleDTO.getTicks());
        probability.set(ruleDTO.getProbability().floatValue());
        for (ActionDTO actionDTO: ruleDTO.getActions()){
            actions.add(actionDTO.getActionType());
        }
    }

    public void setWorld(WorldDTO worldDTO){
        this.world = worldDTO;
    }
}

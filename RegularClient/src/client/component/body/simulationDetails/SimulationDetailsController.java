package client.component.body.simulationDetails;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.RuleDTO;
import DTOManager.impl.WorldDTO;
import DTOManager.impl.actionDTO.*;
import client.component.body.simulationDetails.component.action.calculation.CalculationDetailsController;
import client.component.body.simulationDetails.component.action.condition.ConditionDetailsController;
import client.component.body.simulationDetails.component.action.increasedecrease.IncreaseDecreaseDetailsController;
import client.component.body.simulationDetails.component.action.proximityreplace.ProximityReplaceDetailsController;
import client.component.body.simulationDetails.component.action.setkill.SetKillDetailsController;
import client.component.body.simulationDetails.component.entity.EntityDetailsController;
import client.component.body.simulationDetails.component.property.PropertyDetailsController;
import client.component.body.simulationDetails.component.rule.RuleDetailsController;
import client.util.Constants;
import client.util.HttpClientUtil;
import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public class SimulationDetailsController {

        @FXML
        private GridPane gridPane;
        @FXML
        private SplitPane splitPane;
        @FXML
        private TreeView<String> treeView;
        @FXML
        private FlowPane details;
        private Map<String, WorldDTO>  worldDTOMap;

        public void setWorlds(Object worldsDTO) {
        }

        public void worldsMenu() {
                TreeItem<String> worldRoot = new TreeItem<>("Simulations Types");
                treeView.setRoot(worldRoot);
                for (Map.Entry<String, WorldDTO> entry : worldDTOMap.entrySet()) {
                        String name = entry.getKey();
                        WorldDTO world = entry.getValue();
                        TreeItem<String> simulation = new TreeItem<>(name);
                        worldRoot.getChildren().add(simulation);
                        TreeItem<String> entitiesNode = new TreeItem<>("Entities");
                        TreeItem<String> envVarsNode = new TreeItem<>("Environment Variables");
                        TreeItem<String> rulesNode = new TreeItem<>("Rules");
                        //TreeItem<String> generalsNode = new TreeItem<>("Generals");
                        simulation.getChildren().addAll(entitiesNode, envVarsNode, rulesNode);
                        for (EntityDefinitionDTO entityDefinitionDTO : world.getNameToEntityDefinitionDTO().values()) {
                                TreeItem<String> entityNode = new TreeItem<>(entityDefinitionDTO.getName());
                                entitiesNode.getChildren().add(entityNode);
                                for (PropertyDefinitionDTO propertyDefinitionDTO : entityDefinitionDTO.getPropertiesDTO()) {
                                        TreeItem<String> propertyNode = new TreeItem<>(propertyDefinitionDTO.getName());
                                        entityNode.getChildren().add(propertyNode);
                                }
                        }
                        for (PropertyDefinitionDTO envVarsDTO : world.getEnvPropertiesDefinitionDTO()) {
                                TreeItem<String> envVarNode = new TreeItem<>(envVarsDTO.getName());
                                envVarsNode.getChildren().add(envVarNode);
                        }
                        for (RuleDTO ruleDTO : world.getRulesDTO()) {
                                TreeItem<String> ruleNode = new TreeItem<>(ruleDTO.getName());
                                rulesNode.getChildren().add(ruleNode);
                                for (ActionDTO actionDTO : ruleDTO.getActions()) {
                                        TreeItem<String> actionNode = new TreeItem<>(actionDTO.getActionType());
                                        ruleNode.getChildren().add(actionNode);
                                }
                        }
                }
        }
        private void loadItem(TreeItem<String> item) {
                if(item == null){
                        return;
                }else if(item.getParent() != null && item.getParent().getValue().equals("Entities")){
                        WorldDTO worldDTO = worldDTOMap.get(item.getParent().getParent().getValue());
                        loadEntity(item,worldDTO);
                }else if (item.getParent()!= null && item.getParent().getParent() != null && item.getParent().getParent().getValue().equals("Entities")) {
                        WorldDTO worldDTO = worldDTOMap.get(item.getParent().getParent().getParent().getValue());
                        loadProperty(item,worldDTO);
                }else if(item.getParent() != null && item.getParent().getValue().equals("Rules")){
                        WorldDTO worldDTO = worldDTOMap.get(item.getParent().getParent().getValue());
                        loadRule(item,worldDTO);
                }else if(item.getParent() != null && item.getParent().getValue().equals("Environment Variables")){
                        WorldDTO worldDTO = worldDTOMap.get(item.getParent().getParent().getValue());
                        loadProperty(item,worldDTO);
                }else if(item.getParent() != null && item.getParent().getParent() != null && item.getParent().getParent().getValue().equals("Rules")){
                        WorldDTO worldDTO = worldDTOMap.get(item.getParent().getParent().getParent().getValue());
                        loadAction(item,worldDTO);
                }
                try {
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        private void loadAction(TreeItem<String> item, WorldDTO world) {
                ObservableList<TreeItem<String>> children = item.getParent().getChildren();
                int index = children.indexOf(item);
                RuleDTO ruleDTO = world.getRuleDTOByName(item.getParent().getValue());
                ActionDTO actionDTO = ruleDTO.getActionByIndex(index);
                if(actionDTO instanceof IncreaseDTO || actionDTO instanceof DecreaseDTO) {
                        loadIncreaseDecrease(actionDTO);
                } else if (actionDTO instanceof CalculationDTO) {
                        loadCalculation((CalculationDTO) actionDTO);
                }else if (actionDTO instanceof KillDTO || actionDTO instanceof SetDTO) {
                        loadSetKill(actionDTO);
                } else if (actionDTO instanceof ConditionActionDTO) {
                        loadCondition((ConditionActionDTO)actionDTO);
                }else if(actionDTO instanceof ProximityDTO || actionDTO instanceof ReplaceDTO){
                        loadProximityReplace(actionDTO);
                }

        }

        private void loadProximityReplace(ActionDTO actionDTO) {
                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(Constants.ProximityReplace_Details_FXML_RESOURCE));
                        GridPane detailsBox = loader.load();
                        ProximityReplaceDetailsController proximityReplaceDetailsController = loader.getController();
                        proximityReplaceDetailsController.setProximityReplaceDetails(actionDTO);
                        details.getChildren().clear();
                        details.getChildren().add(detailsBox);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void loadCondition(ConditionActionDTO conditionActionDTO) {
                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(Constants.Condition_Details_FXML_RESOURCE));
                        GridPane detailsBox = loader.load();
                        ConditionDetailsController conditionDetailsController = loader.getController();
                        conditionDetailsController.setConditionDetails(conditionActionDTO);
                        details.getChildren().clear();
                        details.getChildren().add(detailsBox);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void loadSetKill(ActionDTO actionDTO) {
                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(Constants.SetKill_Details_FXML_RESOURCE));
                        GridPane detailsBox = loader.load();
                        SetKillDetailsController setKillDetailsController = loader.getController();
                        setKillDetailsController.setSetKillDetails(actionDTO);
                        details.getChildren().clear();
                        details.getChildren().add(detailsBox);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void loadCalculation(CalculationDTO calculationDTO) {
                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(Constants.Calculation_Details_FXML_RESOURCE));
                        GridPane detailsBox = loader.load();
                        CalculationDetailsController calculationDetailsController = loader.getController();
                        calculationDetailsController.setCalculationDetails(calculationDTO);
                        details.getChildren().clear();
                        details.getChildren().add(detailsBox);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void loadIncreaseDecrease(ActionDTO actionDTO) {
                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(Constants.IncreaseDecrease_Details_FXML_RESOURCE));
                        GridPane detailsBox = loader.load();
                        IncreaseDecreaseDetailsController increaseDecreaseDetailsController = loader.getController();
                        increaseDecreaseDetailsController.setIncreaseDecreaseDetails(actionDTO);
                        details.getChildren().clear();
                        details.getChildren().add(detailsBox);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void loadEntity(TreeItem<String> item,WorldDTO world) {
                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(Constants.Entity_Details_FXML_RESOURCE));
                        GridPane detailsBox = loader.load();
                        EntityDetailsController entityDetailsController= loader.getController();
                        entityDetailsController.setWorld(world);
                        entityDetailsController.setEntityDetails(item);
                        details.getChildren().clear();
                        details.getChildren().add(detailsBox);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }


        private void loadRule(TreeItem<String> item,WorldDTO world) {
                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(Constants.Rule_Details_FXML_RESOURCE));
                        GridPane detailsBox = loader.load();
                        RuleDetailsController ruleDetailsController= loader.getController();
                        ruleDetailsController.setWorld(world);
                        ruleDetailsController.setRuleDetails(item);
                        details.getChildren().clear();
                        details.getChildren().add(detailsBox);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private void loadProperty(TreeItem<String> item, WorldDTO world) {
                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource(Constants.Property_Details_FXML_RESOURCE));
                        GridPane detailsBox = loader.load();
                        PropertyDetailsController propertyDetailsController= loader.getController();
                        propertyDetailsController.setWorld(world);
                        propertyDetailsController.setPropertyDetails(item);
                        details.getChildren().clear();
                        details.getChildren().add(detailsBox);
                } catch (IOException e) {
                        e.printStackTrace();
                }

        }
        @FXML
        public void showItem(MouseEvent mouseEvent) {
                TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
                if (item != null && item.getValue() != null) {
                        loadItem(item);
                }
        }
        public void requestWorldsDTO() {
                HttpClientUtil.runAsync(Constants.SIMULATION_DETAILS, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            assert response.body() != null;
                            String jsonMapOfWorlds = response.body().string();
                            Gson gson = new Gson();
                            String[] worldsDTO = gson.fromJson(jsonMapOfWorlds,null);////////////////////

                        }
                });

        }

        // You can add additional methods and logic as needed


}

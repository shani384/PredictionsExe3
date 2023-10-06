package engine.world.design.world.impl;

import DTOManager.impl.*;
import engine.SimulationOutcome;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.entity.manager.EntityInstanceManagerImpl;
import engine.world.design.execution.environment.api.ActiveEnvironment;
import engine.world.design.grid.api.Grid;
import engine.world.design.grid.impl.GridImpl;
import engine.world.design.termination.api.Termination;
import engine.world.design.termination.impl.TerminationImpl;
import engine.world.design.termination.second.Second;
import engine.world.design.termination.second.SecondImpl;
import engine.world.design.termination.tick.api.Tick;
import engine.world.design.termination.tick.impl.TickImpl;
import engine.world.design.world.api.World;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.environment.api.EnvVariablesManager;
import engine.world.design.rule.Rule;

import java.text.SimpleDateFormat;
import java.util.*;

public class WorldImpl implements World {

    private Map<String, EntityDefinition> nameToEntityDefinition;
    private EnvVariablesManager envVariablesManager;
    private List<Rule> rules;
    private Termination termination;
    private Grid grid;
    private int numOfThreads;

// TODO: 10/08/2023 List<Termination> terminationConditions;
    public WorldImpl() {

    }
//    public WorldImpl clone(){
//        Map<String, EntityDefinition> entities = new HashMap<>();
//        for (Map.Entry<String, EntityDefinition> entry : nameToEntityDefinition.entrySet()) {
//            EntityDefinition originalEntity = entry.getValue();
//            EntityDefinition copiedEntity = new EntityDefinition(originalEntity); // Assuming EntityDefinition has a copy constructor
//            entities.put(entry.getKey(), copiedEntity);
//        }
//    }
    public Map<String, EntityDefinition> getNameToEntityDefinition() {
        return nameToEntityDefinition;
    }
    public Termination getTermination() {
        return termination;
    }

    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public void setNumOfThreads(int numOfThreads) {
        this.numOfThreads = numOfThreads;
    }
    @Override
    public int getNumOfThreads() {
        return numOfThreads;
    }

    @Override
    public EntityDefinition getEntityDefinitionByName(String name) {
        if(!nameToEntityDefinition.containsKey(name)) {
            throw new IllegalArgumentException(name + "is not a name of entity");
        }
        return nameToEntityDefinition.get(name);
    }
    @Override
    public WorldDTO createWorldDTO(){
        Map<String, EntityDefinitionDTO> entityDefinitionDTOMap= new HashMap<>();
        for (EntityDefinition entityDefinition: nameToEntityDefinition.values()){
            entityDefinitionDTOMap.put(entityDefinition.getName(),entityDefinition.createEntityDefinitionDTO());
        }
        List<RuleDTO> rulesDTO = new ArrayList<>();
        for (Rule rule:rules){
            rulesDTO.add(rule.createRuleDTO());
        }
//        TerminationDTO terminationDTO = termination.createTerminationDTO();
        List<PropertyDefinitionDTO> envPropertiesDefinitionDTO = new ArrayList<>();
        for (PropertyDefinition propertyDefinition : envVariablesManager.getEnvVariables().values()){
            envPropertiesDefinitionDTO.add(propertyDefinition.createPropertyDefinitionDTO());
        }
        GridDTO gridDTO = grid.createGridDTO();
        return new WorldDTO(entityDefinitionDTOMap,rulesDTO,null ,envPropertiesDefinitionDTO, gridDTO);
    }
    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public EnvVariablesManager getEnvVariablesManager() {
        return envVariablesManager;
    }
    @Override
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    @Override
    public SimulationOutcome runSimulation(int id) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        String formattedDate = dateFormat.format(currentDate);
        // creating the Active Environment - if the user gave the property its value we will use it otherwise generate value
        ActiveEnvironment activeEnvironment = envVariablesManager.createActiveEnvironment();
        Grid gridSimulation = new GridImpl(grid.getColumns(),grid.getRows());
        EntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl(gridSimulation);
        Termination terminationSimulation = new TerminationImpl();
        Tick tick = null;
        Second second = null;
        if (terminationSimulation.getTicks() != null) {
            tick = new TickImpl(termination.getTicks().getTicks());
        }
        if (terminationSimulation.getTicks() != null){
            second = new SecondImpl(termination.getSecondsToPast().getSeconds());
        }
        terminationSimulation.setTicks(tick);
        terminationSimulation.setSecondsToPast(second);
        return new SimulationOutcome(formattedDate,id, terminationSimulation,entityInstanceManager, entityInstanceManager.createDTO(),activeEnvironment, gridSimulation);
        // TODO: 11/09/2023 change name 


//        for (PropertyDefinition envVarDefinition: envVariablesManager.getEnvVariables().values()) {
//            String envName = envVarDefinition.getName();
//            if(propertyNameToValueAsString.containsKey(envName)) {
//                Object value = envVarDefinition.getType().convert(propertyNameToValueAsString.get(envName));
//                activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(envVarDefinition,value));
//            }
//            else {
//                activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(envVarDefinition, envVarDefinition.generateValue()));
//            }
//        }
//        // showFinalEnvProperties();
//        // creating the instance manager
//        for (EntityDefinition entityDefinition: nameToEntityDefinition.values()) {
//            for (int i = 0 ;i < entityDefinition.getPopulation(); i++) {
//                entityInstanceManager.create(entityDefinition);
//            }
//        }
//
//        termination.startTerminationClock();
//        Map<Integer, SimulationOutcome> informationSimulation = new HashMap();
//        // take a picture
//        int ticks = 0;
//        //informationSimulation.put(0, simulationOutcome);
//        while (!termination.isTerminated(ticks)) {
//            for (EntityInstance entityInstance: entityInstanceManager.getInstances().values()) {
//                Context context = new ContextImpl(entityInstance, entityInstanceManager, activeEnvironment);
//                for (Rule rule: rules) {
//                    if (rule.getActivation().isActive(ticks)) {
//                        rule.getActionToPreform().forEach(action -> action.invoke(context));
//                    }
//                }
//            }
//            entityInstanceManager.killEntities();
//            ticks++;
//        }
        //informationAboutInstance.put(1, entityInstanceManager.createDTO());
    }
    @Override
    public void setEntities(Map<String, EntityDefinition> entities) {
        nameToEntityDefinition = entities;
    }

    @Override
    public void setEnvVariablesManager(EnvVariablesManager envVariablesManager) {
        this.envVariablesManager = envVariablesManager;
    }

    @Override
    public void setRules(List<Rule> ruleList) {
        this.rules = ruleList;
    }


    @Override
    public void setTermination(Termination termination) {
        this.termination = termination;
    }
}

package engine.world.design.reader.impl;

import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.action.calculation.CalculationType;
import engine.world.design.action.condition.*;
import engine.world.design.action.impl.*;
import engine.world.design.grid.impl.GridImpl;
import engine.world.design.rule.Rule;
import engine.world.design.rule.RuleImpl;
import engine.world.design.rule.activation.api.Activation;
import engine.world.design.rule.activation.impl.ActivationImpl;
import engine.world.design.termination.impl.TerminationImpl;
import engine.world.design.termination.second.Second;
import engine.world.design.termination.second.SecondImpl;
import engine.world.design.termination.tick.api.Tick;
import engine.world.design.termination.tick.impl.TickImpl;
import engine.world.design.world.api.World;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.entity.impl.EntityDefinitionImpl;
import engine.world.design.definition.environment.api.EnvVariablesManager;
import engine.world.design.definition.environment.impl.EnvVariablesManagerImpl;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.definition.property.impl.BooleanPropertyDefinition;
import engine.world.design.definition.property.impl.FloatPropertyDefinition;
import engine.world.design.definition.property.impl.IntegerPropertyDefinition;
import engine.world.design.definition.property.impl.StringPropertyDefinition;
import engine.world.design.definition.value.generator.api.ValueGeneratorFactory;
import engine.world.design.world.impl.WorldImpl;
import engine.world.design.reader.api.Reader;
import schema.generated.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class ReaderImpl implements Reader {

    World createdWorld;
    PRDWorld prdWorld;



    @Override
    // after this method The Instance has its own copy of World build from the XML
    public void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME) {
        createdWorld = new WorldImpl();
        try {
            File xmlFile = new File(XML_PATH);

            if (xmlFile.exists()) {
                prdWorld = deserializedFrom(JAXB_XML_PACKAGE_NAME, new FileInputStream(xmlFile));
                readPRDWorld();
            } else {
                throw new IllegalArgumentException("XML file not found: " + XML_PATH);
            }
        } catch (JAXBException | FileNotFoundException e) {
            throw new IllegalArgumentException("XML file not found: " + XML_PATH);
            // Handle JAXB exception
        }
    }

//    @Override
//    // after this method The Instance has its own copy of World build from the XML
//    public void readWorldFromXml(String XML_PATH, String JAXB_XML_PACKAGE_NAME) {
//        createdWorld = new WorldImpl();
//        try {
//            InputStream inputStream = new FileInputStream(new File(XML_PATH));
//            prdWorld = deserializedFrom( JAXB_XML_PACKAGE_NAME, inputStream);
//            readPRDWorld();
//        }catch(JAXBException | FileNotFoundException e){
//            // TODO: 11/08/2023 deal with this problem - file not found or not an XML file message
//            e.printStackTrace();
//        }
//    }

    private static PRDWorld deserializedFrom(String JAXB_XML_PACKAGE_NAME, InputStream in)throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (PRDWorld) u.unmarshal(in);
    }

    private void readPRDWorld() {
        buildEntitiesFromPRD(prdWorld.getPRDEntities());
        buildEnvironmentFromPRD(prdWorld.getPRDEnvironment());
        buildGridFromPRD(prdWorld.getPRDGrid());
        buildRulesFromPRD(prdWorld.getPRDRules());
        //buildTerminationFromPRD(prdWorld.getPRDTermination());
        //createdWorld.setNumOfThreads(prdWorld.getPRDThreadCount());
    }

    private void buildGridFromPRD(PRDWorld.PRDGrid prdGrid) {
        int columns = prdGrid.getColumns();
        int rows = prdGrid.getRows();
        if(columns < 10 || columns > 100){
            throw new RuntimeException("Size of columns is invalid");
        }
        if (rows < 10 || rows > 100){
            throw new RuntimeException("Size of rows is invalid");
        }
        GridImpl grid = new GridImpl(prdGrid.getColumns(), prdGrid.getRows());
        createdWorld.setGrid(grid);
    }

    private void buildTerminationFromPRD(PRDTermination prdTermination) {
        TerminationImpl termination = new TerminationImpl();
        for (Object prdTicksOrSeconds :prdTermination.getPRDBySecondOrPRDByTicks()) {
            if(prdTicksOrSeconds instanceof PRDByTicks) {
                Tick tick = new TickImpl(((PRDByTicks) prdTicksOrSeconds).getCount());
                termination.setTicks(tick);
            }
            else if(prdTicksOrSeconds instanceof PRDBySecond) {
                Second second = new SecondImpl(((PRDBySecond) prdTicksOrSeconds).getCount());
                termination.setSecondsToPast(second);
            }
            else {
                throw new RuntimeException(prdTicksOrSeconds.toString() + "is of unexpected Class");
            }
        }
        createdWorld.setTermination(termination);
    }
    private void buildRulesFromPRD(PRDRules prdRules) {
        List<Rule> ruleList = new ArrayList<>();
        Action action;
        Rule currRule = null;
        Activation activation = null;
        for (PRDRule prdRule: prdRules.getPRDRule()) {
            String name = prdRule.getName();
            if (prdRule.getPRDActivation() != null) {
                Integer ticks = prdRule.getPRDActivation().getTicks();
                Double probability = prdRule.getPRDActivation().getProbability();
                activation = new ActivationImpl(ticks,probability);
            }
            else {
                activation = new ActivationImpl();
            }
            currRule = new RuleImpl(name, activation);
            for (PRDAction prdAction : prdRule.getPRDActions().getPRDAction()) {
                currRule.addAction(buildActionFromPRD(prdAction));
            }
            ruleList.add(currRule);
        }
        createdWorld.setRules(ruleList);
    }
    private Action buildActionFromPRD(PRDAction prdAction) {
        Action res;
        switch (prdAction.getType()) {
            case ("increase"):
                res = createIncreaseOrDecreaseAction(prdAction, ActionType.INCREASE);
                break;
            case ("decrease"):
                res = createIncreaseOrDecreaseAction(prdAction, ActionType.DECREASE);
                break;
            case ("calculation"):
                res = createcalCulationAction(prdAction);
                break;
            case ("condition"):
                res = createConditionAction(prdAction);
                break;
            case ("set"):
                res = createSetAction(prdAction);
                break;
            case ("kill"):
                res = createKillAction(prdAction);
                break;
            case("proximity"):
                res = createProximityAction(prdAction);;
                break;
            case("replace"):
                res = createReplaceAction(prdAction);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + prdAction.getType());
        }
        return res;
    }
    private Action createReplaceAction(PRDAction prdAction) {
        Action res = null;
        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getKill());
        InteractiveEntity interactiveEntity = getInteractive(prdAction);
        EntityDefinition createEntity = createdWorld.getEntityDefinitionByName(prdAction.getCreate());
        return new ReplaceAction(mainEntity,interactiveEntity,prdAction.getMode(), createEntity);
    }
    private Action createProximityAction(PRDAction prdAction) {
        Action res = null;
        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getPRDBetween().getSourceEntity());
        Condition conditionAction = null;
        InteractiveEntity interactiveEntity = getInteractive(prdAction);
        EntityDefinition targetEntity = createdWorld.getEntityDefinitionByName(prdAction.getPRDBetween().getTargetEntity());
        ArrayList<Action> actions = new ArrayList<>();
        for (PRDAction prdProximityAction: prdAction.getPRDActions().getPRDAction()){
            actions.add(buildActionFromPRD(prdProximityAction));
        }
        return new ProximityAction(mainEntity,interactiveEntity,prdAction.getPRDEnvDepth().getOf(),createdWorld.getGrid().getColumns(),createdWorld.getGrid().getRows(),actions, targetEntity);
    }
    private InteractiveEntity getInteractive(PRDAction prdAction){
        Condition conditionAction = null;
        InteractiveEntity interactiveEntity = null;
        if (prdAction.getPRDSecondaryEntity() != null){
            EntityDefinition secondEntity = createdWorld.getEntityDefinitionByName(prdAction.getPRDSecondaryEntity().getEntity());
            int count = Integer.parseInt(prdAction.getPRDSecondaryEntity().getPRDSelection().getCount());
            if(prdAction.getPRDSecondaryEntity().getPRDSelection().getPRDCondition() != null) {
                conditionAction = createSubCondition(prdAction.getPRDSecondaryEntity().getPRDSelection().getPRDCondition());
            }
            interactiveEntity = new InteractiveEntity(secondEntity,count,conditionAction);
        }
        return interactiveEntity;
    }

    private Action createSetAction(PRDAction prdAction) {
        Action res = null;
        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getEntity());
        InteractiveEntity interactiveEntity= getInteractive(prdAction);
        String property = prdAction.getProperty();
        String value = prdAction.getValue();
        res = new SetAction(mainEntity, interactiveEntity,property, value);
        return res;
    }
    private Action createConditionAction(PRDAction prdAction) {
        ConditionAction res = null;
        Condition condition = null;
        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getEntity());
        InteractiveEntity interactiveEntity= getInteractive(prdAction);
        String singularity = prdAction.getPRDCondition().getSingularity();
        switch (singularity) {
            case "single":
                EntityDefinition entity =  createdWorld.getEntityDefinitionByName(prdAction.getPRDCondition().getEntity());
                String property = prdAction.getPRDCondition().getProperty();
                String value = prdAction.getPRDCondition().getValue();
                String operator = prdAction.getPRDCondition().getOperator();
                condition = new SingleCondition(entity, property,value,operator);
                res = new ConditionAction(mainEntity,interactiveEntity,condition);
                break;
            case "multiple":
                String logical = prdAction.getPRDCondition().getLogical();
                if(!logical.equals("or") && !logical.equals("and")){
                    throw new RuntimeException("invalid logical value");
                }
                MultipleCondition multipleCondition = new MultipleCondition(logical);
                for (PRDCondition prdCondition: prdAction.getPRDCondition().getPRDCondition()){
                    multipleCondition.addCondition(createSubCondition(prdCondition));
                }
                res = new ConditionAction(mainEntity,interactiveEntity,multipleCondition);
                break;
            default:
                throw new IllegalArgumentException(singularity + "is not a valid Condition Singularity");
        }
        for (PRDAction prdAction1: prdAction.getPRDThen().getPRDAction()){
            res.getThenActions().add(buildActionFromPRD(prdAction1));
        }
        if (prdAction.getPRDElse() != null) {
            for (PRDAction prdAction1 : prdAction.getPRDElse().getPRDAction()) {
                res.getElseActions().add(buildActionFromPRD(prdAction1));
            }
        }
        return res;
    }
    private Condition createSubCondition(PRDCondition prdCondition) {
        Condition res = null;
        String singularity = prdCondition.getSingularity();
        switch (singularity) {
            case "single":
                EntityDefinition entity =  createdWorld.getEntityDefinitionByName(prdCondition.getEntity());
                String property = prdCondition.getProperty();
                String value = prdCondition.getValue();
                String operator = prdCondition.getOperator();
                res = new SingleCondition(entity, property,value,operator);
                break;
            case "multiple":
                String logical = prdCondition.getLogical();
                if(!logical.equals("or") && !logical.equals("and")){
                    throw new RuntimeException("invalid logical value");
                }
                MultipleCondition multipleCondition = new MultipleCondition(logical);
                for (PRDCondition prdCondition1: prdCondition.getPRDCondition()){
                    multipleCondition.addCondition(createSubCondition(prdCondition1));
                }
                res = multipleCondition;
                break;
            default:
                throw new IllegalArgumentException(singularity + "is not a valid Condition Singularity");
        }
        return res;
    }
    private Action createcalCulationAction(PRDAction prdAction) {
        Action res = null;
        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getEntity());
        InteractiveEntity interactiveEntity= getInteractive(prdAction);
        String property = prdAction.getResultProp();
        String arg1 = null, arg2 = null;
        CalculationType calculationType = null;
        if(prdAction.getPRDMultiply() != null) {
            arg1 = prdAction.getPRDMultiply().getArg1();
            arg2 = prdAction.getPRDMultiply().getArg2();
            calculationType = CalculationType.MULTIPLY;
        } else if (prdAction.getPRDDivide() != null) {
            arg1 = prdAction.getPRDDivide().getArg1();
            arg2 = prdAction.getPRDDivide().getArg2();
            calculationType = CalculationType.DIVIDE;
        }
        else {
            throw new IllegalArgumentException(prdAction + "is Calculation but illegal property" +prdAction.getPRDDivide().toString() +prdAction.getPRDMultiply().toString());
        }
        res = new CalculationAction(mainEntity,interactiveEntity, property, arg1, arg2, calculationType);
        return res;
    }
    private Action createKillAction(PRDAction prdAction) {
        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getEntity());
        InteractiveEntity interactiveEntity= getInteractive(prdAction);
        return new KillAction(mainEntity,interactiveEntity);
    }
    private Action createIncreaseOrDecreaseAction(PRDAction prdAction, ActionType type) {
        Action res = null ;
        EntityDefinition mainEntity = createdWorld.getEntityDefinitionByName(prdAction.getEntity());
        InteractiveEntity interactiveEntity= getInteractive(prdAction);
        String propertyName = prdAction.getProperty();
        String byExpression = prdAction.getBy();
        if(type == ActionType.INCREASE) {
            res = new IncreaseAction(mainEntity,interactiveEntity,propertyName,byExpression);
        }
        else if (type == ActionType.DECREASE) {
            res = new DecreaseAction(mainEntity,interactiveEntity,propertyName,byExpression);
        }
        return res;
    }
    @Override
    public World getWorld() {
        return createdWorld;
    }
    @Override
    public void readEnvironmentPropertiesFromUser(Map<String, Object> propertyNameToValueAsString) {
        for (Object value: propertyNameToValueAsString.values()){
            createdWorld.getEnvVariablesManager();// TODO: 20/08/2023 delete?
        }
    }

    /**
     * this code is responsible for creating Property definition from the correct Type
     */
    private void buildEnvironmentFromPRD(PRDEnvironment prdEnvironment) {
        EnvVariablesManager envVariablesManager = new EnvVariablesManagerImpl();
        for(PRDEnvProperty prdEnvProperty: prdEnvironment.getPRDEnvProperty()) {
            if (envVariablesManager.getEnvVariables().containsKey(prdEnvProperty.getPRDName())) {
                throw new IllegalArgumentException(prdEnvProperty.getPRDName() + "is already exists in the simulation");
            }
            switch (prdEnvProperty.getType()) {
                case "decimal":
                    envVariablesManager.addEnvironmentVariable(createDecimalPropertyDefinition(prdEnvProperty));
                    break;
                case "float":
                    envVariablesManager.addEnvironmentVariable(createFloatPropertyDefinition(prdEnvProperty));
                    break;
                case "boolean":
                    envVariablesManager.addEnvironmentVariable(createBooleanPropertyDefinition(prdEnvProperty));
                    break;
                case "string":
                    envVariablesManager.addEnvironmentVariable(createStringPropertyDefinition(prdEnvProperty));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + prdEnvProperty.getType());
            }
        }
        createdWorld.setEnvVariablesManager(envVariablesManager);
    }
    private PropertyDefinition createStringPropertyDefinition(Object i_prdProperty) {
        PropertyDefinition res = null;
        if( i_prdProperty instanceof PRDEnvProperty) {
            PRDEnvProperty prdEnvProperty = (PRDEnvProperty) i_prdProperty;
            String name = prdEnvProperty.getPRDName();
            res = new StringPropertyDefinition(name, ValueGeneratorFactory.createRandomString());
        }
        else {
            if (i_prdProperty instanceof PRDProperty) {
                PRDProperty prdProperty = (PRDProperty) i_prdProperty;
                PRDValue prdValue = prdProperty.getPRDValue();
                String name = prdProperty.getPRDName();
                if(prdValue.isRandomInitialize()) {
                    res = new StringPropertyDefinition(name, ValueGeneratorFactory.createRandomString());
                }
                else {
                    res = new StringPropertyDefinition(name, ValueGeneratorFactory.createFixed(prdValue.getInit()));
                }
            }
        }
        if(res == null) {
            throw new IllegalArgumentException(i_prdProperty.toString() + "is not expected type");
        }
        return res;

    }
    private PropertyDefinition createBooleanPropertyDefinition(Object i_prdProperty) {
        PropertyDefinition res = null;
        if(i_prdProperty instanceof PRDEnvProperty) {
            PRDEnvProperty prdEnvProperty = (PRDEnvProperty) i_prdProperty;
            String name = prdEnvProperty.getPRDName();
            res = new BooleanPropertyDefinition(name,ValueGeneratorFactory.createRandomBoolean());
        }
        else if( i_prdProperty instanceof PRDProperty) {
            PRDProperty prdProperty = (PRDProperty) i_prdProperty;
            PRDValue prdValue = prdProperty.getPRDValue();
            String name = prdProperty.getPRDName();
            if(prdValue.isRandomInitialize()) {
                res = new BooleanPropertyDefinition(name,ValueGeneratorFactory.createRandomBoolean());
            }
            else {
                res = new BooleanPropertyDefinition(name,ValueGeneratorFactory.createFixed(PropertyType.BOOLEAN.convert(prdValue.getInit())));
            }
        }
        if(res == null) {
            throw new IllegalArgumentException(i_prdProperty.toString() + "is not expected type");
        }
        return res;
    }
    private PropertyDefinition createFloatPropertyDefinition(Object i_prdProperty) {
        PropertyDefinition res = null;
        try {
            if (i_prdProperty instanceof PRDEnvProperty) {
                PRDEnvProperty prdEnvProperty = (PRDEnvProperty) i_prdProperty;
                Float from = PropertyType.FLOAT.convert(prdEnvProperty.getPRDRange().getFrom());
                Float to = PropertyType.FLOAT.convert(prdEnvProperty.getPRDRange().getTo());
                String name = prdEnvProperty.getPRDName();
                res = new FloatPropertyDefinition(name, ValueGeneratorFactory.createRandomFloat(from, to));
            } else if (i_prdProperty instanceof PRDProperty) {
                PRDProperty prdProperty = (PRDProperty) i_prdProperty;
                PRDValue prdValue = prdProperty.getPRDValue();
                String name = prdProperty.getPRDName();
                Float from = PropertyType.FLOAT.convert(prdProperty.getPRDRange().getFrom());
                Float to = PropertyType.FLOAT.convert(prdProperty.getPRDRange().getTo());
                if (prdValue.isRandomInitialize()) {
                    res = new FloatPropertyDefinition(name, ValueGeneratorFactory.createRandomFloat(from, to));
                } else {
                    res = new FloatPropertyDefinition(name, ValueGeneratorFactory.createFixed(PropertyType.FLOAT.convert(prdValue.getInit())));
                }
            }
            if(res == null) {
                throw new IllegalArgumentException(i_prdProperty.toString() + "is not expected type");
            }
        }catch (Exception e){
            throw new RuntimeException("something went wrong in createFloatPropertyDefinition");
        }



        return res;
    }
    private PropertyDefinition createDecimalPropertyDefinition(Object i_prdProperty) {
        PropertyDefinition res = null;
        if(i_prdProperty instanceof PRDEnvProperty) {
            PRDEnvProperty prdEnvProperty = (PRDEnvProperty) i_prdProperty;
            Integer from = PropertyType.DECIMAL.convert(prdEnvProperty.getPRDRange().getFrom());
            Integer to = PropertyType.DECIMAL.convert(prdEnvProperty.getPRDRange().getTo());
            String name = prdEnvProperty.getPRDName();
            res = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createRandomInteger(from, to));
        }
        else if( i_prdProperty instanceof PRDProperty) {
            PRDProperty prdProperty = (PRDProperty) i_prdProperty;
            PRDValue prdValue = prdProperty.getPRDValue();
            String name = prdProperty.getPRDName();
            Integer from = PropertyType.DECIMAL.convert(prdProperty.getPRDRange().getFrom());
            Integer to = PropertyType.DECIMAL.convert(prdProperty.getPRDRange().getTo());
            if(prdValue.isRandomInitialize()) {
                res = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createRandomInteger(from,to));
            }
            else {
                res = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createFixed(PropertyType.DECIMAL.convert(prdValue.getInit())));
            }
        }
        if(res == null) {
            throw new IllegalArgumentException(i_prdProperty.toString() + "is not expected type");
        }
        return res;
    }
    /**
     * this code is responsible for creating the Entities from the PRD files
     */
    private void buildEntitiesFromPRD(PRDEntities prdEntities) {
        Map<String, EntityDefinition> entities = new HashMap<>();
        for (PRDEntity prdEntity: prdEntities.getPRDEntity()){
            EntityDefinition currEntity = new EntityDefinitionImpl(prdEntity.getName());
            for (PRDProperty prdProperty : prdEntity.getPRDProperties().getPRDProperty()) {
                if (currEntity.getProps().contains(prdProperty)) {
                    throw new IllegalArgumentException("Property " + prdProperty + " already exists in the Entity " + currEntity.getName());
                }
                switch (prdProperty.getType()) {
                    case "decimal":
                        currEntity.getProps().add(createDecimalPropertyDefinition(prdProperty));
                        break;
                    case "float":
                        currEntity.getProps().add(createFloatPropertyDefinition(prdProperty));
                        break;
                    case "boolean":
                        currEntity.getProps().add(createBooleanPropertyDefinition(prdProperty));
                        break;
                    case "string":
                        currEntity.getProps().add(createStringPropertyDefinition(prdProperty));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + prdProperty.getType());
                }
            }
            String entityName = prdEntity.getName();
            entities.put(entityName, currEntity);
        }
        createdWorld.setEntities(entities);
    }
}

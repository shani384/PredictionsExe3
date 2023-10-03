package engine.world.design.definition.environment.impl;

import DTOManager.impl.PropertyDefinitionDTO;
import engine.world.design.definition.environment.api.EnvVariablesManager;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.environment.api.ActiveEnvironment;
import engine.world.design.execution.environment.impl.ActiveEnvironmentImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EnvVariablesManagerImpl implements EnvVariablesManager {

    private final Map<String, PropertyDefinition> propNameToPropDefinition;

    public EnvVariablesManagerImpl() {
        propNameToPropDefinition = new HashMap<>();
    }

    @Override
    public void addEnvironmentVariable(PropertyDefinition propertyDefinition) {
        propNameToPropDefinition.put(propertyDefinition.getName(), propertyDefinition);
    }

    @Override
    public ActiveEnvironment createActiveEnvironment() {
        return new ActiveEnvironmentImpl();
    }

    @Override
    public Map<String, PropertyDefinition> getEnvVariables() {
        return propNameToPropDefinition;
    }
    @Override
    public PropertyDefinition getEnvPropertyByName(String envProperty){
        for (PropertyDefinition propertyDefinition: getEnvVariables().values()){
            if (envProperty.equals(propertyDefinition.getName())){
                return propertyDefinition;
            }
        }
        throw new RuntimeException("Environment variable" + envProperty + "doesn't exist");
    }
}
package engine.world.design.definition.environment.api;

import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.environment.api.ActiveEnvironment;

import java.util.Collection;
import java.util.Map;

public interface EnvVariablesManager {
    void addEnvironmentVariable(PropertyDefinition propertyDefinition);
    ActiveEnvironment createActiveEnvironment();
    Map<String, PropertyDefinition> getEnvVariables();
    public PropertyDefinition getEnvPropertyByName(String envProperty);

}

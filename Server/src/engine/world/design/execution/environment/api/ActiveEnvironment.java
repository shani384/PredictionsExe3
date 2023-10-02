package engine.world.design.execution.environment.api;


import engine.world.design.execution.property.PropertyInstance;

public interface ActiveEnvironment {
    PropertyInstance getProperty(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
}
package engine.world.design.execution.context;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.environment.api.ActiveEnvironment;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.grid.api.Grid;

import java.util.Map;

public interface Context {
    EntityInstance getPrimaryEntityInstance();
    void removeEntity(EntityInstance entityInstance);
    PropertyInstance getEnvironmentVariable(String name);
    public EntityInstance getSecondaryEntity();
    public EntityInstanceManager getEntityInstanceManager();
    public Grid getGrid();
    public void setPrimaryEntityInstance(EntityInstance primaryEntityInstance);
    public void setSecondaryEntity(EntityInstance secondaryEntity);
    public ActiveEnvironment getActiveEnvironment();
    void createEntity(EntityDefinition createEntity, Map<String, PropertyInstance> createEntityProperties);
}

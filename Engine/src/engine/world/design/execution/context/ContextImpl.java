package engine.world.design.execution.context;

import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.environment.api.ActiveEnvironment;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.grid.api.Grid;

import java.util.Map;

public class ContextImpl implements Context {

    private EntityInstance primaryEntityInstance;
    private EntityInstance secondaryEntity;
    private final EntityInstanceManager entityInstanceManager;
    private final ActiveEnvironment activeEnvironment;
    private final Grid grid;

    public ContextImpl(EntityInstance primaryEntityInstance, EntityInstance secondaryEntity, EntityInstanceManager entityInstanceManager, ActiveEnvironment activeEnvironment, Grid grid) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.secondaryEntity = secondaryEntity;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
        this.grid = grid;
    }
    @Override
    public void setPrimaryEntityInstance(EntityInstance primaryEntityInstance) {
        this.primaryEntityInstance = primaryEntityInstance;
    }
    @Override
    public ActiveEnvironment getActiveEnvironment() {
        return activeEnvironment;
    }

    @Override
    public void createEntity(EntityDefinition createEntity, Map<String, PropertyInstance> createEntityProperties) {
        entityInstanceManager.createEntity(createEntity,createEntityProperties);
    }

    @Override
    public void setSecondaryEntity(EntityInstance secondaryEntity) {
        this.secondaryEntity = secondaryEntity;
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    @Override
    public EntityInstance getSecondaryEntity() {
        return secondaryEntity;
    }

    @Override
    public EntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }
    @Override
    public EntityInstanceManager getEntityInstanceManager() {
        return entityInstanceManager;
    }

    @Override
    public void removeEntity(EntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }

    @Override
    public PropertyInstance getEnvironmentVariable(String name) {
        return activeEnvironment.getProperty(name);
    }
}

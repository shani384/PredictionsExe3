package engine.world.design.execution.entity.manager;

import DTOManager.impl.EntityInstanceManagerDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.grid.api.Grid;

import java.util.List;
import java.util.Map;

public interface EntityInstanceManager {

    EntityInstanceManagerDTO createDTO();

    EntityInstance create(EntityDefinition entityDefinition);
    Map<Integer, EntityInstance> getInstances();
    void killEntities();
    void killEntity(int id);
    public List<Integer> getInstanceToKill();

    public void createEntities();
    void createEntity(EntityDefinition createEntity, Map<String, PropertyInstance> createEntityProperties);
}

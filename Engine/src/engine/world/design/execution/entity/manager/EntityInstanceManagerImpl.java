package engine.world.design.execution.entity.manager;

import DTOManager.impl.EntityInstanceDTO;
import DTOManager.impl.EntityInstanceManagerDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.entity.impl.EntityInstanceImpl;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.execution.property.PropertyInstanceImpl;
import engine.world.design.grid.api.Grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityInstanceManagerImpl implements EntityInstanceManager{

    private int count;
    private final Map<Integer, EntityInstance> instances;
    private List <Integer> instanceToKill = new ArrayList<>();
    private Map<EntityDefinition,Map<String,PropertyInstance>> instanceToCreate = new HashMap<>();
    private final Grid grid;
    public EntityInstanceManagerImpl(Grid grid) {
        this.grid = grid;
        count = 0;
        instances = new HashMap<>();
    }

    @Override
    public EntityInstanceManagerDTO createDTO() {
        Map<Integer, EntityInstanceDTO> instanceDTOMapToId = new HashMap<>();
        instances.forEach(((Id, entityInstance) -> instanceDTOMapToId.put(Id,entityInstance.createDTO())));
        return new EntityInstanceManagerDTO(instanceDTOMapToId);
    }
//    public EntityInstanceManager clone() throws CloneNotSupportedException {
//        EntityInstanceManager clone = (EntityInstanceManager) super.clone();
//        instances.forEach(((Id, entityInstance) -> clone.getInstances().put(Id,entityInstance.clone());
//        instanceToKill.forEach(((index) -> clone.getInstanceToKill().add(index)));
//        return clone;
//    }

    @Override
    public EntityInstance create(EntityDefinition entityDefinition) {
        count++;
        EntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition, count);
        instances.put(count,newEntityInstance);
        for (PropertyDefinition propertyDefinition : entityDefinition.getProps()) {
            Object value = propertyDefinition.generateValue();
            PropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition, value);
            newEntityInstance.addPropertyInstance(newPropertyInstance);
        }
        grid.initEntityPlace(newEntityInstance);
        return newEntityInstance;
    }

    @Override
    public Map<Integer, EntityInstance> getInstances() {
        return instances;
    }

    private void addEntityToDieList(int id) {
        instanceToKill.add(id);
    }

    @Override
    public void killEntities() {
        instanceToKill.forEach((id) -> {
            grid.getFreeCells().add(instances.get(id).getCoordinate());
            instances.remove(id);
        });
        instanceToKill = new ArrayList<>();
    }
    @Override
    public void createEntities() {
        for (Map.Entry<EntityDefinition,Map<String,PropertyInstance>> entry: instanceToCreate.entrySet()) {
            EntityDefinition key = entry.getKey();
            Map<String,PropertyInstance> value = entry.getValue();
            EntityInstance entityInstance= create(key);
            if (value != null){ //create entity - derived mode
                entityInstance.setProperties(value);
            }
        }
        instanceToCreate = new HashMap<>();
    }
    @Override
    public void killEntity(int id) {
        if(instances.containsKey(id)) {
            addEntityToDieList(id);
        }
        else {
            throw new IllegalArgumentException("this instance is already dead");
        }
    }
    @Override
    public List<Integer> getInstanceToKill() {
        return instanceToKill;
    }

    @Override
    public void createEntity(EntityDefinition createEntity, Map<String, PropertyInstance> createEntityProperties) {
            instanceToCreate.put(createEntity,createEntityProperties);
    }
}
package engine.world.design.execution.entity.impl;

import DTOManager.impl.EntityInstanceDTO;
import DTOManager.impl.PropertyInstanceDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.grid.cell.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class EntityInstanceImpl implements EntityInstance{

    private final EntityDefinition entityDefinition;

    private final int id;
    private Map<String, PropertyInstance> properties;
    private Coordinate coordinate;

    public EntityInstanceImpl(EntityDefinition entityDefinition, int id) {
        this.entityDefinition = entityDefinition;
        this.id = id;
        properties = new HashMap<>();
    }
    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }
    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setProperties(Map<String, PropertyInstance> properties) {
        this.properties = properties;
    }

    @Override
    public PropertyInstance getPropertyByName(String name) {
        if (!properties.containsKey(name)) {
            throw new IllegalArgumentException("for entity of type " + entityDefinition.getName() + " has no property named " + name);
        }

        return properties.get(name);
    }

    @Override
    public void addPropertyInstance(PropertyInstance propertyInstance) {
        properties.put(propertyInstance.getPropertyDefinition().getName(), propertyInstance);
    }

    @Override
    public EntityInstanceDTO createDTO() {
        Map<String, PropertyInstanceDTO> propertyInstanceToNameDTO = new HashMap<>();
        properties.forEach((name, propertyInstance) -> propertyInstanceToNameDTO.put(name,propertyInstance.createDTO()));
        return new EntityInstanceDTO(entityDefinition.createEntityDefinitionDTO(), id,propertyInstanceToNameDTO);
    }
//    @Override
//    public EntityInstance clone() throws CloneNotSupportedException {
//        EntityInstance clone = (EntityInstance) super.clone();
//        clone.getEntityDefinition() = entityDefinition.clone();
//        properties.forEach((name,property)-> clone.getProperties().put(name,property.clone()));
//    }
    @Override
    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }
    @Override
    public Map<String, PropertyInstance> getProperties() {
        return properties;
    }
}

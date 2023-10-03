package engine.world.design.execution.entity.api;

import DTOManager.impl.EntityInstanceDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.grid.cell.Coordinate;

import java.util.Map;

public interface EntityInstance {
    int getId();
    PropertyInstance getPropertyByName(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
    EntityInstanceDTO createDTO();
    public EntityDefinition getEntityDefinition();
    public Map<String, PropertyInstance> getProperties();
    public Coordinate getCoordinate();
    public void setCoordinate(Coordinate coordinate);
    public void setProperties(Map<String, PropertyInstance> properties);
}

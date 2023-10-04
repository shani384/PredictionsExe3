package engine.world.design.definition.entity.api;

import DTOManager.impl.EntityDefinitionDTO;
import engine.world.design.definition.property.api.PropertyDefinition;

import java.util.List;

public interface EntityDefinition extends Cloneable{
    String getName();
    int getPopulation();
    List<PropertyDefinition> getProps();
    EntityDefinitionDTO createEntityDefinitionDTO();
    public PropertyDefinition getPropertyByName(String name);

    void setPopulation(int i);
}

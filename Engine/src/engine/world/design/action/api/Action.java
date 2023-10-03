package engine.world.design.action.api;

import DTOManager.impl.actionDTO.ActionDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.property.PropertyInstance;

import java.util.ArrayList;

public interface Action {
    void invoke(Context context);
    ActionType getActionType();
    EntityDefinition getContextEntity();
    boolean verifyNumericPropertyType(PropertyInstance propertyValue);

    ActionDTO createActionDTO();
    public EntityDefinition getMainEntity();
    public ArrayList<EntityInstance> getSecondaryInstances(Context context);
    public InteractiveEntity getInteractiveEntity();
}

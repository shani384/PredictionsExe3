package engine.world.design.action.condition;

import DTOManager.impl.actionDTO.ConditionDTO;
import engine.world.design.action.api.Action;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;

public interface Condition {

    boolean evaluate(Context context);

    ConditionDTO createConditionDTO();

}

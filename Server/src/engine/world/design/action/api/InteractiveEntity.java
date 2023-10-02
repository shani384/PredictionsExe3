package engine.world.design.action.api;

import engine.world.design.action.condition.Condition;
import engine.world.design.definition.entity.api.EntityDefinition;

public class InteractiveEntity {

    private final EntityDefinition secondaryEntity;
    private final int count;
    private final Condition conditionAction;

    public InteractiveEntity(EntityDefinition secondaryEntity, int count, Condition conditionAction) {
        this.secondaryEntity = secondaryEntity;
        this.count = count;
        this.conditionAction = conditionAction;
    }
    public EntityDefinition getSecondaryEntity() {
        return secondaryEntity;
    }

    public int getCount() {
        return count;
    }
    public Condition getConditionAction() {
        return conditionAction;
    }
}

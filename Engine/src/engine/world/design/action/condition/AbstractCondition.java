package engine.world.design.action.condition;

import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCondition extends AbstractAction implements Condition {

    private final List<Action> thanActions;
    private final List<Action> elseActions;

    protected AbstractCondition(ActionType actionType, EntityDefinition entityDefinition, InteractiveEntity interactiveEntity) {// TODO: 15/08/2023
        super(actionType, entityDefinition, interactiveEntity);
        thanActions = new ArrayList<>();
        elseActions = new ArrayList<>();
    }

    public List<Action> getThanActions() {
        return thanActions;
    }

    public List<Action> getElseActions() {
        return elseActions;
    }
    @Override
    public void invoke(Context context) {
        if(evaluate(context)){
            for (Action action: thanActions){ // TODO: 15/08/2023
                action.invoke(context);
            }
        }
        else{
            for (Action action: elseActions){
                action.invoke(context);
            }
        }
    }

    public static class ConditionAction {
    }
}

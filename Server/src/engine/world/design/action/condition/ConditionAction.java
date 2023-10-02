package engine.world.design.action.condition;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.ConditionActionDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.context.ContextImpl;
import engine.world.design.execution.entity.api.EntityInstance;

import java.util.ArrayList;
import java.util.List;

public class ConditionAction extends AbstractAction{
    private final List<Action> thenActions;
    private final List<Action> elseActions;
    private final Condition condition;
    public ConditionAction(EntityDefinition entityDefinition, InteractiveEntity interactiveEntity, Condition condition) {
        super(ActionType.CONDITION, entityDefinition, interactiveEntity);
        this.condition = condition;
        thenActions = new ArrayList<>();
        elseActions = new ArrayList<>();
    }

    public List<Action> getThenActions() {
        return thenActions;
    }

    public List<Action> getElseActions() {
        return elseActions;
    }
    @Override
    public void invoke(Context context) {
        if (condition.evaluate(context)) {
            for (Action action:thenActions){
                if (action.getMainEntity().getName().equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())){
                    action.invoke(context);
                }
                else if(action.getMainEntity().getName().equals(context.getSecondaryEntity().getEntityDefinition().getName())){
                    Context newContext = new ContextImpl(context.getSecondaryEntity(), context.getPrimaryEntityInstance(), context.getEntityInstanceManager(), context.getActiveEnvironment(), context.getGrid());
                    action.invoke(newContext);
                }
            }
        } else {
            for (Action action:elseActions){
                if (action.getMainEntity().getName().equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())){
                    action.invoke(context);
                }
                else if(action.getMainEntity().getName().equals(context.getSecondaryEntity().getEntityDefinition().getName())){
                    Context newContext = new ContextImpl(context.getSecondaryEntity(), context.getPrimaryEntityInstance(), context.getEntityInstanceManager(), context.getActiveEnvironment(), context.getGrid());
                    action.invoke(newContext);
                }
            }
        }
    }
    @Override
    public ActionDTO createActionDTO() {
        return new ConditionActionDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO(),condition.createConditionDTO(), thenActions.size(), elseActions.size());
    }
}

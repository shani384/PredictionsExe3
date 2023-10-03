package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.ProximityDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.Action;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.context.ContextImpl;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.expression.ExpressionType;

import java.util.ArrayList;

public class ProximityAction extends AbstractAction {

    private final String ofExpression;
    private final int columns;
    private final int rows;
    private final ArrayList<Action> actions;
    private final EntityDefinition targetEntity;

    public ProximityAction(EntityDefinition entityDefinition, InteractiveEntity interactiveEntity, String ofExpression, int columns, int rows, ArrayList<Action> actions, EntityDefinition targetEntity) {
        super(ActionType.PROXIMITY, entityDefinition, interactiveEntity);
        this.ofExpression = ofExpression;
        this.columns = columns;
        this.rows = rows;
        this.actions = actions;
        this.targetEntity = targetEntity;
    }

    @Override
    public void invoke(Context context) {
        EntityInstance targetEntity = isClose(context);
        if(targetEntity != null){
            for (Action action: actions){
                if (action.getMainEntity().getName().equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())){
                    Context newContext = new ContextImpl(context.getPrimaryEntityInstance(), context.getPrimaryEntityInstance(), context.getEntityInstanceManager(), context.getActiveEnvironment(), context.getGrid());
                    newContext.setSecondaryEntity(targetEntity);
                    action.invoke(context);
                }
                else if(action.getMainEntity().getName().equals(targetEntity.getEntityDefinition().getName())){
                    Context newContext = new ContextImpl(targetEntity, context.getPrimaryEntityInstance(), context.getEntityInstanceManager(), context.getActiveEnvironment(), context.getGrid());
                    newContext.setPrimaryEntityInstance(targetEntity);
                    action.invoke(newContext);
                }
            }
        }
    }

    @Override
    public ActionDTO createActionDTO() {
        ArrayList<ActionDTO> actionsDTO = new ArrayList<>();
        actions.forEach(action -> actionsDTO.add(action.createActionDTO()));
        return new ProximityDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO(),ofExpression,columns,rows,actionsDTO, targetEntity.createEntityDefinitionDTO());
    }

    private EntityInstance isClose(Context context){
        EntityInstance sourceEntity = context.getPrimaryEntityInstance();
        int depth = ExpressionType.DECIMAL.evaluate(ofExpression,context);
        int x = sourceEntity.getCoordinate().getX();
        int y = sourceEntity.getCoordinate().getY();
        int xRight = (x + depth) % columns;
        int xLeft = (x - depth) % columns;
        int yUp = (y + depth) % rows;
        int yDown = (y - depth) % rows;
        for (EntityInstance entityInstance:context.getEntityInstanceManager().getInstances().values()){
            if (entityInstance.getEntityDefinition().getName().equals(targetEntity.getName())) {
                int xTarget = entityInstance.getCoordinate().getX();
                int yTarget = entityInstance.getCoordinate().getY();
                if (xTarget >= xLeft && xTarget <= xRight) {
                    if (yTarget <= yUp && yTarget >= yDown) {
                        return entityInstance;
                    }
                }
            }
        }
        return null;
    }
}

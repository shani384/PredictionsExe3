package engine.world.design.action.api;

import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.context.ContextImpl;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.execution.entity.manager.EntityInstanceManager;
import engine.world.design.execution.property.PropertyInstance;

import java.util.ArrayList;

public abstract class AbstractAction implements Action {

    private final ActionType actionType;
    private final EntityDefinition mainEntity;
    private final InteractiveEntity interactiveEntity;

    protected AbstractAction(ActionType actionType, EntityDefinition entityDefinition, InteractiveEntity interactiveEntity) {
        this.actionType = actionType;
        this.mainEntity = entityDefinition;
        this.interactiveEntity = interactiveEntity;
    }
    @Override
    public InteractiveEntity getInteractiveEntity() {
        return interactiveEntity;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getContextEntity() {
        return mainEntity;
    }

    @Override
    public boolean verifyNumericPropertyType(PropertyInstance propertyValue) {
        return
                PropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType()) || PropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }
    @Override
    public ArrayList<EntityInstance> getSecondaryInstances(Context context){
        ArrayList<EntityInstance> secondaryInstances = new ArrayList<>();
        int count = getInteractiveEntity().getCount();
        if(getInteractiveEntity().getConditionAction() == null){
            if(count > interactiveEntity.getSecondaryEntity().getPopulation()){
                count = interactiveEntity.getSecondaryEntity().getPopulation();
            }
            for (int i=0; i<count; i++){
               for (EntityInstance entityInstance: context.getEntityInstanceManager().getInstances().values()){
                   if ((entityInstance.getEntityDefinition().getName().equals(interactiveEntity.getSecondaryEntity().getName()))){
                       secondaryInstances.add(entityInstance);
                       break;
                   }
               }
            }
        }else{
            for (int i=0; i<count; i++){
                for (EntityInstance entityInstance: context.getEntityInstanceManager().getInstances().values()){
                    if ((entityInstance.getEntityDefinition().getName().equals(interactiveEntity.getSecondaryEntity().getName()))){
                        context.setPrimaryEntityInstance(entityInstance);
                        if (interactiveEntity.getConditionAction().evaluate(context)){
                            secondaryInstances.add(entityInstance);
                            break;
                        }
                    }
                }
            }

        }
        return secondaryInstances;
    }

    @Override
    public EntityDefinition getMainEntity() {
        return mainEntity;
    }
}
package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.ReplaceDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.execution.property.PropertyInstanceImpl;

import java.util.HashMap;
import java.util.Map;

public class ReplaceAction extends AbstractAction {
    private final String mode;
    private final EntityDefinition createEntity;

    public ReplaceAction(EntityDefinition entityDefinition, InteractiveEntity interactiveEntity, String mode, EntityDefinition createEntity) {
        super(ActionType.REPLACE, entityDefinition, interactiveEntity);
        this.mode = mode;
        this.createEntity = createEntity;
    }
    @Override
    public void invoke(Context context) {
        if (mode.equals("scratch")){
            context.removeEntity(context.getPrimaryEntityInstance());
            context.createEntity(createEntity,null);
        } else if (mode.equals("derived")) {
            Map<String,PropertyInstance> createEntityProperties = new HashMap<>();
            for (PropertyDefinition propertyDefinition:createEntity.getProps()){
                if(context.getPrimaryEntityInstance().getProperties().containsKey(propertyDefinition.getName())){
                    Object value = context.getPrimaryEntityInstance().getProperties().get(propertyDefinition.getName()).getValue();
                    createEntityProperties.put(propertyDefinition.getName(), new PropertyInstanceImpl(propertyDefinition,value));
                }
                else{
                    Object value = propertyDefinition.generateValue();
                    createEntityProperties.put(propertyDefinition.getName(),new PropertyInstanceImpl(propertyDefinition,value));
                }
            }
            context.createEntity(createEntity,createEntityProperties);
            context.removeEntity(context.getPrimaryEntityInstance());
        }
        else {
            throw new RuntimeException("Not a valid mode in the replace action");
        }
    }
    @Override
    public ActionDTO createActionDTO() {
        return new ReplaceDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO(),mode, createEntity.createEntityDefinitionDTO());
    }
}

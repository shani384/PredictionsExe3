package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.SetDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.expression.Expression;
import engine.world.design.expression.ExpressionType;

public class SetAction extends AbstractAction {

    private final String property;
    private final String value;

    public SetAction(EntityDefinition entityDefinition, InteractiveEntity interactiveEntity, String property, String value) {
        super(ActionType.SET, entityDefinition, interactiveEntity);
        this.property = property;
        this.value = value;
    }

    @Override
    public void invoke(Context context) {
        Expression expression = new Expression();
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        Object type = propertyInstance.getPropertyDefinition().getType();
        if(PropertyType.DECIMAL.equals(type)){
            int result = ExpressionType.DECIMAL.evaluate(value, context);
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.FLOAT.equals(type)){
            float result = ExpressionType.FLOAT.evaluate(value, context);
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.STRING.equals(type)){
            String result = ExpressionType.STRING.evaluate(value, context);
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.BOOLEAN.equals(type)) {
            boolean result = ExpressionType.BOOLEAN.evaluate(value, context);
            propertyInstance.updateValue(result);
        }

    }

    @Override
    public ActionDTO createActionDTO() {
        return new SetDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO(),property,value);
    }
}

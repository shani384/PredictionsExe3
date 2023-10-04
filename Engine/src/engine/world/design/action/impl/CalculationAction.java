package engine.world.design.action.impl;

import DTOManager.impl.actionDTO.ActionDTO;
import DTOManager.impl.actionDTO.CalculationDTO;
import engine.world.design.action.api.AbstractAction;
import engine.world.design.action.api.ActionType;
import engine.world.design.action.api.InteractiveEntity;
import engine.world.design.action.calculation.CalculationType;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.expression.Expression;
import engine.world.design.expression.ExpressionType;

public class CalculationAction extends AbstractAction {

    private final CalculationType calculationType;
    private final String property;
    private final String arg1;
    private final String arg2;


    public CalculationAction(EntityDefinition entityDefinition, InteractiveEntity interactiveEntity, String property, String arg1, String arg2, CalculationType calculationType) {
        super(ActionType.CALCULATION, entityDefinition, interactiveEntity);
        this.property = property;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.calculationType = calculationType;
    }

    @Override
    public void invoke(Context context) {
        Expression expression = new Expression();
        PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(property);
        if (!verifyNumericPropertyType(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + property);
        }
        if(PropertyType.DECIMAL.equals(propertyInstance.getPropertyDefinition().getType()))
        {
            int result = 0;
//            Object num1Obj = expression.evaluate(arg1,context);
//            Object num2Obj = expression.evaluate(arg2,context);
//            int num1 = PropertyType.DECIMAL.convert(num1Obj);
//            int num2 = PropertyType.DECIMAL.convert(num2Obj);

            int num1 = ExpressionType.DECIMAL.evaluate(arg1,context);
            int num2 = ExpressionType.DECIMAL.evaluate(arg2,context);
            if (CalculationType.MULTIPLY.equals(calculationType)){
                result = num1*num2;
            }
            else if (CalculationType.DIVIDE.equals(calculationType)){
                result = num1/num2;
            }
            propertyInstance.updateValue(result);
        }
        else if(PropertyType.FLOAT.equals(propertyInstance.getPropertyDefinition().getType()))
        {
            float result = 0;
//            Object num1Obj = expression.evaluate(arg1,context);
//            Object num2Obj = expression.evaluate(arg2,context);
//            float num1 = PropertyType.FLOAT.convert(num1Obj);
//            float num2 = PropertyType.FLOAT.convert(num2Obj);

            float num1 = ExpressionType.FLOAT.evaluate(arg1,context);
            float num2 = ExpressionType.FLOAT.evaluate(arg2,context);
            if (CalculationType.MULTIPLY.equals(calculationType)){
                result = num1*num2;
            }
            else if (CalculationType.DIVIDE.equals(calculationType)){
                result = num1/num2;
            }
            propertyInstance.updateValue(result);
        }
    }

    @Override
    public ActionDTO createActionDTO() {
        return new CalculationDTO(getActionType().name(),getMainEntity().createEntityDefinitionDTO(),calculationType.name(),property,arg1,arg2);
    }
}

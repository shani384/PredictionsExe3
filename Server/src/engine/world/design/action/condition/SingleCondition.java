package engine.world.design.action.condition;

import DTOManager.impl.actionDTO.SingleConditionDTO;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;
import engine.world.design.expression.Expression;
import engine.world.design.expression.ExpressionType;

public class SingleCondition implements Condition{
    private final String property;
    private final EntityDefinition entity;
    private final String operator;
    private final String value;

    public SingleCondition(EntityDefinition entity, String property, String value, String operator) {// TODO: 15/08/2023
        this.property = property;
        this.value = value;
        this.entity = entity;
        this.operator = operator;
    }

    @Override
    public boolean evaluate(Context context) {
        Expression expression = new Expression();
        Object propertyExpression = expression.evaluate(property,context);
        switch (operator){
            case ("="):{
                return isEqual(propertyExpression,value, context);
            }
            case("!="):{
                return notEqual(propertyExpression,value, context);
            }
            case("bt"):{
                return bt(propertyExpression,value, context);
            }
            case ("lt"):{
                return lt(propertyExpression,value, context);
            }
            default:{
                throw new RuntimeException("Invalid operator");
            }
        }
    }

    private boolean isEqual(Object obj1, String value, Context context){
        if (obj1 instanceof Integer){
            int num1 = (int) obj1;
            int num2 = ExpressionType.DECIMAL.evaluate(value, context);
            return num1 == num2;
        }else if(obj1 instanceof Float){
            float num1 = (float) obj1;
            float num2 = ExpressionType.FLOAT.evaluate(value, context);
            return num1 == num2;
        }else if(obj1 instanceof Boolean ){
            boolean num1 = (boolean) obj1;
            boolean num2 = ExpressionType.BOOLEAN.evaluate(value, context);
            return num1 == num2;
        }else{
            String num1 = (String) obj1;
            boolean num2 = ExpressionType.STRING.evaluate(value, context);
            return num1.equals(num2);
        }
    }
    private boolean notEqual(Object obj1, String value, Context context){
        return !isEqual(obj1,value,context);
    }
    private boolean bt(Object obj1,String  value, Context context) {
        if (obj1 instanceof Integer ){
            int num1 = (int) obj1;
            int num2 = ExpressionType.DECIMAL.evaluate(value, context);
            return num1 > num2;
        }else if(obj1 instanceof Float ){
            float num1 = (float) obj1;
            float num2 = ExpressionType.FLOAT.evaluate(value, context);
            return num1 > num2;
        }else {
            throw new RuntimeException("Bt can't be done on non numeric values");
        }
    }
    private boolean lt(Object obj1,String  value, Context context){
        if (obj1 instanceof Integer){
            int num1 = (int) obj1;
            int num2 = ExpressionType.DECIMAL.evaluate(value, context);
            return num1 < num2;
        }else if(obj1 instanceof Float){
            float num1 = (float) obj1;
            float num2 = ExpressionType.FLOAT.evaluate(value, context);
            return num1 < num2;
        }else {
            throw new RuntimeException("Bt can't be done on non numeric values");
        }
    }

    public boolean verifyNumericPropertyType(PropertyInstance propertyValue) {
        return
                PropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType()) || PropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }
    @Override
    public SingleConditionDTO createConditionDTO(){
        return new SingleConditionDTO(property,entity.createEntityDefinitionDTO(),operator,value);
    }
}

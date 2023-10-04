package engine.world.design.expression;

import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;

import java.util.Random;

public class Expression {
    public Object evaluate(String expression, Context context) {
        int openingParen = expression.indexOf("(");
        int closingParen = expression.indexOf(")");
        if(openingParen != -1 && closingParen != -1) {
            String envFunc = expression.substring(0,openingParen);
            String envFuncArg = expression.substring(openingParen + 1,closingParen);
            switch (envFunc) {
                case ("environment"): {
                    return context.getEnvironmentVariable(envFuncArg).getValue();
                }
                case ("random"): {
                    Object randomVal = evaluate(envFuncArg,context);
                    int value = PropertyType.DECIMAL.convert(randomVal);
                    Random random = new Random();
                    return random.nextInt(value + 1);
                }
                case("evaluate"):{
                    int dot = envFuncArg.indexOf(".");
                    String entity = envFuncArg.substring(0,dot);
                    String propertyName = envFuncArg.substring(dot + 1);
                    if(entity.equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())){
                        return context.getPrimaryEntityInstance().getPropertyByName(propertyName).getValue();
                    }else if(entity.equals(context.getSecondaryEntity().getEntityDefinition().getName())){
                        return context.getSecondaryEntity().getPropertyByName(propertyName).getValue();
                    }
                }
                case ("percent"):{
                    int comma = envFuncArg.indexOf(",");
                    String numberExpression = envFuncArg.substring(0,comma);
                    Object numberObj = evaluate(numberExpression,context);
                    int number = PropertyType.DECIMAL.convert(numberObj);
                    String percentExpression = envFuncArg.substring(comma);
                    Object percentObj = evaluate(percentExpression,context);
                    int percent = PropertyType.DECIMAL.convert(percentObj);
                    return (number * percent) / 100;
                }
                case ("ticks"):{
                    int dot = envFuncArg.indexOf(".");
                    String entity = envFuncArg.substring(0,dot);
                    String propertyName = envFuncArg.substring(dot + 1);
                    if(entity.equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())){
                        return context.getPrimaryEntityInstance().getPropertyByName(propertyName).getTicksSameValue();
                    }else if(entity.equals(context.getSecondaryEntity().getEntityDefinition().getName())){
                        return context.getSecondaryEntity().getPropertyByName(propertyName).getTicksSameValue();
                    }
                }
                default:
                    throw new RuntimeException("There is no such environment function");
            }
        }
        try {
            PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(expression);
            return propertyInstance.getValue();
        }
        catch (IllegalArgumentException e) {
            return expression;
        }
    }
}

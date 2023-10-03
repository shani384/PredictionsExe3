package engine.world.design.expression;

import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.definition.property.api.PropertyDefinition;
import engine.world.design.definition.property.api.PropertyType;
import engine.world.design.definition.value.generator.random.impl.numeric.RandomIntegerGenerator;
import engine.world.design.execution.context.Context;
import engine.world.design.execution.property.PropertyInstance;

import java.util.Random;

public enum ExpressionType {

    FLOAT {
        @Override
        public Float evaluate(String expression, Context context) {
            int openingParen = expression.indexOf("(");
            int closingParen = -1;
            if(openingParen != -1) {
                closingParen = expression.length() - 1;
            }
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        float envPropertyVal = PropertyType.FLOAT.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        int randomVal = ExpressionType.DECIMAL.evaluate(envFuncArg,context);
                        Random random = new Random();
                        int res = random.nextInt(randomVal + 1);
                        return (float) res;
                    }
                    case("evaluate"):{
                        int dot = envFuncArg.indexOf(".");
                        String entity = envFuncArg.substring(0,dot);
                        String propertyName = envFuncArg.substring(dot + 1);
                        if(entity.equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())){
                            return (float) context.getPrimaryEntityInstance().getPropertyByName(propertyName).getValue();
                        }else if(entity.equals(context.getSecondaryEntity().getEntityDefinition().getName())){
                            return (float) context.getSecondaryEntity().getPropertyByName(propertyName).getValue();
                        }
                    }
                    case ("percent"):{
                        int comma = envFuncArg.indexOf(",");
                        String numberExpression = envFuncArg.substring(0,comma);
                        int number = ExpressionType.DECIMAL.evaluate(numberExpression,context);
                        String percentExpression = envFuncArg.substring(comma);
                        int percent = ExpressionType.DECIMAL.evaluate(percentExpression,context);
                        return (float)(number * percent) / 100;
                    }
                    case ("ticks"):{
                        int dot = envFuncArg.indexOf(".");
                        String entity = envFuncArg.substring(0,dot);
                        String propertyName = envFuncArg.substring(dot + 1);
                        if(entity.equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())){
                            return (float) context.getPrimaryEntityInstance().getPropertyByName(propertyName).getTicksSameValue();
                        }else if(entity.equals(context.getSecondaryEntity().getEntityDefinition().getName())){
                            return (float) context.getSecondaryEntity().getPropertyByName(propertyName).getTicksSameValue();
                        }
                    }
                    default:
                        throw new RuntimeException("There is no such environment function");
                }
            }
            try {
                PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(expression); // TODO: 19/08/2023 throw
                return PropertyType.FLOAT.convert(propertyInstance.getValue());
            }
            catch (IllegalArgumentException e) {
                try {
                    float freeVal = Float.parseFloat(expression);
                    return freeVal;
                } catch (NumberFormatException e2) {
                    throw e2;
                }
            }
        }
    },
    DECIMAL{
        @Override
        public Integer evaluate(String expression, Context context) {
            int defaultValue = 3;
            int openingParen = expression.indexOf("(");
            int closingParen = -1;
            if(openingParen != -1) {
                closingParen = expression.length() - 1;
            }
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        int envPropertyVal = PropertyType.DECIMAL.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        int randomVal = ExpressionType.DECIMAL.evaluate(envFuncArg,context);
                        Random random = new Random();
                        int res = random.nextInt(randomVal + 1);
                        return res;
                    }
                    case("evaluate"):{
                        try {
                            int dot = envFuncArg.indexOf(".");
                            String entity = envFuncArg.substring(0, dot);
                            String propertyName = envFuncArg.substring(dot + 1);
                            if (entity.equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())) {
                                return (int) context.getPrimaryEntityInstance().getPropertyByName(propertyName).getValue();
                            } else if (entity.equals(context.getSecondaryEntity().getEntityDefinition().getName())) {
                                return (int) context.getSecondaryEntity().getPropertyByName(propertyName).getValue();
                            }
                        }catch (Exception e){
                            return defaultValue;
                        }
                    }
                    case ("percent"):{
                        int comma = envFuncArg.indexOf(",");
                        String numberExpression = envFuncArg.substring(0,comma);
                        int number = ExpressionType.DECIMAL.evaluate(numberExpression,context);
                        String percentExpression = envFuncArg.substring(comma);
                        int percent = ExpressionType.DECIMAL.evaluate(percentExpression,context);
                        return (int)(number * percent) / 100;
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
                        return defaultValue;
                        //throw new RuntimeException("There is no such environment function");
                }
            }
            try {
                PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(expression); // TODO: 19/08/2023 throw
                return PropertyType.DECIMAL.convert(propertyInstance.getValue());
            }
            catch (IllegalArgumentException e) {
                try {
                    int freeVal = Integer.parseInt(expression);
                    return freeVal;
                } catch (NumberFormatException e2) {
                    throw e2;
                }
            }
        }
    }, 
    STRING{
        @Override
        public String evaluate(String expression, Context context) {// TODO: 15/08/2023
            int openingParen = expression.indexOf("(");
            int closingParen = -1;
            if(openingParen != -1) {
                closingParen = expression.length() - 1;
            }
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        String envPropertyVal = PropertyType.STRING.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        throw new RuntimeException("This random function can't random String value");
                    }
                    case("evaluate"):{
                        int dot = envFuncArg.indexOf(".");
                        String entity = envFuncArg.substring(0,dot);
                        String propertyName = envFuncArg.substring(dot + 1);
                        if(entity.equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())){
                            return (String) context.getPrimaryEntityInstance().getPropertyByName(propertyName).getValue();
                        }else if(entity.equals(context.getSecondaryEntity().getEntityDefinition().getName())){
                            return (String) context.getSecondaryEntity().getPropertyByName(propertyName).getValue();
                        }
                    }
                    case ("percent"):{
                        throw new RuntimeException("This percent function can't return String value");
                    }
                    case ("ticks"):{
                        throw new RuntimeException("This ticks function can't return String value");
                    }
                    default:
                        throw new RuntimeException("There is no such environment function");
                }
            }
            try {
                PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(expression); // TODO: 19/08/2023 throw
                return PropertyType.STRING.convert(propertyInstance.getValue());
            }
            catch (IllegalArgumentException e) {
                return expression;
            }
        }
    },
    BOOLEAN{
        @Override
        public Boolean evaluate(String expression, Context context) { // TODO: 15/08/2023
            int openingParen = expression.indexOf("(");
            int closingParen = -1;
            if(openingParen != -1) {
                closingParen = expression.length() - 1;
            }
            if(openingParen != -1 && closingParen != -1) {
                String envFunc = expression.substring(0,openingParen);
                String envFuncArg = expression.substring(openingParen + 1,closingParen);
                switch (envFunc) {
                    case ("environment"): {
                        Boolean envPropertyVal = PropertyType.BOOLEAN.convert(context.getEnvironmentVariable(envFuncArg).getValue());
                        return envPropertyVal;
                    }
                    case ("random"): {
                        throw new RuntimeException("This random function can't random boolean value");
                    }
                    case("evaluate"):{
                        int dot = envFuncArg.indexOf(".");
                        String entity = envFuncArg.substring(0,dot);
                        String propertyName = envFuncArg.substring(dot + 1);
                        if(entity.equals(context.getPrimaryEntityInstance().getEntityDefinition().getName())){
                            return (boolean) context.getPrimaryEntityInstance().getPropertyByName(propertyName).getValue();
                        }else if(entity.equals(context.getSecondaryEntity().getEntityDefinition().getName())){
                            return (boolean) context.getSecondaryEntity().getPropertyByName(propertyName).getValue();
                        }
                    }
                    case ("percent"):{
                        throw new RuntimeException("This percent function can't return boolean value");
                    }
                    case ("ticks"):{
                        throw new RuntimeException("This ticks function can't return boolean value");
                    }
                    default:
                        throw new RuntimeException("There is no such environment function");
                }
            }
            try {
                PropertyInstance propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(expression);
                return PropertyType.BOOLEAN.convert(propertyInstance.getValue());
            }
            catch (IllegalArgumentException e){
                try{
                    boolean freeVal = Boolean.parseBoolean(expression);
                    return freeVal;
                }
                catch (NumberFormatException e2) {
                    throw e2;
                }
            }
        }
    };
    public abstract <T> T evaluate(String expression, Context context);
}

package engine.world.design.action.condition;

import engine.world.design.action.api.Action;
import engine.world.design.execution.property.PropertyInstance;

public enum Operator {
    EQUAL{
        @Override
        public boolean runOperator(PropertyInstance propertyInstance, String value) {
            return propertyInstance.getValue() == propertyInstance.getPropertyDefinition().getType().convert(value);
        }
        @Override
        public boolean runOperator1(Object val1, Object val2) {
            return (val1 == val2);
        }
    },DIFFERENT{
        @Override
        public boolean runOperator(PropertyInstance propertyInstance, String value) {
            return propertyInstance.getValue() != propertyInstance.getPropertyDefinition().getType().convert(value);
        }

        @Override
        public boolean runOperator1(Object val1, Object val2) {
            return false;
        }
    },BT{
        @Override
        public boolean runOperator(PropertyInstance propertyInstance, String value) {
            return (float) propertyInstance.getValue() > (float) propertyInstance.getPropertyDefinition().getType().convert(value);
        }

        @Override
        public boolean runOperator1(Object val1, Object val2) {
            return false;
        }
    }, LT{
        @Override
        public boolean runOperator(PropertyInstance propertyInstance, String value) {
            return (float) propertyInstance.getValue() < (float) propertyInstance.getPropertyDefinition().getType().convert(value);
        }

        @Override
        public boolean runOperator1(Object val1, Object val2) {
            return false;
        }
    };

    public abstract boolean runOperator(PropertyInstance propertyInstance,String value);
    public abstract boolean runOperator1(Object val1, Object val2);
}

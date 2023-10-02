package engine.world.design.execution.property;

import DTOManager.impl.PropertyInstanceDTO;
import engine.world.design.definition.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class PropertyInstanceImpl implements PropertyInstance {

    private final PropertyDefinition propertyDefinition;
    private Object value;
    private Object oldValue;
    private int ticksSameValue = 0;
    private ArrayList<Integer> sameValueCounts = new ArrayList<>();

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition, Object value) {
        this.propertyDefinition = propertyDefinition;
        this.value = value;
        oldValue = value;
    }

    public ArrayList<Integer> getSameValueCounts() {
        return sameValueCounts;
    }

    @Override
    public PropertyDefinition getPropertyDefinition() {
        return propertyDefinition;
    }

    @Override
    public Object getValue() {
        return value;
    }
    @Override
    public Object getOldValue() {
        return oldValue;
    }
    @Override
    public int getTicksSameValue() {
        return ticksSameValue;
    }
    @Override
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }
    @Override
    public void setTicksSameValue(int ticksSameValue) {
        this.ticksSameValue = ticksSameValue;
    }

    @Override
    public void updateValue(Object val) {
        this.value = propertyDefinition.getType().convert(val);
    }

    @Override
    public PropertyInstanceDTO createDTO() {
        ArrayList<Integer> sameValueCountsDTO = new ArrayList<>();
        for (int sameTicks: sameValueCounts){
            sameValueCountsDTO.add(sameTicks);
        }
        return new PropertyInstanceDTO(propertyDefinition.createPropertyDefinitionDTO(), value, sameValueCountsDTO);
    }
//    @Override
//    public void setValue(Object value) {
//        this.value = value;
//    }
}
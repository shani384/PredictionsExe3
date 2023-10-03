package engine.world.design.execution.property;

import DTOManager.impl.PropertyInstanceDTO;
import engine.world.design.definition.property.api.PropertyDefinition;

import java.util.List;

public interface PropertyInstance {
    PropertyDefinition getPropertyDefinition();
    Object getValue();
    void updateValue(Object val);
//    public void setValue(Object value);

    PropertyInstanceDTO createDTO();
    public Object getOldValue();
    public int getTicksSameValue();
    public void setOldValue(Object oldValue);
    public void setTicksSameValue(int ticksSameValue);
    public List<Integer> getSameValueCounts();
}

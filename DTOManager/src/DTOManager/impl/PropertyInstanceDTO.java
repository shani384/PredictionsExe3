package DTOManager.impl;

import java.util.ArrayList;
import java.util.List;

public class PropertyInstanceDTO {
    private final PropertyDefinitionDTO propertyDefinitionDTO;
    private final Object value;
    private ArrayList<Integer> sameValueCounts;

    public PropertyDefinitionDTO getPropertyDefinitionDTO() {
        return propertyDefinitionDTO;
    }

    public String getName() {
        return getPropertyDefinitionDTO().getName();
    }
    public Object getValue() {
        return value;
    }

    public PropertyInstanceDTO(PropertyDefinitionDTO propertyDefinitionDTO, Object value, ArrayList<Integer> sameValueCounts) {
        this.propertyDefinitionDTO = propertyDefinitionDTO;
        this.value = value;
        this.sameValueCounts = sameValueCounts;
    }

    public ArrayList<Integer> getSameValueCounts() {
        return sameValueCounts;
    }
}

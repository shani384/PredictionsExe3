package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

public class SetDTO extends ActionDTO{
    private final String property;
    private final String value;
    public SetDTO(String actionType, EntityDefinitionDTO mainEntity, String property, String value) {
        super(actionType, mainEntity);
        this.property = property;
        this.value = value;
    }
    public String getProperty() {
        return property;
    }
    public String getValue() {
        return value;
    }
}

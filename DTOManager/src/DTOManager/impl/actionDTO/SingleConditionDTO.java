package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

public class SingleConditionDTO implements ConditionDTO{
    private final String property;
    private final EntityDefinitionDTO entity;
    private final String operator;
    private final String value;
    public SingleConditionDTO(String property, EntityDefinitionDTO entity, String operator, String value) {
        this.property = property;
        this.entity = entity;
        this.operator = operator;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public EntityDefinitionDTO getEntity() {
        return entity;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}

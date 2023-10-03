package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

public class IncreaseDTO extends ActionDTO{

    private final String property;
    private final String byExpression;

    public IncreaseDTO(String actionType, EntityDefinitionDTO mainEntity, String property, String byExpression) {
        super(actionType, mainEntity);
        this.property = property;
        this.byExpression = byExpression;
    }

    public String getProperty() {
        return property;
    }

    public String getByExpression() {
        return byExpression;
    }
}

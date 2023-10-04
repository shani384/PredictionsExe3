package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

public abstract class ActionDTO {
    private String actionType;
    private EntityDefinitionDTO mainEntity;

    public ActionDTO(String actionType, EntityDefinitionDTO mainEntity) {
        this.actionType = actionType;
        this.mainEntity = mainEntity;
    }

    public String getActionType() {
        return actionType;
    }

    public EntityDefinitionDTO getMainEntity() {
        return mainEntity;
    }
}

package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

public class ReplaceDTO extends ActionDTO{
    private final String mode;
    private final EntityDefinitionDTO createEntity;
    public ReplaceDTO(String actionType, EntityDefinitionDTO mainEntity, String mode, EntityDefinitionDTO createEntity) {
        super(actionType, mainEntity);
        this.mode = mode;
        this.createEntity = createEntity;
    }

    public EntityDefinitionDTO getCreateEntity() {
        return createEntity;
    }

    public String getMode() {
        return mode;
    }
}

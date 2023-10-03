package DTOManager.impl;

import java.util.Map;

public class EntityInstanceDTO {
    private final EntityDefinitionDTO entityDefinitionDTO;
    private final Integer id;
    private final Map<String, PropertyInstanceDTO> properties;

    public EntityInstanceDTO(EntityDefinitionDTO entityDefinitionDTO, Integer id, Map<String, PropertyInstanceDTO> properties) {
        this.entityDefinitionDTO = entityDefinitionDTO;
        this.id = id;
        this.properties = properties;
    }

    public EntityDefinitionDTO getEntityDefinitionDTO() {
        return entityDefinitionDTO;
    }

    public String getName() {
        return getEntityDefinitionDTO().getName();
    }
    public Integer getId() {
        return id;
    }

    public Map<String, PropertyInstanceDTO> getProperties() {
        return properties;
    }
}

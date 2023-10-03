package DTOManager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityInstanceManagerDTO {

    private final Map<Integer, EntityInstanceDTO> instancesDTO;

    public EntityInstanceManagerDTO(Map<Integer, EntityInstanceDTO> instancesDTO) {
        this.instancesDTO = instancesDTO;
    }


    public Map<Integer, EntityInstanceDTO> getInstancesDTO() {
        return instancesDTO;
    }
}

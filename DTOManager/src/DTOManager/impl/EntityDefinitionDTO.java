package DTOManager.impl;

import java.util.List;

public class EntityDefinitionDTO {

    private final String name;
    private final int population;
    private final List<PropertyDefinitionDTO> propertiesDTO;
    public EntityDefinitionDTO(String name, int population, List<PropertyDefinitionDTO> propertiesDTO) {
        this.name = name;
        this.population = population;
        this.propertiesDTO = propertiesDTO;
    }

    public int getPopulation() {
        return population;
    }

    public List<PropertyDefinitionDTO> getPropertiesDTO() {
        return propertiesDTO;
    }

    public String getName() {
        return name;
    }

    public PropertyDefinitionDTO getPropertyDefinitionDTOByName(String propertyName){
        for (PropertyDefinitionDTO propertyDefinitionDTO: propertiesDTO){
            if(propertyName.equals(propertyDefinitionDTO.getName())){
                return propertyDefinitionDTO;
            }
        }
        return null;
    }
}


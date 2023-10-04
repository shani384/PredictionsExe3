package DTOManager.impl;

import java.util.List;
import java.util.Map;

public class WorldDTO {

    private GridDTO gridDTO;
    private Map<String, EntityDefinitionDTO> nameToEntityDefinitionDTO;
    private List<RuleDTO> rulesDTO;
    private List<PropertyDefinitionDTO> envPropertiesDefinitionDTO;
    private final TerminationDTO terminationDTO;

    public WorldDTO(TerminationDTO terminationDTO) {
        this.terminationDTO = terminationDTO;
    }

    public WorldDTO(Map<String, EntityDefinitionDTO> nameToEntityDefinitionDTO, List<RuleDTO> rulesDTO,
                    TerminationDTO terminationDTO ,List<PropertyDefinitionDTO> envPropertiesDefinitionDTO, GridDTO gridDTO) {
        this.nameToEntityDefinitionDTO = nameToEntityDefinitionDTO;
        this.rulesDTO = rulesDTO;
        this.terminationDTO = terminationDTO;
        this.envPropertiesDefinitionDTO = envPropertiesDefinitionDTO;
        this.gridDTO = gridDTO;
    }
    public Map<String, EntityDefinitionDTO> getNameToEntityDefinitionDTO() {
        return nameToEntityDefinitionDTO;
    }
    public List<RuleDTO> getRulesDTO() {
        return rulesDTO;
    }
    public List<PropertyDefinitionDTO> getEnvPropertiesDefinitionDTO() {
        return envPropertiesDefinitionDTO;
    }
    public TerminationDTO getTerminationDTO() {
        return terminationDTO;
    }

    public EntityDefinitionDTO getEntityDefinitionDTOByName(String entityName){
        for (EntityDefinitionDTO entityDefinitionDTO: nameToEntityDefinitionDTO.values()){
            if (entityName.equals(entityDefinitionDTO.getName())){
                return entityDefinitionDTO;
            }
        }
        return null;
    }
    public RuleDTO getRuleDTOByName(String ruleName){
        for(RuleDTO ruleDTO: rulesDTO){
            if (ruleName.equals(ruleDTO.getName())){
                return ruleDTO;
            }
        }
        return null;
    }
    public PropertyDefinitionDTO getEnvVarDTOByName(String envVarName){
        for (PropertyDefinitionDTO envVarDTO: envPropertiesDefinitionDTO){
            if (envVarName.equals(envVarDTO.getName())){
                return envVarDTO;
            }
        }
        return null;
    }

    public GridDTO getGridDTO() {
        return gridDTO;
    }
}

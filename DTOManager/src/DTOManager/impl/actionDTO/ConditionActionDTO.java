package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

public class ConditionActionDTO extends ActionDTO{

    private final int numOfThenActions;
    private final int numOfElseActions;
    ConditionDTO conditionDTO;
    public ConditionActionDTO(String actionType, EntityDefinitionDTO mainEntity, ConditionDTO conditionDTO, int numOfThenActions, int numOfElseActions) {
        super(actionType, mainEntity);
        this.numOfThenActions = numOfThenActions;
        this.numOfElseActions = numOfElseActions;
        this.conditionDTO = conditionDTO;
    }

    public int getNumOfThenActions() {
        return numOfThenActions;
    }

    public int getNumOfElseActions() {
        return numOfElseActions;
    }

    public ConditionDTO getConditionDTO() {
        return conditionDTO;
    }
}

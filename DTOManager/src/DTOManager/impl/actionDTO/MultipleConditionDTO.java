package DTOManager.impl.actionDTO;

public class MultipleConditionDTO implements ConditionDTO{
    private final int numOfConditions;
    private final String logical;
    public MultipleConditionDTO(String logical, int numOfConditions) {
        this.logical = logical;
        this.numOfConditions = numOfConditions;
    }

    public int getConditions() {
        return numOfConditions;
    }

    public String getLogical() {
        return logical;
    }
}


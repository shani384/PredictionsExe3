package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

public class CalculationDTO extends ActionDTO{
    private final String calculationType;
    private final String property;
    private final String arg1;
    private final String arg2;
    public CalculationDTO(String actionType, EntityDefinitionDTO mainEntity, String calculationType, String property, String arg1, String arg2) {
        super(actionType, mainEntity);
        this.calculationType = calculationType;
        this.property = property;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public String getCalculationType() {
        return calculationType;
    }

    public String getProperty() {
        return property;
    }

    public String getArg1() {
        return arg1;
    }

    public String getArg2() {
        return arg2;
    }
}

package DTOManager.impl.actionDTO;

import DTOManager.impl.EntityDefinitionDTO;

import java.util.ArrayList;

public class ProximityDTO extends ActionDTO{
    private final String ofExpression;
    private final int columns;
    private final int rows;
    private ArrayList<ActionDTO> actions;
    private final EntityDefinitionDTO targetEntity;


    public ProximityDTO(String actionType, EntityDefinitionDTO mainEntity, String ofExpression, int columns, int rows, ArrayList<ActionDTO> actions, EntityDefinitionDTO targetEntity) {
        super(actionType, mainEntity);
        this.ofExpression = ofExpression;
        this.columns = columns;
        this.rows = rows;
        this.actions = actions;
        this.targetEntity = targetEntity;
    }

    public EntityDefinitionDTO getTargetEntity() {
        return targetEntity;
    }

    public String getOfExpression() {
        return ofExpression;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public ArrayList<ActionDTO> getActions() {
        return actions;
    }
}

package engine.world.design.grid.cell;

import engine.world.design.execution.entity.api.EntityInstance;

public class Cell {

    private EntityInstance entityInstance = null;
    private Coordinate coordinate;

    public Cell(EntityInstance entityInstance, Coordinate coordinate) {
        this.entityInstance = entityInstance;
        this.coordinate = coordinate;
    }

    public EntityInstance getEntityInstance() {
        return entityInstance;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setEntityInstance(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isTaken() {
        return entityInstance != null;
    }
}

package engine.world.design.grid.api;

import DTOManager.impl.GridDTO;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.grid.cell.Cell;
import engine.world.design.grid.cell.Coordinate;

import java.util.List;
import java.util.Random;

public interface Grid {

    public void initEntityPlace(EntityInstance entityInstance);
    public void moveEntity(EntityInstance entityInstance);
    public int getColumns();
    public int getRows();
    public List<Coordinate> getFreeCells();
    GridDTO createGridDTO();
}

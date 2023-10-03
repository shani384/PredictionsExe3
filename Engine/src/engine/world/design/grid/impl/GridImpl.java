package engine.world.design.grid.impl;

import DTOManager.impl.GridDTO;
import engine.world.design.execution.entity.api.EntityInstance;
import engine.world.design.grid.api.Grid;
import engine.world.design.grid.cell.Cell;
import engine.world.design.grid.cell.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GridImpl implements Grid {
    private Cell[][] grid;
    private List<Coordinate> freeCells;
    private final int columns;
    private final int rows;

    public GridImpl(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        grid = new Cell[rows][columns];
        freeCells = new ArrayList<>();
        for(int i = 0; i<rows;i++){
            for(int j=0; j<columns; j++){
                Coordinate coordinate = new Coordinate(i,j);
                grid[i][j] = new Cell(null,coordinate);
                freeCells.add(coordinate);
            }
        }
    }

    public GridDTO createGridDTO() {
        return new GridDTO(columns, rows);
    }
    @Override
    public void initEntityPlace(EntityInstance entityInstance){
        Cell freeCell = randomFreeCell();
        entityInstance.setCoordinate(freeCell.getCoordinate());
        freeCell.setEntityInstance(entityInstance);
    }
    @Override
    public List<Coordinate> getFreeCells() {
        return freeCells;
    }

    private Cell randomFreeCell(){
        Random random = new Random();
        int size = freeCells.size();
        int index = random.nextInt(size);
        Coordinate coordinate = freeCells.get(index);
        freeCells.remove(index);
//        x = random.nextInt(rows);
//        y = random.nextInt(columns);
//        Cell cell = grid[x][y];
//        while(cell.isTaken()){
//            x = random.nextInt(rows);
//            y = random.nextInt(columns);
//            cell = grid[x][y];
//        }
        return grid[coordinate.getX()][coordinate.getY()];
    }
    @Override
    public void moveEntity(EntityInstance entityInstance){
        Cell currCell = grid[entityInstance.getCoordinate().getX()][entityInstance.getCoordinate().getY()];
        Cell upCell = upCell(currCell);
        Cell downCell = downCell(currCell);
        Cell rightCell = rightCell(currCell);
        Cell leftCell = leftCell(currCell);
        if(!upCell.isTaken()){
            upCell.setEntityInstance(entityInstance);
            entityInstance.setCoordinate(upCell.getCoordinate());
            freeCells.remove(upCell.getCoordinate());
        }else if(!downCell.isTaken()){
            downCell.setEntityInstance(entityInstance);
            entityInstance.setCoordinate(downCell.getCoordinate());
            freeCells.remove(downCell.getCoordinate());
        } else if (!rightCell.isTaken()) {
            rightCell.setEntityInstance(entityInstance);
            entityInstance.setCoordinate(rightCell.getCoordinate());
            freeCells.remove(rightCell.getCoordinate());
        }else if(!leftCell.isTaken()){
            leftCell.setEntityInstance(entityInstance);
            entityInstance.setCoordinate(leftCell.getCoordinate());
            freeCells.remove(leftCell.getCoordinate());
        }
        freeCells.add(entityInstance.getCoordinate());
    }
    private Cell upCell(Cell cell){
        return grid[(cell.getCoordinate().getX() + 1) % rows][cell.getCoordinate().getY()];
    }

    private Cell downCell(Cell cell){
        int newX = (cell.getCoordinate().getX() - 1) % rows;
        try {
            if (newX < 0) {
                newX += rows;
            }
            return grid[newX][cell.getCoordinate().getY()];
        }catch (Exception e){
            System.out.println("newx:" + newX);
            System.out.println("y:" + cell.getCoordinate().getY());
        }
        return cell;
    }
    private Cell rightCell(Cell cell){
        return grid[cell.getCoordinate().getX()][(cell.getCoordinate().getY() + 1) % columns];
    }

    private Cell leftCell(Cell cell){
        int newY = (cell.getCoordinate().getY() - 1) % columns;
        try {
            if (newY < 0) {
                newY += columns;
            }
            return grid[cell.getCoordinate().getX()][newY];
        }catch (Exception e){
            System.out.println("newy:" + newY);
            System.out.println("x:" + cell.getCoordinate().getX());
        }
        return cell;
    }
    @Override
    public int getColumns() {
        return columns;
    }
    @Override
    public int getRows() {
        return rows;
    }
}

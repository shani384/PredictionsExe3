package DTOManager.impl;

public class GridDTO {

    final private int columns;
    private final int rows;


    public GridDTO(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public int getArea() {
        return rows * columns;
    }

}

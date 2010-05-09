package world;

import java.util.Iterator;

public class GridIterator implements Iterator<GridCell> {
    private int x;
    private int y;
    private boolean isDone;
    private GridCell[][] grid;

    public GridIterator(GridCell[][] grid) {
        this.grid = grid;
        reset();
    }

    public void reset() {
        x = 0;
        y = 0;
        isDone = false;
    }

    public boolean hasNext() {
        return isDone;
    }

    public GridCell next() {
        if (isDone) {
            return null;
        }
        GridCell result = grid[x][y];
        x++;
        if (x == grid.length) {
            x = 0;
            y++;
            if (y == grid[0].length) {
                isDone = true;
            }
        }
        return result;
    }
    
    public void remove() {        
    }
}

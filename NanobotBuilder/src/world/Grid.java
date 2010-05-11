package world;

import com.agentfactory.logic.lang.FOS;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Grid {

    private final String TOP = "top";
    private final String TOPLEFT = "topleft";
    private final String TOPRIGHT = "topright";
    private final String BOTTOM = "bottom";
    private final String BOTTOMLEFT = "bottomleft";
    private final String BOTTOMRIGHT = "bottomright";
    private final String LEFT = "left";
    private final String RIGHT = "right";

    private GridCell[][] grid;
    private Map<AbstractNanobot, GridCell> nanobotPositionMap = new HashMap<AbstractNanobot, GridCell>();
    private int width;
    private int height;

    public Grid(int x, int y) {
        this.width = x;
        this.height = y;
        grid = new GridCell[width][height];
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                grid[i][j] = new GridCell(i, j, null);
            }
        }
    }

    public void addNanobot(AbstractNanobot nanobot) {
        // Put it in first available position
        GridCell gridCell;
        GridIterator iterator = new GridIterator(grid);
        while ((gridCell = iterator.next()) != null) {
            if (!gridCell.isOccupied()) {
                gridCell.setOccupant(nanobot, this);
                break;
            }
        }
    }
    
    public boolean moveNanobot(AbstractNanobot nanobot, String target) {

        GridCell currentCell = nanobotPositionMap.get(nanobot);
        GridCell newCell = getRelativeCell(currentCell, target);
        if (newCell == null || newCell.isOccupied()){
            return false;
        }
        currentCell.setOccupant(null, null);
        newCell.setOccupant(nanobot, this);
        return true;
    }

    public void setNanobotLocation(AbstractNanobot nanobot, GridCell cell) {
        nanobotPositionMap.put(nanobot, cell);
    }

    private void perceiveNeighbour(int x, int y, String neighbourLabel, List<FOS> percepts) {
        if (x == this.width || x < 0 || y == this.height || y < 0) {
            percepts.add(new FOS("blocked(" + neighbourLabel + ")"));
        } else {
            GridCell cell = grid[x][y];
            if (cell.isOccupied()) {
                percepts.add(new FOS("blocked(" + neighbourLabel + ")"));
                percepts.add(new FOS("occupied(" + neighbourLabel + ")"));
            } else {
                percepts.add(new FOS("clear(" + neighbourLabel + ")"));
            }
        }
    }

    public List<FOS> getPercepts(AbstractNanobot nanobot) {

        // Perceive our current position
        GridCell gridCell = nanobotPositionMap.get(nanobot);
        List<FOS> percepts = new ArrayList<FOS>();
        percepts.add(new FOS("position(" + gridCell.getX() + ", " + gridCell.getY() + ")"));

        // Perceive all the neighbour squares
        int x = gridCell.getX();
        int y = gridCell.getY();
        perceiveNeighbour(x, y+1, TOP, percepts);
        perceiveNeighbour(x-1, y+1, TOPLEFT, percepts);
        perceiveNeighbour(x+1, y+1, TOPRIGHT, percepts);
        perceiveNeighbour(x, y-1, BOTTOM, percepts);
        perceiveNeighbour(x-1, y-1, BOTTOMLEFT, percepts);
        perceiveNeighbour(x+1, y-1, BOTTOMRIGHT, percepts);
        perceiveNeighbour(x-1, y, LEFT, percepts);
        perceiveNeighbour(x+1, y, RIGHT, percepts);

        return percepts;
    }

    public GridCell getRelativeCell(GridCell gridCell, String target) {
 
        int x = gridCell.getX();
        int y = gridCell.getY();
        if (target.equals(TOP)) {
            if (y == this.height)
                return null;
            return grid[x][y+1];
        }
        if (target.equals(BOTTOM)) {
            if (y == 0)
                return null;
            return grid[x][y-1];
        }
        if (target.equals(LEFT)) {
            if (x == 0)
                return null;
            return grid[x-1][y];
        }
        if (target.equals(RIGHT)) {
            if (y == this.width)
                return null;
            return grid[x+1][y];
        }
        else
            return null;
    }
}

package world;

import com.agentfactory.logic.lang.FOS;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Grid {
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
        GridCell gridLocation;
        GridIterator iterator = new GridIterator(grid);
        while ((gridLocation = iterator.next()) != null) {
            if (!gridLocation.isOccupied()) {
                gridLocation.setOccupant(nanobot, this);
                break;
            }
        }
    }

    public void setNanobotLocation(AbstractNanobot nanobot, GridCell cell) {
        nanobotPositionMap.put(nanobot, cell);
    }

    private void perceiveNeighbour(int x, int y, String neighbourLabel, List<FOS> percepts) {
        if (x == this.height || x < 0 || y == this.width || y < 0) {
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
        perceiveNeighbour(x, y+1, "top", percepts);
        perceiveNeighbour(x-1, y+1, "topleft", percepts);
        perceiveNeighbour(x+1, y+1, "topright", percepts);
        perceiveNeighbour(x, y-1, "bottom", percepts);
        perceiveNeighbour(x-1, y-1, "bottomleft", percepts);
        perceiveNeighbour(x+1, y-1, "bottomright", percepts);
        perceiveNeighbour(x-1, y, "left", percepts);
        perceiveNeighbour(x+1, y, "right", percepts);

        return percepts;
    }
}

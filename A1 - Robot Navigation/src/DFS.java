import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class DFS {
    Cell[][] grid;
    int rows;
    int columns;
    Point startPosition;
    List<Point> endPosition;
    boolean found;
    Hashtable<Integer, Boolean> visited = new Hashtable();

    public DFS(Cell[][] grid, int rows, int columns, Point startPosition, List<Point> endPosition) {
        this.grid = grid;
        this.rows = rows;
        this.columns = columns;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.found = false;
        int id = 1;

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                this.visited.put(id, Boolean.FALSE);
                ++id;
            }
        }

    }

    public void DFSSearch() throws InterruptedException {
        List<Cell> path = new ArrayList();
        Cell startNode = this.grid[this.startPosition.getY()][this.startPosition.getX()];
        List<Cell> endNodes = new ArrayList();
        Iterator endPositionIterator = this.endPosition.iterator();

        while(endPositionIterator.hasNext()) {
            Point p = (Point)endPositionIterator.next();
            endNodes.add(this.grid[p.getY()][p.getX()]);
        }

        Visualizer visualiser = new Visualizer(this.grid, this.visited, this.startPosition, this.endPosition, path);
        Thread thread = new Thread(visualiser);
        thread.start();
        this.dfs(startNode, endNodes);
        if (!this.found) {
            System.out.print("No solution found!");
        } else {
            System.out.println(Helper.numberOfVisitedNodes(this.visited));
            Helper.retracePath(this.grid, startNode, endNodes, path);
        }

    }

    boolean dfs(Cell at, List<Cell> endNodes) throws InterruptedException {
        Thread.sleep(100L);
        if (Helper.compareCoordinates(at, endNodes)) {
            this.found = true;
            return true;
        } else {
            this.visited.replace(at.getId(), Boolean.TRUE);
            List<Cell> neighbours = Helper.findNeighbours(this.grid, at, this.rows, this.columns);
            Iterator endPositionIterator = neighbours.iterator();

            while(endPositionIterator.hasNext()) {
                Cell c = (Cell)endPositionIterator.next();
                if (!(Boolean)this.visited.get(c.getId())) {
                    c.setParentNodeId(at.getId());
                    if (this.dfs(c, endNodes)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }
}

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CUS1 {
    Cell[][] grid;
    int rows;
    int columns;
    Point startPosition;
    List<Point> endPosition;

    public CUS1(Cell[][] grid, int rows, int columns, Point startPosition, List<Point> endPosition) {
        this.grid = grid;
        this.rows = rows;
        this.columns = columns;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public void cusSearch() throws InterruptedException {
        Hashtable<Integer, Boolean> visited = new Hashtable();
        int id = 1;

        int agent_life;
        for(int i = 0; i < this.rows; ++i) {
            for(agent_life = 0; agent_life < this.columns; ++agent_life) {
                visited.put(id, Boolean.FALSE);
                ++id;
            }
        }

        List<Cell> path = new ArrayList();
        agent_life = this.rows * this.columns;
        boolean found = false;
        Random random = new Random();
        Cell startNode = this.grid[this.startPosition.getY()][this.startPosition.getX()];
        List<Cell> endNodes = new ArrayList();
        Iterator endPositionIterator = this.endPosition.iterator();

        while(endPositionIterator.hasNext()) {
            Point p = (Point)endPositionIterator.next();
            endNodes.add(this.grid[p.getY()][p.getX()]);
        }

        Cell current = startNode;
        boolean stuckedInLoop = false;
        Visualizer visualiser = new Visualizer(this.grid, visited, this.startPosition, this.endPosition, path);
        Thread thread = new Thread(visualiser);
        thread.start();
        Thread.sleep(100L);

        while(agent_life != 0) {
            visited.replace(current.getId(), Boolean.TRUE);
            List<Cell> neighbours = Helper.findNeighbours(this.grid, current, this.rows, this.columns);

            Cell randomNeighbour;
            do {
                for(Iterator neighboursIterator = neighbours.iterator(); neighboursIterator.hasNext(); stuckedInLoop = true) {
                    Cell n = (Cell)neighboursIterator.next();
                    if (!(Boolean)visited.get(n.getId())) {
                        stuckedInLoop = false;
                        break;
                    }
                }

                randomNeighbour = (Cell)neighbours.get(random.nextInt(neighbours.size()));
            } while(!stuckedInLoop && (randomNeighbour.equals(startNode) || (Boolean)visited.get(randomNeighbour.getId())));

            randomNeighbour.setParentNodeId(current.getId());
            if (Helper.compareCoordinates(current, endNodes)) {
                found = true;
                break;
            }

            if (stuckedInLoop) {
                break;
            }

            current = randomNeighbour;
            --agent_life;
            Thread.sleep(100L);
        }

        if (found) {
            Helper.retracePath(this.grid, startNode, endNodes, path);
        } else if (stuckedInLoop) {
            System.out.print("Looping hell");
        } else {
            System.out.print("Path not found");
        }

    }
}

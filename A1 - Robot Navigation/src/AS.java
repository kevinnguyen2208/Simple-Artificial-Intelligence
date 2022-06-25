import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class AS {
    public AS() {
    }

    public static void initializeGrid(Cell[][] grid, int rows, int columns) {
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                grid[i][j].setfCost(0);
                grid[i][j].setgCost(1);
                grid[i][j].sethCost(99);
            }
        }

    }

    public static void as(Cell[][] grid, int rows, int columns, Point startPosition, List<Point> endPosition) throws InterruptedException {
        initializeGrid(grid, rows, columns);
        Cell startNode = grid[startPosition.getY()][startPosition.getX()];
        List<Cell> endNodes = new ArrayList();
        Iterator endPositionIterator = endPosition.iterator();

        while(endPositionIterator.hasNext()) {
            Point p = (Point)endPositionIterator.next();
            endNodes.add(grid[p.getY()][p.getX()]);
        }

        Cell endNode = (Cell)endNodes.get(0);
        List<Cell> path = new ArrayList();
        Hashtable<Integer, Boolean> visited = new Hashtable();
        int id = 1;

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                visited.put(id, Boolean.FALSE);
                ++id;
            }
        }

        boolean found = false;
        List<Cell> open = new ArrayList();
        List<Cell> closed = new ArrayList();
        Visualizer visualiser = new Visualizer(grid, visited, startPosition, endPosition, path);
        Thread thread = new Thread(visualiser);
        thread.start();
        Thread.sleep(100L);
        open.add(startNode);

        run:
        while(!open.isEmpty()) {
            Collections.sort(open);
            Cell current = (Cell)open.remove(0);
            closed.add(current);
            visited.replace(current.getId(), Boolean.TRUE);
            if (Helper.compareCoordinates(current, endNodes)) {
                found = true;
                break;
            }

            List<Cell> neighbours = Helper.findNeighbours(grid, current, rows, columns);
            Iterator neighboursIterator = neighbours.iterator();

            while(true) {
                Cell cell;
                int newCostToNeighbour;
                do {
                    do {
                        if (!neighboursIterator.hasNext()) {
                            Thread.sleep(100L);
                            continue run;
                        }

                        cell = (Cell)neighboursIterator.next();
                    } while(containsCell(closed, cell));

                    newCostToNeighbour = current.getgCost() + getManhattanDistance(current, cell);
                } while(containsCell(open, cell) && newCostToNeighbour >= cell.getgCost());

                cell.setgCost(newCostToNeighbour);
                cell.sethCost(getManhattanDistance(cell, endNode));
                cell.setParentNodeId(current.getId());
                if (!containsCell(open, cell)) {
                    open.add(cell);
                }
            }
        }

        if (!found) {
            System.out.print("Path not found!");
        } else {
            System.out.println(Helper.numberOfVisitedNodes(visited));
            Helper.retracePath(grid, startNode, endNodes, path);
        }

    }

    public static int getManhattanDistance(Cell c1, Cell c2) {
        return Math.abs(c2.getX() - c1.getX()) + Math.abs(c2.getY() - c1.getY());
    }

    public static boolean containsCell(List<Cell> list, Cell cell) {
        boolean result = false;
        Iterator listIterator = list.iterator();

        while(listIterator.hasNext()) {
            Cell c = (Cell)listIterator.next();
            if (c.getId() == cell.getId()) {
                result = true;
            }
        }

        return result;
    }
}

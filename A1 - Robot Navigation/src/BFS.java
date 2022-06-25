import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class BFS {
    public BFS() {
    }

    static void BFSSearch(Cell[][] grid, int rows, int columns, Point startPosition, List<Point> endPosition) throws InterruptedException {
        List<Cell> final_path = new ArrayList();
        Queue<Cell> queue = new ArrayDeque();
        Hashtable<Cell, Cell> trackNodes = new Hashtable();
        Hashtable<Integer, Boolean> visited1 = new Hashtable();
        List<Cell> endNodes = new ArrayList();
        Iterator endPositionIterator = endPosition.iterator();

        while(endPositionIterator.hasNext()) {
            Point p = (Point)endPositionIterator.next();
            endNodes.add(grid[p.getY()][p.getX()]);
        }

        Visualizer visualiser = new Visualizer(grid, visited1, startPosition, endPosition, final_path);
        Thread thread = new Thread(visualiser);
        thread.start();
        Thread.sleep(100L);
        int id = 1;

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                visited1.put(id, Boolean.FALSE);
                ++id;
            }
        }

        boolean found = false;
        Cell startCell = grid[startPosition.getY()][startPosition.getX()];
        Cell currentNode = new Cell(-1, -1, -1, false);
        Cell destinationCell = null;
        queue.add(startCell);
        trackNodes.put(currentNode, new Cell(-1, -1, 0, false));

        for(; !queue.isEmpty(); Thread.sleep(120L)) {
            currentNode = (Cell)queue.remove();
            visited1.replace(currentNode.getId(), Boolean.TRUE);
            List<Cell> neighbours = Helper.findNeighbours(grid, currentNode, rows, columns);
            if (!neighbours.isEmpty()) {
                if (Helper.compareCoordinates(currentNode, endNodes)) {
                    destinationCell = currentNode;
                    found = true;
                    break;
                }

                Iterator neighboursIterator = neighbours.iterator();

                while(neighboursIterator.hasNext()) {
                    Cell cell = (Cell)neighboursIterator.next();
                    if (!(Boolean)visited1.get(cell.getId()) && !Helper.containsCell(queue, cell)) {
                        queue.add(cell);
                        visited1.replace(cell.getId(), Boolean.TRUE);
                        trackNodes.put(cell, currentNode);
                    }
                }
            }
        }

        if (!found) {
            System.out.print("Path not found!");
        } else {
            System.out.println(Helper.numberOfVisitedNodes(visited1));
            Cell at = destinationCell;

            ArrayList path;
            for(path = new ArrayList(); at != null; at = (Cell)trackNodes.get(at)) {
                path.add(at);
            }

            for(int i = path.size() - 1; i >= 0; --i) {
                final_path.add((Cell)path.get(i));
            }

            final_path = Helper.setDirection(final_path);
            Iterator finalPathIterator = final_path.iterator();

            while(finalPathIterator.hasNext()) {
                Cell _point = (Cell)finalPathIterator.next();
                System.out.print(_point.getDirection() + " ");
            }
        }

    }
}

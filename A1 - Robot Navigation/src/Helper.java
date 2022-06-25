import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class Helper {
    public Helper() {
    }

    static List<Cell> findNeighbours(Cell[][] grid, Cell currentNode, int rows, int columns) {
        List<Cell> neighbours = new ArrayList();
        int currentX = currentNode.getX();
        int currentY = currentNode.getY();
        new Cell(-1, -1, -1, false);
        Cell temporaryCell;
        if (0 <= currentY - 1) {
            temporaryCell = grid[currentY - 1][currentX];
            if (!temporaryCell.isWall()) {
                neighbours.add(temporaryCell);
            }
        }

        if (0 <= currentX - 1) {
            temporaryCell = grid[currentY][currentX - 1];
            if (!temporaryCell.isWall()) {
                neighbours.add(temporaryCell);
            }
        }

        if (rows > currentY + 1) {
            temporaryCell = grid[currentY + 1][currentX];
            if (!temporaryCell.isWall()) {
                neighbours.add(temporaryCell);
            }
        }

        if (columns > currentX + 1) {
            temporaryCell = grid[currentY][currentX + 1];
            if (!temporaryCell.isWall()) {
                neighbours.add(temporaryCell);
            }
        }

        return neighbours;
    }

    static List<Cell> setDirection(List<Cell> path) {
        List<Cell> newPathWithDirection = new ArrayList();

        for(int i = 0; i < path.size() - 1; ++i) {
            Cell current_cell = (Cell)path.get(i);
            Cell next_cell = (Cell)path.get(i + 1);
            if (next_cell.getX() == current_cell.getX() + 1) {
                current_cell.setDirection("Right");
            } else if (next_cell.getX() == current_cell.getX() - 1) {
                current_cell.setDirection("Left");
            } else if (next_cell.getY() == current_cell.getY() + 1) {
                current_cell.setDirection("Down");
            } else if (next_cell.getY() == current_cell.getY() - 1) {
                current_cell.setDirection("Up");
            }

            newPathWithDirection.add(current_cell);
        }

        return newPathWithDirection;
    }

    public static Cell findCellInGrid(Cell[][] grid, int id) {
        Cell result = null;
        boolean found = false;

        for(int i = 0; i < grid.length && !found; ++i) {
            for(int j = 0; j < grid[i].length && !found; ++j) {
                if (grid[i][j].getId() == id) {
                    result = grid[i][j];
                    found = true;
                }
            }
        }

        return result;
    }

    static boolean compareCoordinates(Cell c1, List<Cell> cList) {
        boolean result = false;
        Iterator listIterator = cList.iterator();

        while(listIterator.hasNext()) {
            Cell c = (Cell)listIterator.next();
            if (c1.getX() == c.getX() && c1.getY() == c.getY()) {
                result = true;
                break;
            }
        }

        return result;
    }

    static void retracePath(Cell[][] grid, Cell startNode, List<Cell> endNodes, List<Cell> final_path) {
        List<Cell> path = new ArrayList();
        Iterator endPositionIterator = endNodes.iterator();

        while(true) {
            Cell _point;
            do {
                if (!endPositionIterator.hasNext()) {
                    for(int i = path.size() - 1; i >= 0; --i) {
                        final_path.add((Cell)path.get(i));
                    }

                    final_path = setDirection(final_path);
                    if (final_path.isEmpty()) {
                        System.out.print("No path found");
                    }

                    endPositionIterator = final_path.iterator();

                    while(endPositionIterator.hasNext()) {
                        _point = (Cell)endPositionIterator.next();
                        System.out.print(_point.getDirection() + " ");
                    }

                    return;
                }

                _point = (Cell)endPositionIterator.next();
            } while(_point.getParentNodeId() == 0);

            Cell currentNode = _point;

            do {
                path.add(currentNode);
                int id = currentNode.getParentNodeId();
                currentNode = findCellInGrid(grid, id);
            } while(currentNode.getId() != startNode.getId());

            path.add(startNode);
        }
    }

    static int numberOfVisitedNodes(Hashtable<Integer, Boolean> visited) {
        int count = 0;
        Iterator endPositionIterator = visited.values().iterator();

        while(endPositionIterator.hasNext()) {
            Boolean b = (Boolean)endPositionIterator.next();
            if (b == Boolean.TRUE) {
                ++count;
            }
        }

        return count;
    }
    
    static boolean containsCell(Collection<Cell> list, Cell cell) {
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

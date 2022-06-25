import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

class MyCanvas extends Canvas {
    public Queue<Cell> queue;
    public Hashtable<Integer, Boolean> visitedNodes;
    public Cell[][] grid;
    public Point startNode;
    public List<Point> endNodes;
    public List<Cell> path;
    public boolean path_found;
    int cellSize;
    int WIDTH;
    int HEIGHT;

    public MyCanvas(int w, int h) {
        this.setBackground(Color.WHITE);
        this.setSize(w, h);
    }

    public void paint(Graphics g) {
        int rows = this.grid.length;
        int columns = this.grid[0].length;
        this.WIDTH = columns * this.cellSize;
        this.HEIGHT = rows * this.cellSize;
        Iterator visitedNodeIterator = this.visitedNodes.keySet().iterator();

        int j;
        while(visitedNodeIterator.hasNext()) {
            j = (Integer)visitedNodeIterator.next();
            if ((Boolean)this.visitedNodes.get(j)) {
                Cell node = findCellInGrid(this.grid, j);
                this.drawRectangle(g, node.getX() * this.cellSize, node.getY() * this.cellSize, this.cellSize, Color.cyan);
            }
        }

        int i;
        for(i = 0; i < rows; ++i) {
            for(j = 0; j < columns; ++j) {
                if (this.grid[i][j].isWall()) {
                    this.drawRectangle(g, j * this.cellSize, i * this.cellSize, this.cellSize, Color.LIGHT_GRAY);
                }
            }
        }

        this.drawRectangle(g, this.startNode.getX() * this.cellSize, this.startNode.getY() * this.cellSize, this.cellSize, Color.RED);
        visitedNodeIterator = this.endNodes.iterator();

        while(visitedNodeIterator.hasNext()) {
            Point p = (Point)visitedNodeIterator.next();
            this.drawRectangle(g, p.getX() * this.cellSize, p.getY() * this.cellSize, this.cellSize, Color.GREEN);
        }

        g.setColor(Color.BLACK);

        for(i = 1; i < rows; ++i) {
            g.drawLine(0, i * this.HEIGHT / rows, this.WIDTH, i * this.HEIGHT / rows);
        }

        for(i = 1; i < columns; ++i) {
            g.drawLine(i * this.WIDTH / columns, 0, i * this.WIDTH / columns, this.HEIGHT);
        }

        if (this.path_found) {
            visitedNodeIterator = this.path.iterator();

            while(visitedNodeIterator.hasNext()) {
                Cell c = (Cell)visitedNodeIterator.next();
                this.drawRectangle(g, c.getX() * this.cellSize, c.getY() * this.cellSize, this.cellSize, Color.YELLOW);

                try {
                    Thread.sleep(150L);
                } catch (InterruptedException endPositionIterator8) {
                    Logger.getLogger(MyCanvas.class.getName()).log(Level.SEVERE, (String)null, endPositionIterator8);
                }

                this.drawRectangle(g, this.startNode.getX() * this.cellSize, this.startNode.getY() * this.cellSize, this.cellSize, Color.RED);
                Iterator endPositionIterator = this.endNodes.iterator();

                while(endPositionIterator.hasNext()) {
                    Point p = (Point)endPositionIterator.next();
                    this.drawRectangle(g, p.getX() * this.cellSize, p.getY() * this.cellSize, this.cellSize, Color.GREEN);
                }

                g.setColor(Color.BLACK);

                for(i = 1; i < rows; ++i) {
                    g.drawLine(0, i * this.HEIGHT / rows, this.WIDTH, i * this.HEIGHT / rows);
                }

                for(i = 1; i < columns; ++i) {
                    g.drawLine(i * this.WIDTH / columns, 0, i * this.WIDTH / columns, this.HEIGHT);
                }
            }
        }

    }

    void drawRectangle(Graphics g, int x, int y, int cellSize, Color c) {
        g.setColor(c);
        g.fillRect(x, y, cellSize, cellSize);
    }

    static Cell findCellInGrid(Cell[][] grid, int id) {
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
}

import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Visualizer implements Runnable {
    public MyCanvas canvas;
    public Frame frame;
    public List<Cell> path;

    public Visualizer(Cell[][] grid, Hashtable<Integer, Boolean> visitedNodes, Point startNode, List<Point> endNodes, List<Cell> path) throws InterruptedException {
        this.path = path;
        int rows = grid.length;
        int columns = grid[0].length;
        int cellSize = 80;
        this.canvas = new MyCanvas(columns * cellSize, rows * cellSize);
        this.canvas.grid = grid;
        this.canvas.visitedNodes = visitedNodes;
        this.canvas.cellSize = cellSize;
        this.canvas.startNode = startNode;
        this.canvas.endNodes = endNodes;
        this.canvas.path = path;
        this.canvas.path_found = false;
        this.canvas.setSize(columns * cellSize, rows * cellSize);
        this.frame = new Frame("Canvas Example");
        this.frame.add(this.canvas);
        this.frame.setLayout((LayoutManager)null);
        this.frame.setSize(columns * cellSize, rows * cellSize);
        this.frame.setResizable(false);
    }

    public void run() {
        this.frame.setVisible(true);
        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Visualizer.this.frame.dispose();
                System.exit(0);
            }
        });

        while(this.path.isEmpty()) {
            this.canvas.repaint();

            try {
                Thread.sleep(70L);
            } catch (InterruptedException endPositionIterator2) {
                Logger.getLogger(Visualizer.class.getName()).log(Level.SEVERE, (String)null, endPositionIterator2);
            }
        }

        this.canvas.path_found = true;
        this.canvas.repaint();
    }
}

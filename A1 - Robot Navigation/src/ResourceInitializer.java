import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceInitializer {
    private Cell[][] grid;
    private Point startPosition;
    private List<Point> endPosition;
    private int rows;
    private int columns;

    public ResourceInitializer() {
    }

    public Cell[][] getGrid() {
        return this.grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public Point getStartPos() {
        return this.startPosition;
    }

    public void setStartPos(Point startPosition) {
        this.startPosition = startPosition;
    }

    public List<Point> getEndPos() {
        return this.endPosition;
    }

    public void setEndPos(List<Point> endPosition) {
        this.endPosition = endPosition;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void Initialize(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        int counter = 0;
        this.rows = 0;
        this.columns = 0;
        this.endPosition = new ArrayList();
        List<Wall> walls = new ArrayList();
        String regex = "\\d+";

        String st;
        int _x;
        int _y;
        int _wide;
        for(Pattern pattern = Pattern.compile(regex); (st = br.readLine()) != null; ++counter) {
            Matcher matcher;
            if (counter == 0) {
                matcher = pattern.matcher(st);
                matcher.find();
                this.rows = Integer.parseInt(matcher.group());
                matcher.find();
                this.columns = Integer.parseInt(matcher.group());
            }

            if (counter == 1) {
                matcher = pattern.matcher(st);
                matcher.find();
                _x = Integer.parseInt(matcher.group());
                matcher.find();
                _y = Integer.parseInt(matcher.group());
                this.startPosition = new Point(_x, _y);
            }

            if (counter == 2) {
                matcher = pattern.matcher(st);

                while(matcher.find()) {
                    _x = Integer.parseInt(matcher.group());
                    matcher.find();
                    _y = Integer.parseInt(matcher.group());
                    this.endPosition.add(new Point(_x, _y));
                }
            }

            if (counter >= 3) {
                matcher = pattern.matcher(st);

                while(matcher.find()) {
                    _x = Integer.parseInt(matcher.group());
                    matcher.find();
                    _y = Integer.parseInt(matcher.group());
                    matcher.find();
                    _wide = Integer.parseInt(matcher.group());
                    matcher.find();
                    int _high = Integer.parseInt(matcher.group());
                    walls.add(new Wall(_wide, _high, _x, _y));
                }
            }
        }

        this.grid = new Cell[this.rows][this.columns];
        _x = 1;

        for(_y = 0; _y < this.rows; ++_y) {
            for(_wide = 0; _wide < this.columns; ++_wide) {
                if (isWall(_wide, _y, walls)) {
                    this.grid[_y][_wide] = new Cell(_wide, _y, _x, true);
                } else {
                    this.grid[_y][_wide] = new Cell(_wide, _y, _x, false);
                }

                ++_x;
            }
        }

        br.close();
    }

    static boolean isWall(int xPos, int yPos, List<Wall> walls) {
        boolean result = false;

        for(int i = 0; i < walls.size(); ++i) {
            Wall _wall = (Wall)walls.get(i);
            if (xPos >= _wall.getX() && yPos >= _wall.getY() && xPos < _wall.getX() + _wall.getWide() && yPos < _wall.getY() + _wall.getHigh()) {
                result = true;
                break;
            }
        }

        return result;
    }

    static void printGrid(int rows, int columns, Cell[][] grid) {
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < columns; ++j) {
                System.out.print(grid[i][j].toString());
            }

            System.out.println();
        }

    }
}

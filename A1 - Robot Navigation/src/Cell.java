public class Cell extends Point implements Comparable<Cell> {
    private int id;
    private boolean wall;
    private String direction;
    private int fCost;
    private int gCost;
    private int hCost;
    private int parentNodeId;

    public int getfCost() {
        return this.fCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }
    
    public int getgCost() {
        return this.gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }
 

    public int gethCost() {
        return this.hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public int getParentNodeId() {
        return this.parentNodeId;
    }

    public void setParentNodeId(int parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    Cell(Cell cell) {
        super(cell.getX(), cell.getY());
        this.id = cell.getId();
    }

    public String toString() {
        return this.isWall() ? String.format("* ") : String.format("%d ", 0);
    }

    public String printPoint() {
        return super.toString();
    }

    public Cell(int x, int y, int id, boolean wall) {
        super(x, y);
        this.wall = wall;
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWall(boolean is_wall) {
        this.wall = is_wall;
    }

    public boolean isWall() {
        return this.wall;
    }
 

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    public boolean equals(Cell obj) {
        return this.getX() == obj.getX() && this.getY() == obj.getY();
    }

    public int compareTo(Cell o) {
        return this.getgCost() + this.gethCost() - (o.getgCost() + o.gethCost());
    }
}

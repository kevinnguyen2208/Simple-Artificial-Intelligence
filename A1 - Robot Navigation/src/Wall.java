public class Wall extends Point {
    private int wide;
    private int high;

    public Wall(int wide, int high, int x, int y) {
        super(x, y);
        this.wide = wide;
        this.high = high;
    }
    
    public int getHigh() {
        return this.high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getWide() {
        return this.wide;
    }

    public void setWide(int wide) {
        this.wide = wide;
    }   

    public String toString() {
        return String.format("X: %d, Y: %d, Wide: %d, High: %d", this.getX(), this.getY(), this.getWide(), this.getHigh());
    }
}

package lab1.playerutilities;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void addX(int dx) {
        this.x += dx;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addY(int dy) {
        this.y += dy;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Coordinate: (" + this.getX() + ", " + this.getY() + ")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate coordinate = (Coordinate) o;
        return x == coordinate.x && y == coordinate.y;
    }
}

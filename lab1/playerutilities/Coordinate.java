package lab1.playerutilities;

import java.util.Objects;

public class Coordinate {
    private Integer x;
    private Integer y;

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

    public Coordinate copy() {
        return new Coordinate(this.getX(), this.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate coordinate = (Coordinate) o;
        return Objects.equals(x, coordinate.x) && Objects.equals(y, coordinate.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}

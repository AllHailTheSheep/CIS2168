package lab1.playerutilities;

import java.util.Objects;

public class Coordinate {
    private Integer x;
    private Integer y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void swap() {
        int temp = this.y;
        this.y = this.x;
        this.x = temp;

    }

    
    public boolean within(Coordinate c, int tolerance) {
        Double dist = distance(c);
        if (dist <= tolerance) {
            return true;
        } else {
            return false;
        }
    }

    public double distance(Coordinate c) {
        double ac = Math.abs(c.getY() - this.y);
        double cb = Math.abs(c.getX()- this.x);
        return Math.hypot(ac, cb);
    }


    /** 
     * Gets the current X-axis value.
     * @return int The current X-axis value.
     */
    public int getX() {
        return this.x;
    }

    
    /** 
     * Sets the X-axis value.
     * @param x The new X-axis value.
     */
    public void setX(int x) {
        this.x = x;
    }
    
    
    /** 
     * Adds the specified amount to the X-axis.
     * @param dx The amount to add to the X-axis.
     */
    public void addX(int dx) {
        this.x += dx;
    }

    
    /** 
     * Gets the Y-axis value.
     * @return int The current Y-axis value.
     */
    public int getY() {
        return this.y;
    }

    
    /** 
     * Sets the Y-axis value.
     * @param y The new Y-axis value.
     */
    public void setY(int y) {
        this.y = y;
    }

    
    /** 
     * Adds the specified amount to the Y-axis.
     * @param dy The amount to add.
     */
    public void addY(int dy) {
        this.y += dy;
    }
    
    
    /** 
     * Returns a string representation of the Coordinate.
     * @return String String representation of the Coordinate.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Coordinate: (" + this.getX() + ", " + this.getY() + ")");
        return sb.toString();
    }

    
    /** 
     * Returns a deep copy of the current coordinate. The new Coordinate will be independent of the old but they will
     * have the same hashcode.
     * @return Coordinate The new Coordinate.
     */
    public Coordinate copy() {
        return new Coordinate(this.getX(), this.getY());
    }

    
    /** 
     * Returns true if two Coordinate objects are equal.
     * @param o The Coordinate object to compare.
     * @return boolean Whether the objects are equal.
     */
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

    
    /** 
     * Returns the hashcode of the object. Necessary for object equivalence in HashMaps where a Coordinate is the key.
     * @return int The hashcode of the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}

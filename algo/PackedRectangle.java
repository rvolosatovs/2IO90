/**
 * Created by s154563 on 1-5-2017.
 * Class to represent a rectangle with coordinates, having a width and a height and a Coordinates coords.
 * It extends the Rectangle class by adding Coordinates.
 */
public class PackedRectangle extends Rectangle {

    /** Representation for the Coordinates */
    private Coordinates coords;

    /**
     * Constructor for the PackedRectangle needing a width, height and Coordinates.
     *
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param coords the coordinates for the rectangle
     */
    public PackedRectangle(int width, int height, Coordinates coords) {
        super(width, height);
        setCoordinates(coords);
    }

    /**
     * Method to get the current coordinates of the rectangle.
     *
     * @return the current coordinates of the rectangle.
     */
    public Coordinates getCoordinates() {
        return coords;
    }

    /**
     *
     * @return X coordinate of the rectangle.
     */
    public int getX() {
        return coords.getX();
    }

    /**
     *
     * @return Y coordinate of the rectangle.
     */
    public int getY() {
        return coords.getY();
    }

    /**
     * Method to set new coordinates
     *
     * @param coords the new Coordinates to be set.
     */
    public void setCoordinates(Coordinates coords) {
        this.coords = coords;
    }
}

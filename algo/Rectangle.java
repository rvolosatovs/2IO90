/**
 * Created by s154563 on 1-5-2017.
 * Class to represent a rectangle, having a width and a height.
 */
public class Rectangle {

    /** Representations of the width and the height */
    private final int width;
    private final int height;

    /**
     * Constructor for the rectangle with width and height.
     *
     * @param width the width
     * @param height the height
     */
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Method to return the width of the rectangle.
     *
     * @return the width of the rectangle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Method to return the height of the rectangle.
     *
     * @return the height of the rectangle.
     */
    public int getHeight() {
        return height;
    }
}

/**
 * Created by rvolosatovs on 5/2/17.
 */
public abstract class Rectangle extends java.awt.Rectangle {
    private boolean rotated = false;

    public Rectangle(final int width, final int height) {
       super(width, height);
    }

    public boolean wasRotated() {
        return rotated;
    }

    public void rotate() {
        rotated = !rotated;
        // TODO shift coords
    }

    public String toString() {
        //TODO return coords and rotation
        return "0 0 yes";
    }
}

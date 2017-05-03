/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Rectangle extends java.awt.Rectangle {
    private int index;
    private bool rotated = false;

    public Rectangle(final int index, final int width, final int height) {
       super(width, height);
       this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public bool wasRotated() {
        return rotated;
    }

    public void rotate() {
        rotated = !rotated;
        // TODO shift coords
    }

    public void toString() {
        //TODO return coords and rotation
        return "0 0 yes"
    }
}

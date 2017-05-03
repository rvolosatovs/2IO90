/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Container extends java.awt.Rectangle {
    public Container(final int width, final int height) {
       super(width, height);
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

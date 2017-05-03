/**
 * Created by rvolosatovs on 5/2/17.
 */
public class IndexedRectangle extends Rectangle {
    private int index;
    public Rectangle(final int width, final int height) {
       super(width, height);
       this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void toString() {
        //TODO return coords and rotation
        return "0 0 yes"
    }
}

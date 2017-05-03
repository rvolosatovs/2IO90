import java.util.Set;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Container extends java.awt.Rectangle {
    private Set<IndexedRectangle> rectangles;

    public Container(Set<IndexedRectangle> rectangles) {
       this.rectangles = rectangles;
    }

    public String toString() {
        //TODO return coords and rotation
        return "0 0 yes";
    }
}

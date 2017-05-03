import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Container extends java.awt.Rectangle {
    private Collection<IndexedRectangle> rectangles;

    public Container(Collection<IndexedRectangle> rectangles) {
       this.rectangles = rectangles;
    }

    public Collection<IndexedRectangle> getRectangles() {
        return new LinkedHashSet<>(rectangles);
    }

    public String toString() {
        //TODO return coords and rotation
        return "0 0 yes";
    }
}

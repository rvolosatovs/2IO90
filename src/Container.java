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
        StringBuilder sb = new StringBuilder();
        sb.append("placement of rectangles:\n");
        rectangles.forEach((r)-> sb.append(r.toString()).append("\n"));
        return sb.toString();
    }
}

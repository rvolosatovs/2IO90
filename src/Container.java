import java.awt.*;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Container extends AbstractCollection<IndexedRectangle> {
    private Collection<IndexedRectangle> rectangles;

    public Container(Collection<IndexedRectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public Collection<IndexedRectangle> getRectangles() {
        return new ArrayList<>(rectangles);
    }

    public Iterator<IndexedRectangle> iterator() {
        return rectangles.iterator();
    }

    public boolean add(IndexedRectangle r) {
        return rectangles.add(r);
    }

    public int size() {
        return rectangles.size();
    }

    public int getWidth() {
        int width = 0;

        for (Rectangle r : rectangles) {
            if (r.x + r.width > width) {
                width = r.x + r.width;
            }
        }
        return width;
    }

    public int getHeight() {
        int height = 0;

        for (Rectangle r : rectangles) {
            if (!(r.x == 0 && r.y == 0)) {
                if (r.y + r.height > height) {
                    height = r.y + r.height;
                }
            }
        }
        return height;
    }

    public Dimension getSize() {
        Dimension d = new Dimension(this.getWidth(), this.getHeight());
        return new Dimension(d);
    }

    public Rectangle getBounds() {
        return new Rectangle(getSize());
    }

    public int getArea() {
        Dimension d = getSize();
        return d.width * d.height;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("placement of rectangles:\n");
        rectangles.forEach((r) -> sb.append(r.toString()).append("\n"));
        return sb.toString();
    }
}

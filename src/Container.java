import java.awt.*;
import java.util.*;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Container extends AbstractCollection<IndexedRectangle> {
    private Collection<IndexedRectangle> rectangles;

    public Container(Collection<IndexedRectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public Container() { this.rectangles = new ArrayList<>(); }

    public Collection<IndexedRectangle> getRectangles() {
        return new ArrayList<>(rectangles);
    }

    public Collection<IndexedRectangle> getRectanglesByIndex() {
        ArrayList<IndexedRectangle> copy = new ArrayList<>();
        copy.addAll(rectangles);
        ArrayList<IndexedRectangle> result = new ArrayList<>();
        while (!copy.isEmpty()) {
            IndexedRectangle min = copy.get(0);
            for (IndexedRectangle r: copy) {
                if (r.getIndex() < min.getIndex()) {
                    min = r;
                }
            }
            copy.remove(min);
            result.add(min);
        }
        return result;
    }

    public Iterator<IndexedRectangle> iterator() {
        return rectangles.iterator();
    }

    public boolean add(IndexedRectangle r) {
        return rectangles.add(r);
    }

    public boolean remove(IndexedRectangle r) {
        return rectangles.remove(r);
    }

    public int size() {
        return rectangles.size();
    }

    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    public boolean contains(int x, int y) {
        for (Rectangle r: this) {
            if (r.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public int getWidth() {
        int maxWidth = 0;

        for (Rectangle r : this) {
            int width = r.x + r.width;
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    public int getHeight() {
        int maxHeight = 0;

        for (Rectangle r : this) {
            int height = r.x + r.height;
            if (height > maxHeight) {
                maxHeight =  height;
            }
        }
        return maxHeight;
    }

    public Dimension getSize() {return new Dimension(this.getWidth(), this.getHeight());}

    public int getArea() {
        Dimension d = getSize();
        return d.width * d.height;
    }

    public Polygon getPolygon() {
        Rectangle bounds = getBounds();
        Set<Point> points = new HashSet<>(3*size());
        for (int x = bounds.x; x > 0; x--) {
            boolean lastContained = false;
           for (int y = bounds.y; y > 0; y--) {
               if (!contains(x, y)) {
                    lastContained = false;
                    continue;
               }
               if (!lastContained && !(contains(x-1,y) && contains(x+1,y))) {
                   points.add(new Point(x, y));
               }
               lastContained = true;
           }
        }

        int nPoints = points.size();

        int[] xPoints, yPoints;
        xPoints = new int[nPoints];
        yPoints = new int[nPoints];
        int i = 0;
        for (Point p : points) {
            xPoints[i] = p.x;
            yPoints[i] = p.y;
            i++;
        }
        return new Polygon(xPoints, yPoints, nPoints);
    }

    public Rectangle getBounds() {
        return new Rectangle(getSize());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("placement of rectangles:\n");
        rectangles.forEach((r) -> sb.append(r.toString()).append("\n"));
        return sb.toString();
    }
}

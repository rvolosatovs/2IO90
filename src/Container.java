import java.awt.*;
import java.util.*;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Container extends AbstractCollection<IndexedRectangle> {
    private Set<IndexedRectangle> rectangles;

    public Container(Collection<IndexedRectangle> rectangles) {
        this.rectangles = new HashSet(rectangles);
    }

    public Container() {
        this.rectangles = new HashSet();
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

    // Checks whether x,y is contained by the container
    public boolean contains(int x, int y) {
        for (Rectangle r : this) {
            if (r.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Point p) {
        return contains(p.x, p.y);
    }


    public boolean isOccupied(int x, int y) {
        return contains(x, y) && !isBounding(x, y);
    }

    public boolean isOccupied(Point p) {
        return isOccupied(p.x, p.y);
    }

    public boolean canPlaceRectangle(int x, int y, int width, int height) {
        int maxX = x + width;
        int maxY = y + height;

        for (int newX = x; newX < maxX; newX++) {
            if (isOccupied(newX, y) || isOccupied(newX, maxY)) {
                return false;
            }
        }

        for (int newY = y; newY < maxY; newY++) {
            if (isOccupied(x, newY) || isOccupied(maxX, newY)) {
                return false;
            }
        }

        for (int dx = 1; dx < width; dx++) {
            for (int dy = 1; dy < height; dy++) {
                if (contains(x + dx, y + dy)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canPlaceRectangle(int x, int y, Rectangle r) {
        return canPlaceRectangle(x, y, r.width, r.height);
    }

    public boolean canPlaceRectangle(Point p, int width, int height) {
        return canPlaceRectangle(p.x, p.y, width, height);
    }

    public boolean canPlaceRectangle(Point p, Rectangle r) {
        return canPlaceRectangle(p.x, p.y, r.width, r.height);
    }

    public boolean isBounding(int x, int y) {
        return (contains(x, y) &&
                ((x == 0 && y == 0) ||
                        !((x == 0 || contains(x - 1, y - 1) &&
                                contains(x - 1, y + 1)) &&
                                (y == 0 || contains(x - 1, y - 1) &&
                                        contains(x + 1, y - 1)) &&
                                contains(x + 1, y + 1))));
    }

    public boolean isBounding(Point p) {
        return isBounding(p.x, p.y);
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
            int height = r.y + r.height;
            if (height > maxHeight) {
                maxHeight = height;
            }
        }
        return maxHeight;
    }

    public Dimension getSize() {
        return new Dimension(this.getWidth(), this.getHeight());
    }


    public int getArea() {
        return getWidth() * getHeight();
    }

    // Gets the bounding rectangle of container
    public Rectangle getBounds() {
        return new Rectangle(getSize());
    }

    // Gets the bounding polygon of container
    public Polygon getPolygon() {
        Rectangle bounds = getBounds();
        Set<Point> points = new HashSet<>(3 * size());
        for (int x = bounds.width; x > 0; x--) {
            boolean lastContained = false;
            for (int y = bounds.height; y > 0; y--) {
                if (!contains(x, y)) {
                    lastContained = false;
                    continue;
                }
                if (!lastContained && isBounding(x, y)) {
                    points.add(new Point(x, y));
                }
                lastContained = true;
            }
        }

        int nPoints = points.size() + 1;

        int[] xPoints, yPoints;
        xPoints = new int[nPoints];
        yPoints = new int[nPoints];

        // add 0,0
        xPoints[0] = 0;
        yPoints[0] = 0;

        int i = 1;
        for (Point p : points) {
            xPoints[i] = p.x;
            yPoints[i] = p.y;
            i++;
        }
        return new Polygon(xPoints, yPoints, nPoints);
    }

    public Set<Point> getBoundingLine() {
        Set<Point> points = new HashSet();
        for (Rectangle r : this) {
            int maxX = r.x + r.width;
            for (int dy = 0; dy <= r.height; dy++) {
                int y = r.y + dy;
                points.add(new Point(r.x, y));
                points.add(new Point(maxX, y));
            }

            int maxY = r.y + r.height;
            for (int dx = 0; dx <= r.width; dx++) {
                int x = r.x + dx;
                points.add(new Point(x, r.y));
                points.add(new Point(x, maxY));
            }
        }
        points.removeIf(p -> !isBounding(p));
        return points;
    }

    public void printRectangles() {
        System.out.println("Rectangles:");
        for (IndexedRectangle r : this) {
            System.out.printf("i=%d %dx%d (%d,%d)\n", r.getIndex(), r.width, r.height, r.x, r.y);
        }

        for (int y = getHeight(); y >= 0; y--) {
            System.out.printf("%d\t", y);
            for (int x = 0; x <= getWidth(); x++) {
                if (contains(x, y)) {
                    if (isBounding(x, y)) {
                        System.out.print("*");
                    } else {
                        System.out.print("+");
                    }
                } else {
                    System.out.print(" ");
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.print("\t");
        for (int x = 0; x <= getWidth(); x++) {
            System.out.printf("%d ", x);
        }
        System.out.println();
        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("placement of rectangles:\n");
        rectangles.forEach((r) -> sb.append(r.toString()).append("\n"));
        return sb.toString();
    }
}

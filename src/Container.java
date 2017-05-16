import java.awt.Point;
import java.awt.Dimension;
import java.awt.Polygon;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Container extends AbstractCollection<IndexedRectangle> {
    private Set<IndexedRectangle> rectangles;

    Container(Collection<IndexedRectangle> rectangles) {
        this.rectangles = new HashSet<>(rectangles);
    }

    Container() {
        this.rectangles = new HashSet<>();
    }

    public Iterator<IndexedRectangle> iterator() {
        return rectangles.iterator();
    }

    @Override
    public boolean add(IndexedRectangle r) {
        return rectangles.add(r);
    }

    public boolean remove(IndexedRectangle r) {
        return rectangles.remove(r);
    }

    @Override
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
                ((x == 0 && y == 0) || !(
                        (x == 0 || contains(x - 1, y - 1) &&
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rectangles:\n");
        rectangles.forEach(r -> sb.append(r.toString()).append("\n"));

        for (int y = getHeight(); y >= 0; y--) {
            sb.append(String.format("%d\t", y));
            for (int x = 0; x <= getWidth(); x++) {
                if (contains(x, y)) {
                    if (isBounding(x, y)) {
                        sb.append("*");
                    } else {
                        sb.append("+");
                    }
                } else {
                    sb.append(" ");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        sb.append("\t");
        for (int x = 0; x <= getWidth(); x++) {
            sb.append(String.format("%d ", x));
        }
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }

    private class Plane {
        private ArrayList<ArrayList<Integer>> rows;

        Plane() {
            rows = new ArrayList<>();
        }

        Plane(int height) {
            rows = new ArrayList<>(height);
        }

        public int getHeight() {
            return rows.size();
        }

        public int getWidth() {
            int maxWidth = 0;
            for (ArrayList<Integer> row:rows) {
                int width = row.size();
                if (width <= maxWidth) {
                    continue;
                }
                for (; width != maxWidth; width--) {
                    if (row.get(width-1) != 0) {
                        maxWidth = width;
                        break;
                    }
                }
            }
            return maxWidth;
        }

        private ArrayList<Integer> getRow(int y) {
            int height = rows.size();
            if (height > y) {
                return rows.get(y);
            }

            ArrayList<Integer> row = new ArrayList<>();
            rows.ensureCapacity(y);
            for (int i = height; i < y; i++) {
                rows.add(new ArrayList<>());
            }
            rows.add(row);
            return row;
        }

        private int getValue(int x, int y) {
            ArrayList<Integer> row = getRow(y);
            if (x < row.size()) {
                return row.get(x);
            }
            return 0;
        }

        void add(int x, int y) {
            ArrayList<Integer> row = getRow(y);

            int width = row.size();
            if (width > x) {
                row.set(x, row.get(x) + 1);
            } else {
                row.ensureCapacity(x);
                for (int i = width; i < x; i++) {
                    row.add(0);
                }
                row.add(1);
            }
        }

        void remove(int x, int y) {
            ArrayList<Integer> row = getRow(y);
            row.set(x, row.get(x) - 1);
        }

        boolean contains(int x, int y) {
            return getValue(x,y) > 0;
        }

        private boolean isSurrounded(int x, int y) {
            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++) {
                    if (getValue(x+dx, x+dy) != 1) {
                        // Either empty or another bound
                        return false;
                    }
                }
            }
            return true;
        }

        public boolean isOccupied(int x, int y) {
            int val = getValue(x,y);
            if (val == 0) {
                return false;
            } else if (x == 0 || y == 0) {
                if (x == 0 && y == 0) {
                    return val == 1;
                }
                return val == 2;
            } else if (val == 4) {
                return true;
            } else if (val > 1) {
                return false;
            }
            // val == 1
            return isSurrounded(x,y);
        }

        public boolean isBounding(int x, int y) {
            int val = getValue(x,y);
            if (val > 1) {
                return true;
            } else if (val == 0) {
                return false;
            } else if (x == 0 || y == 0) {
                return true;
            }
            // val == 1
            return !isSurrounded(x,y);
        }

    }

    public static class WithPlane extends Container {
        private Plane plane;

        public WithPlane(Case c) {
            if (c.isHeightFixed()) {
                plane = new Plane(c.getHeight());
            } else {
                plane = new Plane();
            }
        }

        @Override
        public boolean contains(int x, int y) {
            return plane.contains(x, y);
        }

        @Override
        public boolean add(IndexedRectangle r) {
            for (int x = r.x; x <= r.x+r.width; x++) {
                for (int y = r.y; y <= r.y+r.height; y++) {
                    plane.add(x,y);
                }
            }
            return super.add(r);
        }

        @Override
        public boolean remove(IndexedRectangle r) {
            for (int x = r.x; x <= r.x+r.width; x++) {
                for (int y = r.y; y <= r.y+r.height; y++) {
                    plane.remove(x,y);
                }
            }
            return super.remove(r);
        }

        @Override
        public int getWidth() {
            return plane.getWidth();
        }

        @Override
        public int getHeight() {
            return plane.getHeight();
        }

        @Override
        public boolean isBounding(int x, int y) {
            return plane.isBounding(x,y);
        }

        @Override
        public boolean isOccupied(int x, int y) {
            return plane.isOccupied(x,y);
        }
    }
}

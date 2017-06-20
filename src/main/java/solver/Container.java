package solver;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Polygon;
import java.util.*;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Container extends AbstractCollection<IndexedRectangle> {
    private Set<IndexedRectangle> rectangles;

    Container(Collection<? extends IndexedRectangle> rectangles) {
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
    boolean contains(int x, int y) {
        for (Rectangle r : this) {
            if (r.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    boolean contains(Point p) {
        return contains(p.x, p.y);
    }

    boolean hasEmptyNeighbour(int x, int y) {
        for (int dy = -1; dy < 2; dy++) {
            int newY = y + dy;
            for (int dx = -1; dx < 2; dx++) {
                int newX = x + dx;
                if (newX == x && newY == y) {
                    continue;
                }
                if (!contains(newX, newY)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isOccupied(int x, int y) {
        if (!contains(x,y)) {
            return false;
        } else if (x == 0 && y == 0) {
            return true;
        } else if (x == 0) {
            return (contains(x, y+1) && contains(x+1, y+1) &&
                    contains(x+1,y)&&
                    contains(x+1, y-1) && contains(x, y-1));
        } else if (y == 0) {
            return (contains(x-1, y+1) && contains(x, y+1) && contains(x+1, y+1) &&
                    contains(x-1, y) && contains(x+1, y));
        }
        return !hasEmptyNeighbour(x,y) ||(contains(x,y+1) && contains(x+1, y+1) && contains(x+1, y));
    }

    boolean isOccupied(Point p) {
        return isOccupied(p.x, p.y);
    }

    boolean isBounding(int x, int y) {
        if (!contains(x,y)) {
            return false;
        } else if (x == 0 || y == 0) {
           return true;
        }
        return hasEmptyNeighbour(x,y);
    }

    boolean isBounding(Point p) {
        return isBounding(p.x, p.y);
    }

    boolean canPlaceRectangle(int x, int y, int width, int height) {
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

    boolean canPlaceRectangle(int x, int y, Rectangle r) {
        return canPlaceRectangle(x, y, r.width, r.height);
    }

    boolean canPlaceRectangle(Point p, int width, int height) {
        return canPlaceRectangle(p.x, p.y, width, height);
    }

    boolean canPlaceRectangle(Point p, Rectangle r) {
        return canPlaceRectangle(p.x, p.y, r.width, r.height);
    }
    
    int getWidth() {
        int maxWidth = 0;

        for (Rectangle r : this) {
            int width = r.x + r.width;
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    int getHeight() {
        int maxHeight = 0;

        for (Rectangle r : this) {
            int height = r.y + r.height;
            if (height > maxHeight) {
                maxHeight = height;
            }
        }
        return maxHeight;
    }

    int getArea() {
        return getWidth() * getHeight();
    }

    Set<Point> getBoundingLine() {
        Set<Point> points = new HashSet<>();
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
        this.forEach(r -> sb.append(r.toString()).append("\n"));

        for (int y = getHeight(); y >= 0; y--) {
            sb.append(String.format("%d\t", y));
            for (int x = 0; x <= getWidth(); x++) {
                if (contains(x, y)) {
                    if (isBounding(x, y)) {
                        sb.append("▓▓");
                    } else {
                        sb.append("██");
                    }
                } else {
                    sb.append("  ");
                }
                sb.append(" ");
            }
            sb.append("\n");
        }
        sb.append("\t");
        for (int x = 0; x <= getWidth(); x++) {
            String format = "%d ";
            if (x < 10) {
                format += " ";
            }
            sb.append(String.format(format, x));
        }
        return sb.toString();
    }

    public static class WithPlane extends Container {
        private Plane plane;
        private Set<Point> boundingLine;

        WithPlane(Case c) {
            if (c.isHeightFixed()) {
                plane = new Plane(c.getHeight());
            } else {
                plane = new Plane();
            }
            boundingLine = new HashSet<>();
        }

        WithPlane(Collection<? extends IndexedRectangle> rectangles) {
            super(rectangles);
            boundingLine = new HashSet<>();
            plane = new Plane();
            this.addAll(rectangles);
        }


        @Override
        boolean contains(int x, int y) {
            return plane.contains(x, y);
        }

        private void updateBoundingLine(Set<Point> points) {
            for (Point p : points) {
                if (isBounding(p)) {
                    boundingLine.add(p);
                } else {
                    boundingLine.remove(p);
                }
            }
        }

        @Override
        public boolean add(IndexedRectangle r) {
            Set<Point> edges = new HashSet<>(2 * r.width + 2 * r.height - 4);

            int maxX = r.x + r.width;
            int maxY = r.y + r.height;
            for (int x = r.x; x <= maxX; x++) {
                edges.add(new Point(x, maxY));
                edges.add(new Point(x, r.y));
                for (int y = r.y; y <= maxY; y++) {
                    plane.add(x, y);
                    edges.add(new Point(maxX, y));
                    edges.add(new Point(r.x, y));
                }
            }
            updateBoundingLine(edges);
            return super.add(r);
        }

        @Override
        public boolean remove(IndexedRectangle r) {
            Set<Point> edges = new HashSet<>(2 * r.width + 2 * r.height - 4);

            int maxX = r.x + r.width;
            int maxY = r.y + r.height;
            for (int x = r.x; x <= maxX; x++) {
                edges.add(new Point(x, maxY));
                edges.add(new Point(x, r.y));
                for (int y = r.y; y <= maxY; y++) {
                    plane.remove(x, y);
                    edges.add(new Point(maxX, y));
                    edges.add(new Point(r.x, y));
                }
            }
            updateBoundingLine(edges);
            return super.remove(r);
        }

        @Override
        int getWidth() {
            return plane.getWidth();
        }

        @Override
        int getHeight() {
            return plane.getHeight();
        }

        @Override
        boolean isOccupied(int x, int y) {
            return plane.isOccupied(x, y) || super.isOccupied(x,y);
        }

        @Override
        boolean hasEmptyNeighbour(int x, int y) {
            return plane.hasEmptyNeighbour(x,y);
        }

        @Override
        Set<Point> getBoundingLine() {
            return new HashSet<>(boundingLine);
        }

        @Override
        public String toString() {
            return String.format("%s\nPlane:\n%s", super.toString(), plane.toString());
        }
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
            for (int i = rows.size()-1; i >= 0; i--) {
                for (int v : rows.get(i)) {
                    if (v != 1) {
                        return i;
                    }
                }
            }
            return 0;
        }

        int getWidth() {
            int maxWidth = 0;
            for (ArrayList<Integer> row : rows) {
                int width = row.size()-1;
                if (width <= maxWidth) {
                    continue;
                }
                for (; width != maxWidth; width--) {
                    if (row.get(width) != 0) {
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
            return getValue(x, y) > 0;
        }

        boolean hasEmptyNeighbour(int x, int y) {
            for (int dy = -1; dy < 2; dy++) {
                int newY = y + dy;
                ArrayList<Integer> row = getRow(newY);
                for (int dx = -1; dx < 2; dx++) {
                    int newX = x + dx;
                    if (newX == x && newY == y) {
                        continue;
                    }
                    if (row.size() <= newX || row.get(newX) == 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        boolean isOccupied(int x, int y) {
            int val = getValue(x, y);
            if (val == 0) return false;
            if (x == 0 || y == 0) return x == y || val == 2;
            return val == 4;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int y = getHeight(); y >= 0; y--) {
                sb.append(String.format("%d\t", y));
                for (int x = 0; x <= getWidth(); x++) {
                    sb.append(getValue(x, y)).append("  ");
                }
                sb.append("\n");
            }
            sb.append("\t");
            for (int x = 0; x <= getWidth(); x++) {
                String format = "%d ";
                if (x < 10) {
                    format += " ";
                }
                sb.append(String.format(format, x));
            }
            return sb.toString();
        }
    }
}

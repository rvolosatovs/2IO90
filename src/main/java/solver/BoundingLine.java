package solver;

import java.awt.Point;
import java.util.*;

/**
 * Created by rvolosatovs on 6/20/17.
 */
public class BoundingLine extends AbstractCollection<Point> {
    private Set<Point> bound;
    private Set<Point> masked;

    BoundingLine() {
        bound = new HashSet<>();
        bound.add(new Point(0, 0));
        masked = new HashSet<>();
    }

    BoundingLine(int height) {
        int size = height * height;
        bound = new HashSet<>(size);
        bound.add(new Point(0, 0));
        masked = new HashSet<>(size);
    }

    public Iterator<Point> iterator() {
        return bound.iterator();
    }

    @Override
    public int size() {
        return bound.size();
    }

    private void mask(Point p) {
        masked.add(p);
        bound.remove(p);
    }

    boolean isMasked(Point p) {
        return masked.contains(p);
    }

    boolean isMasked(int x, int y) {
        return masked.contains(new Point(x, y));
    }

    boolean isBounding(Point p) {
        return bound.contains(p);
    }

    @Override
    public boolean add(Point p) {
        return bound.add(p);
    }

    boolean add(int x, int y) {
        return add(new Point(x, y));
    }

    private void unmaskOrAdd(Point p) {
        if (isMasked(p)) {
            masked.remove(p);
        } else {
            add(p);
        }
    }

    public boolean addIfNotMasked(Point p) {
        return !isMasked(p) && add(p);
    }

    void add(Rectangle r) {
        int maxX = r.x + r.width;
        int maxY = r.y + r.height;

        for (int x = r.x; x < maxX; x++) {
            //unmaskOrAdd(new Point(x, maxY);
            addIfNotMasked(new Point(x, maxY));
            mask(new Point(x, r.y));
        }

        for (int y = r.y; y < maxY; y++) {
            //unmaskOrAdd(new Point(maxX, y);
            addIfNotMasked(new Point(maxX, y));
            mask(new Point(r.x, y));
        }
        addIfNotMasked(new Point(maxX, maxY));
    }

    boolean remove(Point p) {
        return bound.remove(p);
    }

    boolean remove(int x, int y) {
        return bound.remove(new Point(x, y));
    }

    void remove(Rectangle r) {
        int maxX = r.x + r.width;
        int maxY = r.y + r.height;

        for (int x = r.x; x <= r.x + r.width; x++) {
            add(new Point(x, r.y));
            addIfNotMasked(new Point(x, maxY));
        }

        for (int y = r.y + 1; y < r.y + r.height; y++) {
            add(new Point(r.x, y));
            addIfNotMasked(new Point(maxX, y));
        }
    }

    boolean contains(int x, int y) {
        return contains(new Point(x, y));
    }

    boolean contains(Point p) {
        return bound.contains(p);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Point p : bound) {
            sb.append(String.format("(%d,%d),", p.x, p.y));
        }
        return sb.substring(0, sb.length() - 1);
    }
}

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

    BoundingLine(BoundingLine l) {
        bound = new HashSet<>(l.bound.size());
        for (Point p : l.bound) {
            bound.add(new Point(p));
        }

        masked = new HashSet<>(l.masked.size());
        for (Point p : l.masked) {
            masked.add(new Point(p));
        }
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

    private boolean unmask(Point p) {
        return masked.remove(p);
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

    public boolean unmaskIfNotMasked(Point p) {
        return !isMasked(p) && unmask(p);
    }

    public boolean addIfNotMasked(Point p) {
        return !isMasked(p) && add(p);
    }

    void addAndUnmask(Point p) {
        add(p);
        unmask(p);
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

    @Override
    public Object clone() {
        return new BoundingLine(this);
    }
}

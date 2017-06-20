package solver;

import java.awt.Point;
import java.util.*;

/**
 * Created by rvolosatovs on 6/20/17.
 */
public class BoundingLine extends AbstractCollection<Point>{
    private Set<Point> points;

    BoundingLine() {
        points = new HashSet<>();
        points.add(new Point(0,0));
    }

    BoundingLine(int height) {
        points = new HashSet<>(height*height);
        points.add(new Point(0,0));
    }

    BoundingLine(Set<Point> points) {
        this.points = new HashSet<>(points);
    }

    public Iterator<Point> iterator() {
        return points.iterator();
    }

    @Override
    public int size() {
        return points.size();
    }


    @Override
    public boolean add(Point p) {
        return points.add(p);
    }

    boolean add(int x, int y) {
        return add(new Point(x,y));
    }

    void add(Rectangle r) {
        int maxX = r.x+r.width;
        int maxY = r.y+r.height;

        Point p;
        for (int x = r.x; x <= r.x+r.width; x++) {
            p = new Point(x, maxY);
            if (!contains(p)) add(p);
            else remove(p);
        }

            for (int y = r.y; y < r.y+r.height; y++) {
                p = new Point(maxX,y);
                if (!contains(p)) add(p);
                else remove(p);
            }
    }

    boolean remove(Point p) {
        return points.remove(p);
    }

    void remove(Rectangle r) {
        int maxX = r.x+r.width;
        int maxY = r.y+r.height;

        for (int x = r.x; x <= r.x+r.width; x++) {
            add(new Point(x, r.y));
            add(new Point(x, maxY));
        }

        for (int y = r.y+1; y < r.y+r.height; y++) {
            add(new Point(r.x, y));
            add(new Point(maxX, y));
        }
    }

    boolean contains(int x, int y) {
        return contains(new Point(x,y));
    }
    boolean contains(Point p) {
        return points.contains(p);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Point p: points) {
            sb.append(String.format("(%d,%d),",p.x,p.y));
        }
        return sb.substring(0,sb.length()-1);
    }
}

import java.awt.Point;
import java.awt.Polygon;
import java.util.*;

/**
 * Created by s154563 on 8-5-2017.
 */
public class GreedyPacker implements Packer {
    public Set<Point> pointsAvailable(Container container, Rectangle r) {
        Set<Point> points = container.getBoundingLine();

        System.out.println(points);

        for (Point p : points) {
            if (!points.contains(new Point(p.x - 1, p.y))) {
                int minX = Math.max(0, p.x - r.width);
                for (int newX = p.x - 1; newX >= minX; newX--) {
                    if (!container.contains(newX, p.y) || container.isBounding(newX, p.y)) {
                        System.out.println("adding " + newX + " " + p.y);
                        points.add(new Point(newX, p.y));
                    }
                }
                break;
            }
            System.out.println("done counting X edge");

            if (!points.contains(new Point(p.x, p.y - 1))) {
                int minY = Math.max(0, p.y - r.height);
                for (int newY = p.y - 1; newY >= minY; newY--) {
                    if (!container.contains(p.x, newY) || container.isBounding(p.x, newY)) {
                        System.out.println("adding " + p.x + " " + newY);
                        points.add(new Point(p.x, newY));
                    }
                }
                break;
            }
            System.out.println("done counting Y edge");
        }

        Collection<Point> free = new HashSet(points.size());
        Collection<Point> occupied = new HashSet(points.size());

        points.removeIf(p -> {
            if (free.contains(p)) {
                return false;
            }
            if (occupied.contains(p)) {
                return true;
            }

            for (int dy = r.height; dy > 0; dy--) {
                int newY = p.y + dy;
                for (int dx = r.width; dx > 0; dx--) {
                    int newX = p.x + dx;
                    if (!container.canPlaceRectangle(newX, newY)) {
                        occupied.add(p);
                        return true;
                    }
                }
            }
            free.add(p);
            return false;
        });
        return points;
    }

    @Override
    public Container Pack(Case c) {
        ArrayList<IndexedRectangle> rectangles = Util.sortByArea(c.getRectangles());

        Container container = new Container();
        container.add(rectangles.get(0));

        int maxHeight = Integer.MAX_VALUE;
        boolean fixedHeight = c.isHeightFixed();
        if (fixedHeight) {
            maxHeight = c.getHeight();
        }
        container.printRectangles();

        for (int i = 1; i < rectangles.size(); i++) {
            IndexedRectangle r = rectangles.get(i);
            Set<Point> points = pointsAvailable(container, r);

            int minArea = Integer.MAX_VALUE;
            Point minPoint = null;
            boolean needsRotation = false;
            boolean rotated = false;

            container.add(r);
            while (true) {
                for (Point p : points) {
                    if (fixedHeight && (p.y + r.height) > maxHeight) {
                        continue;
                    }

                    System.out.printf("(%d,%d)\n", p.x, p.y);

                    r.setLocation(p);
                    int area = container.getArea();
                    if (area <= minArea) {
                        if (area < minArea || p.x < minPoint.x || p.y < minPoint.y)  {
                            needsRotation = rotated;
                            minPoint = p;
                            minArea = area;
                        }
                    }
                }

                if (!c.areRotationsAllowed() || rotated) {
                    break;
                }

                r.rotate();
                rotated = true;
            }
            if (!needsRotation && rotated) {
                r.rotate();
            }
            r.setLocation(minPoint);
            container.printRectangles();
        }

        return container;
    }
}

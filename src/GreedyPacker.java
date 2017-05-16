import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by s154563 on 8-5-2017.
 */
public class GreedyPacker implements Packer {
    @Override
    public Container Pack(Case c) {
        List<IndexedRectangle> rectangles = c.getRectangles();
        Util.sortByArea(rectangles);

        int maxHeight = Integer.MAX_VALUE;
        boolean fixedHeight = c.isHeightFixed();
        if (fixedHeight) {
            maxHeight = c.getHeight();
        }

        Container container = new Container();
        for (int i = 0; i < rectangles.size(); i++) {
            IndexedRectangle r = rectangles.get(i);

            Set<Point> points;
            if (container.size() != 0) {
                points = container.getBoundingLine();
            } else {
                points = new HashSet();
                points.add(new Point(0, 0));
            }

            Set<Point> additionalPoints = new HashSet<>(points.size());
            for (Point p : points) {
                if (!points.contains(new Point(p.x - 1, p.y))) {
                    for (int x = Math.max(0, p.x - r.width); x < p.x; x++) {
                        if (container.isOccupied(x, p.y)) {
                            break;
                        }
                        additionalPoints.add(new Point(x, p.y));
                    }
                }

                if (!points.contains(new Point(p.x, p.y - 1))) {
                    for (int y = Math.max(0, p.y - r.height); y < p.y; y++) {
                        if (container.isOccupied(p.x, y)) {
                            break;
                        }
                        additionalPoints.add(new Point(p.x, y));
                    }
                }
            }
            points.addAll(additionalPoints);

            int minArea = Integer.MAX_VALUE;
            Point minPoint = null;
            boolean needsRotation = false;
            boolean rotated = false;

            Set<Point> fittingPointsRotated = null;
            if (c.areRotationsAllowed()) {
                fittingPointsRotated = new HashSet(points.size());
                r.rotate();
                for (Point p : points) {
                    if (container.canPlaceRectangle(p, r)) {
                        fittingPointsRotated.add(p);
                    }
                }
                r.rotate();
            }

            Set<Point> fittingPoints = new HashSet(points.size());
            points.forEach(p -> {
                if (container.canPlaceRectangle(p, r)) {
                    fittingPoints.add(p);
                }
            });

            container.add(r);
            for (Point p : fittingPoints) {
                if (fixedHeight && (p.y + r.height) > maxHeight) {
                    continue;
                }

                r.setLocation(p);
                int area = container.getArea();
                if (area <= minArea) {
                    if (area < minArea || p.x < minPoint.x || p.y < minPoint.y) {
                        minPoint = p;
                        minArea = area;
                    }
                }
            }

            if (c.areRotationsAllowed()) {
                r.rotate();

                for (Point p : fittingPointsRotated) {
                    if (fixedHeight && (p.y + r.height) > maxHeight) {
                        continue;
                    }

                    r.setLocation(p);
                    int area = container.getArea();
                    if (area <= minArea) {
                        if (area < minArea || p.x < minPoint.x || p.y < minPoint.y) {
                            needsRotation = true;
                            minPoint = p;
                            minArea = area;
                        }
                    }
                }

                if (!needsRotation) {
                    r.rotate();
                }
            }
            r.setLocation(minPoint);
        }
        return container;
    }
}

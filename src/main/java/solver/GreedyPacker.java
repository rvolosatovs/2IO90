package solver;

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

        BoundingLine boundingLine;

        int maxHeight = Integer.MAX_VALUE;
        boolean fixedHeight = c.isHeightFixed();
        if (fixedHeight) {
            maxHeight = c.getHeight();
            boundingLine = new BoundingLine(maxHeight);
        } else {
            boundingLine = new BoundingLine();
        }

        Rectangle lastRectangle = null;

        Set<Point> additionalPoints = new HashSet<>();

        Container container = new Container();
        for (IndexedRectangle r : rectangles) {
            //int maxY = lastRectangle.y + lastRectangle.height;
            //for (int x = lastRectangle.x; x >= 0; x--) {
            //    Point p = new Point(x,maxY);
            //    if (boundingLine.contains(p) || container.isOccupied(p)) {
            //        break;
            //    }
            //    boundingLine.add(p);
            //}
            //int maxX = lastRectangle.x + lastRectangle.width;
            //for (int y = lastRectangle.y; y >= 0; y--) {
            //    Point p = new Point(maxX,y);
            //    if (boundingLine.contains(p) || container.isOccupied(p)) {
            //        break;
            //    }
            //    boundingLine.add(p);
            //}

            System.out.println(boundingLine);

            int minArea = Integer.MAX_VALUE;
            Point minPoint = null;
            boolean needsRotation = false;

            Set<Point> fittingPointsRotated = null;
            if (c.areRotationsAllowed()) {
                fittingPointsRotated = new HashSet<>(boundingLine.size());
                r.rotate();
                for (Point p : boundingLine) {
                    if (container.canPlaceRectangle(p, r)) {
                        fittingPointsRotated.add(p);
                    }
                }
                r.rotate();
            }

            Set<Point> fittingPoints = new HashSet<>(boundingLine.size());
            for (Point p : boundingLine) {
                if (container.canPlaceRectangle(p, r)) {
                    fittingPoints.add(p);
                }
            }

            for (Point p : fittingPoints) {
                if (fixedHeight && (p.y + r.height) > maxHeight) {
                    continue;
                }

                r.setLocation(p);
                container.add(r);
                int area = container.getArea();
                if (area <= minArea) {
                    if (area < minArea || p.x < minPoint.x || p.y < minPoint.y) {
                        minPoint = p;
                        minArea = area;
                    }
                }
                container.remove(r);
            }

            if (c.areRotationsAllowed()) {
                r.rotate();
                for (Point p : fittingPointsRotated) {
                    if (fixedHeight && (p.y + r.height) > maxHeight) {
                        continue;
                    }

                    r.setLocation(p);
                    container.add(r);
                    int area = container.getArea();
                    if (area <= minArea) {
                        if (area < minArea || p.x < minPoint.x || p.y < minPoint.y) {
                            needsRotation = true;
                            minPoint = p;
                            minArea = area;
                        }
                    }
                    container.remove(r);
                }

                if (!needsRotation) {
                    r.rotate();
                }
            }

            r.setLocation(minPoint);
            container.add(r);
            boundingLine.add(r);
        }

        return container;
    }
}

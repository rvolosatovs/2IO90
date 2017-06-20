package solver;

import java.awt.Point;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeoutException;

/**
 * Created by s154563 on 8-5-2017.
 */
public class GreedyPacker implements Packer {

    public Container Pack(Case c) throws InterruptedException {
        List<IndexedRectangle> rectangles = c.getRectangles();
        Util.sortByArea(rectangles);

        BoundingLine boundingLine;

        Container container;

        int maxHeight = Integer.MAX_VALUE;
        boolean fixedHeight = c.isHeightFixed();
        if (fixedHeight) {
            maxHeight = c.getHeight();
            //container = new Container.WithPlane(maxHeight);
            container = new Container();
            boundingLine = new BoundingLine(maxHeight);
        } else {
            //container = new Container.WithPlane();
            container = new Container();
            boundingLine = new BoundingLine();
        }

        for (int i = 0; i < rectangles.size(); i++) {
            if (System.currentTimeMillis() - PackingSolver.startTime > 270000) {
                rectangles = rectangles.subList(i, rectangles.size());
                if (!fixedHeight) {
                    maxHeight = container.getHeight();
                    c.setContainerHeight(maxHeight);
                }

                throw new InterruptedException(container, rectangles);
            }

            IndexedRectangle r = rectangles.get(i);

            int minArea = Integer.MAX_VALUE;
            Point minPoint = null;

            for (Point p : boundingLine) {
                if ((fixedHeight && (p.y + r.height) > maxHeight) || !container.canPlaceRectangle(p,r)) {
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
                boolean needsRotation = false;

                r.rotate();
                for (Point p : boundingLine) {
                    if ((fixedHeight && (p.y + r.height) > maxHeight) || !container.canPlaceRectangle(p,r)) {
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


package solver;

import java.awt.Point;
import java.util.List;

/**
 * Created by s154563 on 8-5-2017.
 */
public class GreedyPacker implements Packer {
    @Override
    public Container Pack(Case c) {
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

        for (IndexedRectangle r : rectangles) {
            int minArea = Integer.MAX_VALUE;
            Point minPoint = null;

            outer:
            for (Point p : boundingLine) {
                int height = p.y + r.height;
                if (fixedHeight && height > maxHeight) {
                    continue;
                }

                int width = p.x+r.width;
                for (int x = p.x; x < width; x++) {
                    for (int y = p.y; y < height; y++) {
                        if (boundingLine.isMasked(x, y)) {
                            continue outer;
                        }
                    }
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
                outer:
                for (Point p : boundingLine) {
                    int height = p.y + r.height;
                    if (fixedHeight && height > maxHeight) {
                        continue;
                    }

                    int width = p.x+r.width;
                    for (int x = p.x; x < width; x++) {
                        for (int y = p.y; y < height; y++) {
                            if (boundingLine.isMasked(x, y)) {
                                continue outer;
                            }
                        }
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

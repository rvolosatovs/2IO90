package solver;

import java.awt.Point;
import java.util.List;

/**
 * Created by s154563 on 8-5-2017.
 */
public class GreedyPacker implements Packer {

    public Container Pack(Case c) throws InterruptedException {
        List<IndexedRectangle> rectangles = c.getRectangles();
        Util.sortByArea(rectangles);

        BoundingLine boundingLine;

        Container container = new Container(rectangles.size());

        int maxHeight = Integer.MAX_VALUE;
        boolean fixedHeight = c.isHeightFixed();
        if (fixedHeight) {
            maxHeight = c.getHeight();
            boundingLine = new BoundingLine(maxHeight);
        } else {
            boundingLine = new BoundingLine();
        }

        int containerWidth = 0;
        int containerHeight = 0;

        for (int i = 0; i < rectangles.size(); i++) {
            if (System.currentTimeMillis() - PackingSolver.startTime > 270000) {
                rectangles = rectangles.subList(i, rectangles.size());
                if (!fixedHeight) {
                    c.setContainerHeight(containerHeight);
                }

                throw new InterruptedException(container, rectangles);
            }

            IndexedRectangle r = rectangles.get(i);

            int minArea = Integer.MAX_VALUE;
            Point minPoint = null;

            int oldHeight = containerHeight;
            int oldWidth = containerWidth;

            outer:
            for (Point p : boundingLine) {
                int height = p.y + r.height;
                if (fixedHeight && height > maxHeight) {
                    continue;
                }

                int width = p.x + r.width;
                for (int x = p.x; x < width; x++) {
                    for (int y = p.y; y < height; y++) {
                        if (boundingLine.isMasked(x, y)) {
                            continue outer;
                        }
                    }
                }

                containerHeight = Math.max(oldHeight, height);
                containerWidth = Math.max(oldWidth, width);
                int area = containerWidth * containerHeight;
                if (area <= minArea) {
                    if (area < minArea || p.x < minPoint.x || p.y < minPoint.y) {
                        minPoint = p;
                        minArea = area;
                    }
                }
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

                    int width = p.x + r.width;
                    for (int x = p.x; x < width; x++) {
                        for (int y = p.y; y < height; y++) {
                            if (boundingLine.isMasked(x, y)) {
                                continue outer;
                            }
                        }
                    }

                    containerHeight = Math.max(oldHeight, height);
                    containerWidth = Math.max(oldWidth, width);
                    int area = containerWidth * containerHeight;
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
            container.add(r);
            boundingLine.add(r);

            containerHeight = Math.max(oldHeight, r.y + r.height);
            containerWidth = Math.max(oldWidth, r.x + r.width);
        }

        return container;
    }
}


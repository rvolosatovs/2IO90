package solver;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by berrietrippe on 22/05/2017.
 */
public class BrutePacker implements Packer {

    int minArea = Integer.MAX_VALUE; // Global variable keeping track of the most optimal area found yet
    Container finalContainer; // Global variable keeping track of the most optimal solution found yet

    @Override
    public Container Pack(Case c) {
        Container container = new Container();
        int maxHeight;

        BoundingLine boundingLine;

        if (c.isHeightFixed()) {
            maxHeight = c.getHeight();
            boundingLine = new BoundingLine(maxHeight);
        } else {
            maxHeight = 0;
            boundingLine = new BoundingLine();
        }

        int containerWidth = 0;
        int containerHeight = 0;

        calculateCombination(container, 0, c.getRectangles(),boundingLine, c.areRotationsAllowed(), c.isHeightFixed(), maxHeight, containerWidth, containerHeight);
        return finalContainer;
    }

    public void calculateCombination(Container c, int index, List<IndexedRectangle> rectangles, BoundingLine boundingLine, boolean rotationsAllowed, boolean fixedHeight, int maxHeight, int containerWidth, int containerHeight) {
        if (index == rectangles.size()) {
            int area = containerWidth * containerHeight;
            if (area <= minArea) {
                if (area < minArea) {
                    minArea = c.getArea();
                    List<IndexedRectangle> newRectangles = new ArrayList<>();
                    rectangles.forEach((r) -> newRectangles.add((IndexedRectangle) r.clone()));
                    finalContainer = new Container(newRectangles);
                }
            }
        } else {
            for (int i = index; i < rectangles.size(); i++) {

                int oldHeight = containerHeight;
                int oldWidth = containerWidth;

                IndexedRectangle r = rectangles.get(i);
                index++;
                Set<Point> points = new HashSet<>(boundingLine);

                for (Point p : points) {
                    boolean shouldPlaceRectangle = true;
                    if (rotationsAllowed) {
                        r.rotate();
                        int height = p.y + r.height;
                        if (fixedHeight && (height > maxHeight)) {
                            shouldPlaceRectangle = false;
                        }

                        int width = p.x + r.width;
                        for (int x = p.x; x < width; x++) {
                            for (int y = p.y; y < height; y++) {
                                if (boundingLine.isMasked(x, y)) {
                                    shouldPlaceRectangle = false;
                                }
                            }
                        }
                        if (shouldPlaceRectangle) {
                            r.setLocation(p);
                            c.add(r);
                            boundingLine.add(r);
                            containerHeight = Math.max(oldHeight, r.y+r.height);
                            containerWidth = Math.max(oldWidth, r.x+r.width);
                            calculateCombination(c, index, rectangles, boundingLine, rotationsAllowed, fixedHeight, maxHeight, containerWidth, containerHeight); //Recursive call
                            containerHeight = oldHeight;
                            containerWidth = oldWidth;
                            boundingLine.remove(r);
                            c.remove(r);
                        }
                        r.rotate();
                    }
                    shouldPlaceRectangle = true;
                    int height = p.y + r.height;
                    if (fixedHeight && (height > maxHeight)) {
                        shouldPlaceRectangle = false;
                    }

                    int width = p.x + r.width;
                    for (int x = p.x; x < width; x++) {
                        for (int y = p.y; y < height; y++) {
                            if (boundingLine.isMasked(x, y)) {
                                shouldPlaceRectangle = false;
                            }
                        }
                    }
                    if (shouldPlaceRectangle) {
                        r.setLocation(p);
                        c.add(r);
                        boundingLine.add(r);
                        containerHeight = Math.max(oldHeight, r.y + r.height);
                        containerWidth = Math.max(oldWidth, r.x + r.width);
                        calculateCombination(c, index, rectangles, boundingLine, rotationsAllowed, fixedHeight, maxHeight, containerWidth, containerHeight); //Recursive call
                        containerHeight = oldHeight;
                        containerWidth = oldWidth;
                        boundingLine.remove(r);
                        c.remove(r);
                    }
                }
            }
        }
    }

}

package solver;

import java.awt.Point;
import java.util.*;
import java.util.List;

/**
 * Created by berrietrippe on 22/05/2017.
 */
public class BrutePacker implements Packer {
    int minArea = Integer.MAX_VALUE; // Global variable keeping track of the most optimal area found yet
    Container finalContainer; // Global variable keeping track of the most optimal solution found yet
    Container container;

    List<IndexedRectangle> rectangles;

    boolean rotationsAllowed;
    boolean fixedHeight;
    int maxHeight;

    @Override
    public Container Pack(Case c) {
        BoundingLine boundingLine;
        if (c.isHeightFixed()) {
            fixedHeight = true;
            maxHeight = c.getHeight();
            boundingLine = new BoundingLine(maxHeight);
        } else {
            fixedHeight = false;
            boundingLine = new BoundingLine();
        }
        rotationsAllowed = c.areRotationsAllowed();
        rectangles = c.getRectangles();
        container = new Container(rectangles.size());

        calculateCombination(0, 0, 0, boundingLine);
        return finalContainer;
    }

    void calculateCombination(int index, int containerWidth, int containerHeight, BoundingLine boundingLine) {
        if (index == rectangles.size()) {
            int area = containerWidth * containerHeight;
            if (area < minArea) {
                minArea = area;
                finalContainer = new Container(rectangles.size());
                for (IndexedRectangle r : container) {
                    finalContainer.add(new IndexedRectangle(r));
                }
            }
        } else {
            int nextIndex = index + 1;
            for (int i = index; i < rectangles.size(); i++) {
                boolean needsSwap = i != index && nextIndex != rectangles.size();

                int oldHeight = containerHeight;
                int oldWidth = containerWidth;

                IndexedRectangle r = rectangles.get(i);
                outer:
                for (Point p : new HashSet<>(boundingLine)) {
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
                    if (containerWidth * containerHeight > minArea) {
                        continue;
                    }
                    if (needsSwap) {
                        rectangles.set(i, rectangles.get(index));
                        rectangles.set(index, r);
                    }
                    r.setLocation(p);
                    BoundingLine newBoundingLine = new BoundingLine(boundingLine);
                    newBoundingLine.add(r);
                    container.add(r);
                    calculateCombination(nextIndex, containerWidth, containerHeight, newBoundingLine);
                    container.remove(r);
                    if (needsSwap) {
                        rectangles.set(index, rectangles.get(i));
                        rectangles.set(i, r);
                    }
                }

                if (rotationsAllowed) {
                    r.rotate();
                    outer:
                    for (Point p : new HashSet<>(boundingLine)) {
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
                        if (containerWidth * containerHeight > minArea) {
                            continue;
                        }
                        if (needsSwap) {
                            rectangles.set(i, rectangles.get(index));
                            rectangles.set(index, r);
                        }
                        r.setLocation(p);
                        BoundingLine newBoundingLine = new BoundingLine(boundingLine);
                        newBoundingLine.add(r);
                        container.add(r);
                        calculateCombination(nextIndex, containerWidth, containerHeight, newBoundingLine);
                        container.remove(r);
                        if (needsSwap) {
                            rectangles.set(index, rectangles.get(i));
                            rectangles.set(i, r);
                        }
                    }
                    r.rotate();
                }
            }
        }
    }
}

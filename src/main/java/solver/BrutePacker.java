package solver;

import java.awt.*;
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
    BoundingLine boundingLine;

    boolean rotationsAllowed;
    boolean fixedHeight;
    int maxHeight;

    @Override
    public Container Pack(Case c) {
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

        calculateCombination(0, 0, 0);
        return finalContainer;
    }

    void calculateCombination(int index, int containerWidth, int containerHeight) {
        if (index == rectangles.size()) {
            System.out.println(container);
            int area = containerWidth * containerHeight;
            if (area < minArea) {
                minArea = area;
                Set<IndexedRectangle> newRectangles = new HashSet<>(container.size());
                container.forEach((r) -> newRectangles.add((IndexedRectangle) r.clone()));
                finalContainer = new Container(newRectangles);
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
                        rectangles.set(i, rectangles.get(nextIndex));
                        rectangles.set(nextIndex, r);
                    }
                    r.setLocation(p);
                    boundingLine.add(r);
                    container.add(r);
                    calculateCombination(nextIndex, containerWidth, containerHeight);
                    boundingLine.remove(r);
                    container.remove(r);
                    if (needsSwap) {
                        rectangles.set(nextIndex, rectangles.get(i));
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
                            rectangles.set(i, rectangles.get(nextIndex));
                            rectangles.set(nextIndex, r);
                        }
                        r.setLocation(p);
                        boundingLine.add(r);
                        container.add(r);
                        calculateCombination(nextIndex, containerWidth, containerHeight);
                        boundingLine.remove(r);
                        container.remove(r);
                        if (needsSwap) {
                            rectangles.set(nextIndex, rectangles.get(i));
                            rectangles.set(i, r);
                        }
                    }
                    r.rotate();
                }
            }
        }
    }
}

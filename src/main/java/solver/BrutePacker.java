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
        if (c.isHeightFixed()){
            maxHeight = c.getHeight();
        } else {
            maxHeight = 0;
        }
        calculateCombination(container, 0, c.getRectangles(), c.areRotationsAllowed(),c.isHeightFixed(),maxHeight);
        return finalContainer;
    }

    public void calculateCombination(Container c, int index, List<IndexedRectangle> rectangles, boolean rotationsAllowed, boolean fixedHeight, int maxHeight) {
        if (index == rectangles.size()) {
            if (c.getArea() < minArea && c.size() == rectangles.size()) {
                minArea = c.getArea();
                List<IndexedRectangle> newRectangles = new ArrayList<>();
                rectangles.forEach((r) -> newRectangles.add((IndexedRectangle)r.clone()));
                finalContainer = new Container(newRectangles);
            }
        } else {
            Set<Point> points;
            for (int i = index; i < rectangles.size(); i++) {

                if (c.size() != 0) {
                    points = c.getBoundingLine();
                } else {
                    points = new HashSet<>();
                    points.add(new Point(0, 0));
                }

                IndexedRectangle r = rectangles.get(i);
                index++;
                for (Point p : points) {
                    if (rotationsAllowed){
                        r.rotate();
                        if (c.canPlaceRectangle(p, r)) {
                            boolean shouldPlaceRectangle = true;
                            if (fixedHeight) {
                                if ((p.y + r.height) > maxHeight){
                                    shouldPlaceRectangle = false;
                                }
                            }
                            if (shouldPlaceRectangle) {
                                r.setLocation(p);
                                c.add(r);
                                calculateCombination(c, index, rectangles, rotationsAllowed, fixedHeight, maxHeight); //Recursive call
                                c.remove(r);
                            }
                        }
                        r.rotate();
                    }
                    if (c.canPlaceRectangle(p, r)) {
                        boolean shouldPlaceRectangle = true;
                        if (fixedHeight) {
                            if ((p.y + r.height) > maxHeight){
                                shouldPlaceRectangle = false;
                            }
                        }
                        if (shouldPlaceRectangle) {
                            r.setLocation(p);
                            c.add(r);
                            calculateCombination(c, index, rectangles, rotationsAllowed, fixedHeight, maxHeight); //Recursive call
                            c.remove(r);
                        }
                    }
                }
            }
        }
    }

}

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
        getArea(container, 0, c.getRectangles(), c.areRotationsAllowed());
        return finalContainer;
    }

    public int getArea(Container c, int index, List<IndexedRectangle> rectangles, boolean rotationsAllowed) {
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
                    points = new HashSet();
                    points.add(new Point(0, 0));
                }

                IndexedRectangle r = rectangles.get(i);
                index++;
                for (Point p : points) {
                    if (rotationsAllowed){
                        r.rotate();
                        if (c.canPlaceRectangle(p, r)) {
                            r.setLocation(p);
                            c.add(r);
                            getArea(c, index, rectangles, rotationsAllowed); //Recursive call
                            c.remove(r);
                        }
                        r.rotate();
                    }
                    if (c.canPlaceRectangle(p, r)) {
                        r.setLocation(p);
                        c.add(r);
                        getArea(c, index, rectangles, rotationsAllowed); //Recursive call
                        c.remove(r);
                    }
                }
            }
        }
        return minArea;
    }

}

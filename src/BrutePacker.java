import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by berrietrippe on 22/05/2017.
 */
public class BrutePacker implements Packer {

    int minArea = Integer.MAX_VALUE;
    Container finalContainer;
    List<IndexedRectangle> rectangles;

    @Override
    public Container Pack(Case c) {
        rectangles = c.getRectangles();
        Util.sortByArea(rectangles);

        Container container = new Container();
        if (rectangles != null) {
            rectangles.get(0).setLocation(0, 0);
        }

        container.add(rectangles.get(0));

        getArea(container, 1);

        return finalContainer;
    }

    public int getArea(Container c, int index) {
        if (index == rectangles.size()) {
            if (c.getArea() < minArea && c.size() == rectangles.size()) {
                minArea = c.getArea();
                List<IndexedRectangle> newRectangles = new ArrayList<>();
                rectangles.forEach((r) -> newRectangles.add(new IndexedRectangle(r.index, r.x, r.y, r.width, r.height)));
                finalContainer = new Container(newRectangles);
            }
        } else {
            for (int i = index; i < rectangles.size(); i++) {
                Set<Point> points = c.getBoundingLine();
                IndexedRectangle r = rectangles.get(i);

                Set<Point> additionalPoints = new HashSet<>(points.size());

                index++;

                for (Point p : points) {

                    if (c.canPlaceRectangle(p, r)) {
                        r.setLocation(p);
                        c.add(r);
                        getArea(c, index); //Recursive call
                        c.remove(r);
                    } else {
                    }
                }
            }
        }
        return minArea;
    }

}

import com.sun.org.apache.regexp.internal.RE;

import java.awt.*;
import java.util.*;

/**
 * Created by s154563 on 8-5-2017.
 */
public class GreedyPacker implements Packer {

    /**
     * Sorts a Collection of IndexedRectangles on their area size.
     * @param rectangles
     * @return Sorted descending ArrayList with the rectangles
     */
    private ArrayList<IndexedRectangle> sortOnSize(Collection<IndexedRectangle> rectangles) {
        ArrayList<IndexedRectangle> result = new ArrayList<>();

        for (IndexedRectangle rectangle: rectangles) {
            if (result.isEmpty()) {
                result.add(rectangle);
            } else {
                int area = rectangle.width * rectangle.height;
                for (int i = 0; i < result.size(); i++) {
                    if (area >= result.get(i).height * result.get(i).width) {
                        result.add(i, rectangle);
                        break;
                    }
                }
                if (!result.contains(rectangle)) {
                    result.add(rectangle);
                }
            }
        }
        return result;
    }

    public Set<Point> pointsAvailable(Container container) {
        Polygon polygon = container.getPolygon();
        Set<Point>  points = new HashSet<>(polygon.npoints);
        for (int x : polygon.xpoints) {
            for( int y: polygon.ypoints) {
                points.add(new Point(x+1, y));
                points.add(new Point(x, y+1));
                points.add(new Point(x+1, y+1));
            }
        }
        // TODO check that there were no 1x1 fields, remove unneeded points
        return points;
    }

    @Override
    public Container Pack(Case c) throws Exception {
        Collection<IndexedRectangle> originalRectangles = c.getRectangles();

        ArrayList<IndexedRectangle> sortedRectangles = sortOnSize(originalRectangles);
        for (IndexedRectangle rectangle: sortedRectangles) {
            System.out.println("rectangle area:"+rectangle.width * rectangle.height);
        }

        Container container = new Container();
        container.add(sortedRectangles.get(0));

        for (int i = 1; i < sortedRectangles.size(); i++) {
            IndexedRectangle r = sortedRectangles.get(i);
            container.add(r);

            int minArea = Integer.MAX_VALUE;
            Point minPoint = null;
            for (Point p : pointsAvailable(container)) {
                r.setLocation(p);
                int area = container.getArea();
                if (area < minArea) {
                    minPoint = p;
                    minArea = area;
                }
            }
            r.setLocation(minPoint);
        }

        return container;
    }
}

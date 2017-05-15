import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by s154563 on 8-5-2017.
 */
public class GreedyPacker implements Packer {

    /**
     * Sorts a Collection of IndexedRectangles on their area size.
     *
     * @param rectangles
     * @return Sorted descending ArrayList with the rectangles
     */
    private ArrayList<IndexedRectangle> sortOnSize(Collection<IndexedRectangle> rectangles) {
        ArrayList<IndexedRectangle> result = new ArrayList<>();

        for (IndexedRectangle rectangle : rectangles) {
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

    public Set<Point> pointsAvailable(Container container, Rectangle r) {
        Polygon polygon = container.getPolygon();
        Set<Point> points = new HashSet<>(polygon.npoints);
        for (int i = 0; i < polygon.npoints; i++) {
            int x = polygon.xpoints[i];
            int y = polygon.ypoints[i];

            points.add(new Point(x - 1, y - 1));
            points.add(new Point(x - 1, y));
            points.add(new Point(x - 1, y + 1));
            points.add(new Point(x, y + 1));
            points.add(new Point(x + 1, y + 1));
            points.add(new Point(x + 1, y));
            points.add(new Point(x + 1, y - 1));
            points.add(new Point(x, y - 1));
        }
        points.removeIf(p -> {
            if (p.x < 0 || p.y < 0){
                return true;
            }
            for (int dx = 0; dx < r.width; dx++) {
                for (int dy = 0; dy < r.height; dy++) {
                    if (container.contains(p.x+dx, p.x+dy)) {
                        return true;
                    }
                }
            }
            return false;
        });
        return points;
    }

    @Override
    public Container Pack(Case c) throws Exception {
        Collection<IndexedRectangle> originalRectangles = c.getRectangles();

        ArrayList<IndexedRectangle> sortedRectangles = sortOnSize(originalRectangles);

        Container container = new Container();
        container.add(sortedRectangles.get(0));

        int maxHeight = Integer.MAX_VALUE;
        boolean fixedHeight = c.isHeightFixed();
        if (fixedHeight) {
           maxHeight = c.getHeight();
        }

        for (int i = 1; i < sortedRectangles.size(); i++) {
            IndexedRectangle r = sortedRectangles.get(i);
            container.add(r);

            int minArea = Integer.MAX_VALUE;
            Point minPoint = null;
            for (Point p : pointsAvailable(container, r)) {
                if (fixedHeight && p.y+r.height > maxHeight) {
                    continue;
                }
                r.setLocation(p);
                int area = container.getArea();
                if (area < minArea) {
                    minPoint = p;
                    minArea = area;
                }
            }
            if (minPoint == null) {
                throw new Exception(String.format("No free point available for rectangle with index %d (%d %d)", r.getIndex(), r.width, r.height));
            }
            r.setLocation(minPoint);
        }

        return container;
    }
}

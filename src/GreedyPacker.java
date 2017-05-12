import java.awt.*;
import java.util.*;

/**
 * Created by s154563 on 8-5-2017.
 */
public class GreedyPacker implements Packer {
    @Override
    public Container Pack(Case c) {
        Collection<IndexedRectangle> originalRectangles = c.getRectangles();
        ArrayList<IndexedRectangle> sortedRectangles = sortOnSize(originalRectangles);

        Container container = new Container(sortedRectangles);
        ArrayList<IndexedRectangle> rectangles = (ArrayList<IndexedRectangle>)container.getRectangles();

        for (IndexedRectangle rectangle: rectangles) {
            System.out.println("rectangle area:"+rectangle.width * rectangle.height);
        }

        //adding the biggest rectangle at 0, 0
        IndexedRectangle firstRectangle = sortedRectangles.get(0);
        firstRectangle.setLocation(0, 0);
        container.getRectangles().add(firstRectangle);
        sortedRectangles.remove(0);

        System.out.println("Placed first rectangle of size " + firstRectangle.width*firstRectangle.height
        + " at 0,0+height with width " + firstRectangle.width + " and height " + firstRectangle.height);

        for (IndexedRectangle rectangle: sortedRectangles) {
            int smallestArea = Integer.MAX_VALUE;
            IndexedRectangle bestRectangle = null;
            for (Point point: pointsAvailable(container)) {
                rectangle.setLocation(point);
                container.getRectangles().add(rectangle);
                if (isValidContainer(container, c)) {
                    int area = container.getArea();
                    if (area < smallestArea) {
                        smallestArea = area;
                        bestRectangle = rectangle;
                    }
                }
                container.getRectangles().remove(rectangle);
                if (c.areRotationsAllowed()) {
                    rectangle.rotate();
                    container.getRectangles().add(rectangle);
                    if (isValidContainer(container, c)) {
                        int area = container.getArea();
                        if (area < smallestArea) {
                            smallestArea = area;
                            bestRectangle = rectangle;
                        }
                    }
                    container.getRectangles().remove(rectangle);
                }
            }
            container.getRectangles().add(bestRectangle);
        }

        return new Container(originalRectangles);
    }

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

    public boolean isValidContainer(Container container, Case c) {
        Collection<IndexedRectangle> rectangles = container.getRectangles();
        System.out.println(rectangles);
        // check for negative container, ignoring non-placed ones
        for (Rectangle r : rectangles) {
            System.out.println("x" + r.getMinX() + "y" +r.getMinY());
            System.out.println("x" + r.getMaxX() + "y" +r.getMaxY());
            if (!(r.x == 0 && r.y == 0)) {
                if (r.getMinX() < 0 || r.getMinY() < 0) {
                    return false;
                }
            }
        }

        // check for height limit
        if (c.isHeightFixed()) {
            if (c.getHeight() < container.getHeight()) {
                return false;
            }

        }
        // check if there are overlapping rectangles
        for (Rectangle r : rectangles) {
            if (!(r.x == 0 && r.y == 0)) {
                for (Rectangle l : rectangles) {
                    if (r.contains(l) && r != l) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Set<Point> pointsAvailable(Container container) {
        Set<Point> set = new HashSet<>();
        for (IndexedRectangle rectangle: container) {
            //add all the points on the top side
            for (int i = rectangle.x; i < rectangle.x + rectangle.width; i++) {
                set.add(new Point(i, rectangle.y));
            }
            //add all the points on the right side
            for (int j = rectangle.y - rectangle.height; j < rectangle.y; j++) {
                set.add(new Point(rectangle.x + rectangle.width, j));
            }
            //TODO remove points that have bigger ones
            Set<Point> cloneSet = new HashSet<>();
            cloneSet.addAll(set);
            for (Point point1: cloneSet) {
                for (Point point2: cloneSet) {
                    if (point1.x == point2.x && point1.y < point2.y) {
                        set.remove(point1);
                    }
                    if (point1.y == point2.y && point1.x < point2.x) {
                        set.remove(point1);
                    }
                }
            }
        }
        return set;
    }
}

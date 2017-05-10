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
        IndexedRectangle firstRectangle = rectangles.get(0);
        firstRectangle.setLocation(0, 0 + firstRectangle.height);

        System.out.println("Placed first rectangle of size " + firstRectangle.width*firstRectangle.height
        + " at 0,0+height with width " + firstRectangle.width + " and height " + firstRectangle.height);

        for (IndexedRectangle rectangle: rectangles.subList(1, rectangles.size() - 1)) {
                int smallestArea = Integer.MAX_VALUE;
                Point bestRectangle = null;
                Set<Point> pointsAvailable = pointsAvailable(container);
                System.out.println(pointsAvailable);
                for (Point point : pointsAvailable) {
                    rectangle.setLocation(point);

                    if (isValidContainer(container, c)) {
                        int area = container.getArea();
                        if (area < smallestArea) {
                            smallestArea = area;
                            bestRectangle = rectangle.getLocation();
                        }
                    }
                    if (c.areRotationsAllowed()) {
                        rectangle.rotate();
                        if (isValidContainer(container, c)) {
                            int area = container.getArea();
                            if (area < smallestArea) {
                                smallestArea = area;
                                bestRectangle = rectangle.getLocation();
                            }
                        }
                    }
                }
                rectangle.setLocation(bestRectangle);
        }

        /*
        rectanglePlaced:
            if (!sortedRectangles.isEmpty()) {
                IndexedRectangle rectangle = sortedRectangles.get(0); // from the biggest rectangle to the smallest
                int x = 0;
                int y = 0;
                List<Point> checkedPoints = new ArrayList<>();
                while (true) {
                    for (int i = 0; i < x; i++) {
                        for (int j = 0; j < y; j++) {
                            if (!checkedPoints.contains(new Point(i, j))) {
                                if (trueCheck if this point is empty) {

                                    IndexedRectangle placedRectangle = new IndexedRectangle(rectangle.getIndex(),
                                            new Rectangle(i, j + rectangle.height, rectangle.width, rectangle.height));
                                    container.add(placedRectangle);
                                    if (!isValidContainer(container)) {
                                        container.remove(placedRectangle);
                                    } else {

                                        sortedRectangles.remove(0);
                                        break rectanglePlaced;
                                    }
                                }
                                checkedPoints.add(new Point(i, j));
                            }
                        }
                    }
                    x++;
                    y++;
                }
            }*/





        /*int x = 0;
        if (c.isHeightFixed()) {
            for (IndexedRectangle r : rectangles) {
                try {
                    if (r.getHeight() > c.getHeight()) {
                        if (c.areRotationsAllowed()) {
                            r.rotate();
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Exception: " + e.getMessage());
                }

                r.setLocation(x, 0);
                x += r.width;
            }
        } else {
            for (IndexedRectangle r : rectangles) {
                r.setLocation(x, 0);
                x += r.width;
            }
        }*/
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
        // check for negative container, ignoring non-placed ones
        for (Rectangle r : rectangles) {
            if (!(r.x == 0 && r.y == 0)) {
                if (r.getMinX() < 0 || r.getMinY() < 0) {
                    return false;
                }
            }
        }

        // check for height limit
        if (c.isHeightFixed()) {
            try {
                if (c.getHeight() < container.getHeight()) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        }
        return set;
    }
}

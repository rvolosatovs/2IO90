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
        Container container = new Container(new ArrayList<>());

        for (IndexedRectangle rectangle: sortedRectangles) {
            System.out.println("rectangle area:"+rectangle.width * rectangle.height);
        }

        //adding the biggest rectangle at 0, 0
        IndexedRectangle firstRectangle = sortedRectangles.get(0);
        firstRectangle.setLocation(0, 0);
        container.add(firstRectangle);
        sortedRectangles.remove(0);

        System.out.println("Placed first rectangle of size " + firstRectangle.width*firstRectangle.height
        + " at (0,0) with width " + firstRectangle.width + " and height " + firstRectangle.height);

        for (IndexedRectangle rectangle: sortedRectangles) {
            int smallestArea = Integer.MAX_VALUE;
            IndexedRectangle bestRectangle = null;
            for (Point point: pointsAvailable(container)) {
                rectangle.setLocation(point);
                container.add(rectangle);
                if (isValidContainer(container, c)) {
                    int area = container.getArea();
                    if (area < smallestArea) {
                        smallestArea = area;
                        bestRectangle = rectangle;
                    }
                }
                container.remove(rectangle);
                if (c.areRotationsAllowed()) {
                    rectangle.rotate();
                    rectangle.setLocation(point);
                    container.add(rectangle);
                    if (isValidContainer(container, c)) {
                        int area = container.getArea();
                        if (area < smallestArea) {
                            smallestArea = area;
                            bestRectangle = rectangle;
                        }
                    }
                    container.remove(rectangle);
                }
            }
            container.add(bestRectangle);
        }

        return container;
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

        // check for height limit
        if (c.isHeightFixed()) {
            if (c.getHeight() < container.getHeight()) {
                return false;
            }
        }

        // check if there are overlapping rectangles
        for (Rectangle r : rectangles) {
            for (Rectangle l : rectangles) {
                if (r.contains(l) && r != l) {
                    return false;
                }
            }
        }
        return true;
    }

    public Set<Point> pointsAvailable(Container container) {
        Set<Point> set = new HashSet<>();
        for (IndexedRectangle rectangle: container) {
            //add all the points on the top side
            for (int i = rectangle.x; i <= rectangle.x + rectangle.width; i++) {
                set.add(new Point(i, rectangle.y + rectangle.height));
            }
            //add all the points on the right side
            for (int j = rectangle.y; j <= rectangle.y + rectangle.height; j++) {
                set.add(new Point(rectangle.x + rectangle.width, j));
            }
        }

        int[] biggestYforX = new int[container.getWidth() + 1];
        int[] biggestXforY = new int[container.getHeight() + 1];
        for (int i: biggestXforY) {i = -1;}
        for (int i: biggestYforX) {i = -1;}

        //add only the highest y's for the top
        for (Point point: set) {
            if (biggestYforX[point.x] < point.y) {
                biggestYforX[point.x] = point.y;
            }
        }

        //add only the highest x's for the side
        for (Point point: set) {
            if (biggestXforY[point.y] < point.x) {
                biggestXforY[point.y] = point.x;
            }
        }

        Set<Point> newSet = new HashSet<>();
        for (int x = 0; x < biggestYforX.length; x++) {
            newSet.add(new Point(x, biggestYforX[x]));
        }
        for (int y = 0; y < biggestXforY.length; y++) {
            newSet.add(new Point(biggestXforY[y], y));
        }


        return newSet;
    }
}

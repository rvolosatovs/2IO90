package solver;

import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by rvolosatovs on 5/15/17.
 */
public class ContainerTest {
    Container newContainer(Collection<? extends IndexedRectangle> rectangles) {
        return  new Container(rectangles);
    }
    @Test
    public void testIsBounding() {
        List<IndexedRectangle> rectangles = Arrays.asList(
                new IndexedRectangle(0, 0, 0, 2, 3),
                new IndexedRectangle(1, 2, 1, 3, 4),
                new IndexedRectangle(2, 5, 4, 1, 1)
        );

        List<Point> edges = Arrays.asList(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(1, 3),
                new Point(2, 3),
                new Point(2, 4),
                new Point(2, 5),
                new Point(3, 5),
                new Point(4, 5),
                new Point(5, 5),
                new Point(6, 5),
                new Point(6, 4),
                new Point(5, 4),
                new Point(5, 3),
                new Point(5, 2),
                new Point(5, 1),
                new Point(4, 1),
                new Point(3, 1),
                new Point(2, 1),
                new Point(2, 0),
                new Point(1, 0)
        );

        Container c = newContainer(rectangles);
        System.out.println(c);
        for (Rectangle r : rectangles) {
            for (int dx = 0; dx <= r.width; dx++) {
                for (int dy = 0; dy <= r.height; dy++) {
                    Point p = new Point(r.x + dx, r.y + dy);
                    assertEquals(String.format("c.isBounding(%d,%d)", p.x, p.y), edges.contains(p), c.isBounding(p));
                }
            }
        }
    }

    @Test
    public void testContains() {
        List<IndexedRectangle> rectangles = Arrays.asList(
                new IndexedRectangle(0, 0, 0, 1, 1),
                new IndexedRectangle(1, 0, 1, 2, 2),
                new IndexedRectangle(2, 3, 0, 1, 1)
        );

        List<List<Integer>> contained = Arrays.asList(
                Arrays.asList(0, 1, 2, 3),
                Arrays.asList(0, 1, 2, 3),
                Arrays.asList(1, 2, 3),
                Arrays.asList(0, 1),
                Arrays.asList(0, 1)
        );

        Container c = newContainer(rectangles);
        System.out.println(c);
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 3; y++) {
                assertEquals(String.format("c.contains(%d,%d)", x, y), contained.get(x).contains(y), c.contains(x, y));
            }
        }
    }

    @Test
    public void testIsOccupied() {
        List<IndexedRectangle> rectangles = Arrays.asList(
                new IndexedRectangle(0, 0, 0, 1, 1),
                new IndexedRectangle(1, 0, 1, 2, 2),
                new IndexedRectangle(2, 3, 0, 1, 1)
        );

        List<List<Integer>> inside = Arrays.asList(
                Arrays.asList(0, 1, 2),
                Arrays.asList(2),
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList()
        );

        Container c = newContainer(rectangles);
        System.out.println(c);
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 3; y++) {
                assertEquals(String.format("c.isOccupied(%d,%d)", x, y), inside.get(x).contains(y), c.isOccupied(x, y));
            }
        }
    }

    @Test
    public void testCanPlaceRectangle() {
        List<IndexedRectangle> rectangles = Arrays.asList(
                new IndexedRectangle(0, 0, 0, 7, 1)
        );

        Container c = newContainer(rectangles);
        System.out.println(c);
        assertFalse("c.canPlaceRectangle (0,0,4,8)", c.canPlaceRectangle(0, 0, 4, 8));
    }

    @Test
    public void testAreaWidthAndHeight() {
        List<IndexedRectangle> rectangles = Arrays.asList(
                new IndexedRectangle(0, 0, 0, 2, 2),
                new IndexedRectangle(1, 2, 2, 1, 1),
                new IndexedRectangle(2, 3, 3, 1, 2)
        );

        Container c = newContainer(rectangles);
        System.out.println(c);
        assertTrue("c.getWidth() == 4", c.getWidth() == 4);
        assertTrue("c.getHeight() == 5", c.getHeight() == 5);
        assertTrue("c.getArea() == 20", c.getArea() == 20);
    }
}

import org.junit.Test;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by rvolosatovs on 5/15/17.
 */
public class ContainerTest {
    @Test
    public void testIsOnEdge() {
        List<IndexedRectangle> rectangles = Arrays.asList(
                new IndexedRectangle(0, 0, 0, 2, 2),
                new IndexedRectangle(1, 2, 2, 3, 3),
                new IndexedRectangle(2, 5, 4, 1, 1)
        );

        List<Point> edges = Arrays.asList(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(1, 2),
                new Point(2, 2),
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
                new Point(4, 2),
                new Point(3, 2),
                new Point(2, 2),
                new Point(2, 1),
                new Point(2, 0),
                new Point(1, 0)
        );

        Container c = new Container(rectangles);
        c.printRectangles();
        for (Rectangle r : rectangles) {
            for (int dx = 0; dx <= r.width; dx++) {
                for (int dy = 0; dy <= r.height; dy++) {
                    Point p = new Point(r.x + dx, r.y + dy);
                    assertEquals(edges.contains(p), c.isBounding(p));
                }
            }
        }
    }
}

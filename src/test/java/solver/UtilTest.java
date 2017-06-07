package solver;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by rvolosatovs on 5/16/17.
 */
public class UtilTest {
    @Test
    public void testSortByArea() {
        List<Rectangle> rectangles = Arrays.asList(
                new Rectangle(10, 10),
                new Rectangle(20, 20),
                new Rectangle(2, 2),
                new Rectangle(4, 4)
        );
        Util.sortByArea(rectangles);

        List<Rectangle> expected = Arrays.asList(
                new Rectangle(20, 20),
                new Rectangle(10, 10),
                new Rectangle(4, 4),
                new Rectangle(2, 2)
        );

        assertArrayEquals(expected.toArray(), rectangles.toArray());
    }

    @Test
    public void testSortByIndex() {
        List<IndexedRectangle> rectangles = Arrays.asList(
                new IndexedRectangle(4, 42, 42),
                new IndexedRectangle(5, 42, 42),
                new IndexedRectangle(1, 42, 42),
                new IndexedRectangle(3, 42, 42),
                new IndexedRectangle(2, 42, 42),
                new IndexedRectangle(0, 42, 42)
        );
        Util.sortByIndex(rectangles);

        List<IndexedRectangle> expected = Arrays.asList(
                new IndexedRectangle(0, 42, 42),
                new IndexedRectangle(1, 42, 42),
                new IndexedRectangle(2, 42, 42),
                new IndexedRectangle(3, 42, 42),
                new IndexedRectangle(4, 42, 42),
                new IndexedRectangle(5, 42, 42)
        );

        assertArrayEquals(expected.toArray(), rectangles.toArray());
    }
}

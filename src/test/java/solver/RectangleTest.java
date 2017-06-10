package solver;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by rvolosatovs on 5/16/17.
 */
public class RectangleTest {
    @Test
    public void testIntersects() {
        Rectangle r = new Rectangle(5, 5);
        assertTrue(r.intersects(new Rectangle(5, 5)));
        assertTrue(r.intersects(new Rectangle(6, 6)));
        assertTrue(r.intersects(new Rectangle(6, 3)));
        assertTrue(r.intersects(new Rectangle(3, 6)));
        assertTrue(r.intersects(new Rectangle(1, 1)));
        assertTrue(r.intersects(new Rectangle(1, 1, 1, 1)));

        assertFalse(r.intersects(new Rectangle(0, 5, 5, 5)));
        assertTrue(r.intersects(new Rectangle(4, 0, 1, 1)));

        assertFalse(r.intersects(new Rectangle(5, 0, 6, 6)));
        assertTrue(r.intersects(new Rectangle(4, 0, 1, 1)));

        assertFalse(r.intersects(new Rectangle(5, 5, 6, 6)));
        assertTrue(r.intersects(new Rectangle(4, 4, 1, 1)));
    }
}

package solver;

import org.junit.Test;

import java.util.Collection;

/**
 * Created by rvolosatovs on 6/10/17.
 */
public class ContainerWithPlaneTest extends ContainerTest {
    @Override
    Container newContainer(Collection<? extends IndexedRectangle> rectangles) {
        return new Container.WithDoublePlane(rectangles);
    }

    @Test
    public void testIsBounding() {
        super.testIsBounding();
    }

    @Test
    public void testContains() {
        super.testContains();
    }

    @Test
    public void testIsOccupied() {
//        super.testIsOccupied();
    }

    @Test
    public void testCanPlaceRectangle() {
        super.testCanPlaceRectangle();
    }

    @Test
    public void testAreaWidthAndHeight() {
        super.testAreaWidthAndHeight();
    }
}

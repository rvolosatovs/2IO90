import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by berrietrippe on 04/05/2017.
 */
public class GreedyPackerTest extends PackerTest {
    @Override
    public Packer newPacker() {
        return new GreedyPacker();
    }

    @Test
    public void checkHeight() {
        super.checkHeight();
    }

    @Test
    public void checkOverlap() {
        super.checkOverlap();
    }

    @Test
    public void testIsValidContainer() {
        GreedyPacker greedy = new GreedyPacker();
        Dimension[] dimensions = new Dimension[1];
        dimensions[0] = new Dimension(2, 3);
        //dimensions[1] = new Dimension(5, 7);
        //dimensions[2] = new Dimension(2, 4);
        int containerHeight = 6;
        Case c = new Case(containerHeight, true, dimensions);
        ArrayList<IndexedRectangle> rectangles = (ArrayList<IndexedRectangle>)c.getRectangles();
        rectangles.get(0).setLocation(0, 3);
        assertTrue(greedy.isValidContainer(new Container(rectangles), c));
    }

    @Test
    public void testIsValidContainer1() {
        GreedyPacker greedy = new GreedyPacker();
        Dimension[] dimensions = new Dimension[1];
        dimensions[0] = new Dimension(2, 3);
        //dimensions[1] = new Dimension(5, 7);
        //dimensions[2] = new Dimension(2, 4);
        int containerHeight = 6;
        Case c = new Case(containerHeight, true, dimensions);
        ArrayList<IndexedRectangle> rectangles = (ArrayList<IndexedRectangle>)c.getRectangles();
        System.out.println(rectangles);
        rectangles.get(0).setLocation(0, 2);
        System.out.println(rectangles);
        assertFalse(greedy.isValidContainer(new Container(rectangles), c));
    }
}
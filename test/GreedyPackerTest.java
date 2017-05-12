import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    public void testAllFiles() { super.testAllFiles(); }

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
        //System.out.println(rectangles);
        rectangles.get(0).setLocation(0, 6);
        //System.out.println(rectangles);
        assertFalse(greedy.isValidContainer(new Container(rectangles), c));
    }

    @Test
    public void testPointsAvailable1() {
        GreedyPacker greedy = new GreedyPacker();
        Dimension[] dimensions = new Dimension[1];
        dimensions[0] = new Dimension(2, 3);
        int containerHeight = 6;
        Case c = new Case(containerHeight, true, dimensions);
        ArrayList<IndexedRectangle> rectangles = (ArrayList<IndexedRectangle>)c.getRectangles();
        rectangles.get(0).setLocation(0, 0);
        Set<Point> set = new HashSet<>();
        set.add(new Point(2, 0));
        set.add(new Point(2, 1));
        set.add(new Point(2, 2));
        set.add(new Point(2, 3));
        set.add(new Point(0, 3));
        set.add(new Point(1, 3));
        assertEquals(set, greedy.pointsAvailable(new Container(rectangles)));
    }

    @Test
    public void testPointsAvailable2() {
        GreedyPacker greedy = new GreedyPacker();
        Dimension[] dimensions = new Dimension[2];
        dimensions[0] = new Dimension(1, 1);
        dimensions[1] = new Dimension(1, 2);
        int containerHeight = 6;
        Case c = new Case(containerHeight, true, dimensions);
        ArrayList<IndexedRectangle> rectangles = (ArrayList<IndexedRectangle>)c.getRectangles();
        rectangles.get(0).setLocation(0, 0);
        rectangles.get(1).setLocation(1, 0);
        Set<Point> set = new HashSet<>();
        set.add(new Point(2, 0));
        set.add(new Point(2, 1));
        set.add(new Point(2, 2));
        set.add(new Point(1, 2));
        set.add(new Point(0, 1));
        assertEquals(set, greedy.pointsAvailable(new Container(rectangles)));
    }
}
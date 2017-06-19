package solver;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by berrietrippe on 08/05/2017.
 */
public abstract class PackerTest {
    public abstract Packer newPacker();

    void assertNoOverlap(Collection<? extends Rectangle> rectangles) {
        for (Rectangle r1 : rectangles) {
            for (Rectangle r2 : rectangles) {
                assertFalse(String.format("r1(%s).intersects(r2(%s)): %s", r1, r2, r1.intersection(r2)), r1.intersects(r2) && r1 != r2);
            }
        }
    }

    void assertHeightLimitRespected(Case c, Collection<? extends Rectangle> rectangles) {
        if (!c.isHeightFixed()) {
            return;
        }

        int maxHeight = 0;
        for (Rectangle r : rectangles) {
            int height = r.y + r.height;
            if (height > maxHeight) {
                maxHeight = height;
            }
        }
        assertFalse(String.format("Max height: %d, got %d", c.getHeight(), maxHeight), maxHeight > c.getHeight());
    }

    public void testHeight() {
        Dimension[] dimensions = new Dimension[3];
        dimensions[0] = new Dimension(2, 3);
        dimensions[1] = new Dimension(5, 7);
        dimensions[2] = new Dimension(2, 4);
        int containerHeight = 6;

        Case c = new Case(containerHeight, true, dimensions);

        Collection<IndexedRectangle> rectangles = null;
        try {
            rectangles = newPacker().Pack(c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(rectangles);
        assertTrue(String.format("Input size: %d, got %d", c.getRectangles().size(), rectangles.size()), c.getRectangles().size() == rectangles.size());
        assertHeightLimitRespected(c, rectangles);
        assertNoOverlap(rectangles);
    }

    public void testSmallInput() {
        try {
            Stream<Path> paths = Files.walk(Paths.get("testcases"));
            paths.forEach(path -> {
                if (Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".txt") && path.getFileName().toString().matches("0(\\d)_(.*)")) {
                    System.out.println("Solving " + path.toString());
                    Case c = null;
                    try {
                        c = new Case(new FileInputStream(path.toFile()));
                    } catch (Exception e) {
                        System.out.printf("Failed to parse case: %s", e.getMessage());
                        return;
                    }
                    Collection<IndexedRectangle> rectangles = null;
                    try {
                        rectangles = newPacker().Pack(c);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(rectangles);
                    assertTrue(String.format("Input size: %d, got %d", c.getRectangles().size(), rectangles.size()), c.getRectangles().size() == rectangles.size());
                    assertHeightLimitRespected(c, rectangles);
                    assertNoOverlap(rectangles);
                }
            });
            paths.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assertEqualOutputLength(Collection<? extends Dimension> dimensions) {
        Dimension[] dimensionArr = new Dimension[dimensions.size()];
        dimensions.toArray(dimensionArr);
        Collection<IndexedRectangle> c = null;
        try {
            c = newPacker().Pack(new Case(true, dimensionArr));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Output length:", dimensions.size(), c.size());
    }

    public void testOutputLengthRandom() {
        assertEqualOutputLength(Arrays.asList(
                new Dimension(4, 5),
                new Dimension(2, 10),
                new Dimension(7, 13)
        ));
    }

    public void testOutputLengthSameSizes() {
        assertEqualOutputLength(Arrays.asList(
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5)
        ));
    }

    public void testOutputLengthEmpty() {
        assertEqualOutputLength(new ArrayList<>());
    }
}

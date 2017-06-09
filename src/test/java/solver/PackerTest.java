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

    public void testHeight() {
        Dimension[] dimensions = new Dimension[3];
        dimensions[0] = new Dimension(2, 3);
        dimensions[1] = new Dimension(5, 7);
        dimensions[2] = new Dimension(2, 4);
        int containerHeight = 6;

        int maxHeight = 0;
        Collection<IndexedRectangle> rectangles = newPacker().Pack(new Case(containerHeight, true, dimensions));
        System.out.println(rectangles);
        for (Rectangle r : rectangles) {
            int height = r.y + r.height;
            if (height > maxHeight) {
                maxHeight = height;
            }
        }
        assertFalse(String.format("Max height: %d, got %d\nContainer:\n%s", containerHeight, maxHeight, rectangles), maxHeight > containerHeight);
    }

    public void testOverlap() {
        Dimension[] dimensions = new Dimension[4];
        dimensions[0] = new Dimension(4, 5);
        dimensions[1] = new Dimension(2, 10);
        dimensions[2] = new Dimension(7, 13);
        dimensions[3] = new Dimension(11, 12);

        Collection<IndexedRectangle> rectangles = newPacker().Pack(new Case(14, false, dimensions));
        System.out.println(rectangles);
        for (Rectangle r1 : rectangles) {
            for (Rectangle r2 : rectangles) {
                assertFalse(String.format("r1(%s).intersects(r2(%s)): %s", r1, r2, r1.intersection(r2)), r1.intersects(r2) && r1 != r2);
            }
        }
    }

    public void testSmallInput() {
        try {
            Stream<Path> paths = Files.walk(Paths.get("test/cases"));
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
                    System.out.println(new Solution(c, newPacker().Pack(c)));
                }
            });
            paths.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void assertEqualOutputLength(Collection<Dimension> dimensions) {
        Dimension[] dimensionArr = new Dimension[dimensions.size()];
        dimensions.toArray(dimensionArr);
        Collection<IndexedRectangle> c = newPacker().Pack(new Case(true, dimensionArr));
        assertEquals("Output length:", dimensions.size(), c.size());
    }

    public void testOutputLengthRandom() {
        assertEqualOutputLength(Arrays.asList(
                new Dimension(4, 5),
                new Dimension(2, 10),
                new Dimension(7, 13),
                new Dimension(8, 13),
                new Dimension(11, 12),
                new Dimension(11, 12),
                new Dimension(11, 12),
                new Dimension(13, 12)
        ));
    }

    public void testOutputLengthSameSizes() {
        assertEqualOutputLength(Arrays.asList(
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5),
                new Dimension(5, 5)
        ));
    }

    public void testOutputLengthEmpty() {
        assertEqualOutputLength(new ArrayList<>());
    }
}

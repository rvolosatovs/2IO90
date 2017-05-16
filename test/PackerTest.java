import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Created by berrietrippe on 08/05/2017.
 */
public abstract class PackerTest {
    public abstract Packer newPacker();

    public void checkHeight() {
        Dimension[] dimensions = new Dimension[3];
        dimensions[0] = new Dimension(2, 3);
        dimensions[1] = new Dimension(5, 7);
        dimensions[2] = new Dimension(2, 4);
        int containerHeight = 6;

        assertFalse("Packer respects container height", newPacker().Pack(new Case(containerHeight, true, dimensions)).getHeight() > containerHeight);
    }

    public void checkOverlap() {
        Dimension[] dimensions = new Dimension[4];
        dimensions[0] = new Dimension(4, 5);
        dimensions[1] = new Dimension(2, 10);
        dimensions[2] = new Dimension(7, 13);
        dimensions[3] = new Dimension(11, 12);

        Collection<IndexedRectangle> rectangles = newPacker().Pack(new Case(14, false, dimensions)).getRectangles();
        for (Rectangle r : rectangles) {
            for (Rectangle l : rectangles) {
                assertFalse("Packer overlaps rectangles", r.contains(l) && r != l);
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
}

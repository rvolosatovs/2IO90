import org.junit.Test;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.*;

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

        Case c = new Case(containerHeight, true, dimensions);
        int height = newPacker().Pack(c).getHeight();

        assertFalse("Packer respects container height", height > containerHeight);
    }

    public void checkOverlap() {
        Dimension[] dimensions = new Dimension[4];
        dimensions[0] = new Dimension(4, 5);
        dimensions[1] = new Dimension(2, 10);
        dimensions[2] = new Dimension(7, 13);
        dimensions[3] = new Dimension(11, 12);

        Case c = new Case(10, false, dimensions);
        Collection<IndexedRectangle> rectangles = newPacker().Pack(c).getRectangles();

        for (Rectangle r : rectangles) {
            for (Rectangle l : rectangles) {
                assertFalse("Packer overlaps rectangles", r.contains(l) && r != l);
            }
        }
    }

    public void testAllFiles() {
        try {
            Stream<Path> paths = Files.walk(Paths.get("test/cases"));
            paths.forEach(path -> {
                if (Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".txt")) {
                    try {
                        System.out.println("Parsing " + path.toString());
                        Case c = new Case(new FileInputStream(path.toFile()));
                        Solution s = new Solution(c, newPacker().Pack(c));
                        System.out.println(s);
                    } catch (Exception e) {
                        fail(e.getMessage());
                    }
                }
            });
            paths.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

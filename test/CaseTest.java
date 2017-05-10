import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by s154563 on 3-5-2017.
 */
public class CaseTest {
    @Test
    public void readAllFilesTest() {
        try {
            Stream<Path> paths = Files.walk(Paths.get("test/cases"));
            paths.forEach(path -> {
                if (Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".txt")) {
                    try {
                        System.out.println("Parsing " + path.toString());
                        new Case(new FileInputStream(path.toFile()));
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

    @Test
    public void parseSpecificationTest() {
        class spec {
            boolean rotationsAllowed;
            boolean sizeFixed;
            int size;

            spec(boolean rotationsAllowed, int size) {
                this.rotationsAllowed = rotationsAllowed;
                this.sizeFixed = true;
                this.size = size;
            }

            spec(boolean rotationsAllowed) {
                this.rotationsAllowed = rotationsAllowed;
            }
        }

        spec[] specs = new spec[]{
                new spec(true, 42),
                new spec(false, 42),
                new spec(true),
                new spec(false)
        };


        for (spec spec : specs) {
            String s = String.join(System.getProperty("line.separator"),
                    "container height:" + (spec.sizeFixed ? "fixed " + spec.size : "free"),
                    "rotations allowed:" + (spec.rotationsAllowed ? "yes" : "no"),
                    "number of rectangles: 3",
                    "420 69",
                    "42 42",
                    "9 11"
            );
            Case c = new Case(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
            assertEquals("Fixed size", spec.sizeFixed, c.isSizeFixed());
            assertEquals("Rotations", spec.rotationsAllowed, c.areRotationsAllowed());
            if (spec.sizeFixed) {
                assertEquals("Height", spec.size, c.getSize());
            } else {
                try {
                    c.getSize();
                    fail("getHeight did not throw an exception on free sized case");
                } catch (Error e) {
                    System.out.println("getHeight on free sized case threw error: " + e.getMessage());
                }
            }
            assertArrayEquals(new IndexedRectangle[]{
                    new IndexedRectangle(1, 420, 69),
                    new IndexedRectangle(2, 42, 42),
                    new IndexedRectangle(3, 9, 11)
            }, c.getRectangles().toArray());
        }
    }
}

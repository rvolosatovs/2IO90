package solver;

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
            Stream<Path> paths = Files.walk(Paths.get("testcases"));
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
            boolean heightFixed;
            long height;

            spec(boolean rotationsAllowed, long height) {
                this.rotationsAllowed = rotationsAllowed;
                this.heightFixed = true;
                this.height = height;
            }

            spec(boolean rotationsAllowed) {
                this.rotationsAllowed = rotationsAllowed;
            }
        }

        long longHeight = Integer.MAX_VALUE;
        longHeight += Integer.MAX_VALUE;

        spec[] specs = new spec[]{
                new spec(true, 42),
                new spec(false, 42),
                new spec(true),
                new spec(false),
                new spec(false, longHeight + 1)
        };

        for (spec spec : specs) {
            String s = String.join(System.getProperty("line.separator"),
                    "container height:" + (spec.heightFixed ? "fixed " + spec.height : "free"),
                    "rotations allowed:" + (spec.rotationsAllowed ? "yes" : "no"),
                    "number of rectangles: 3",
                    "420 69",
                    "42 42",
                    "9 11"
            );
            Case c = new Case(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)));
            assertEquals("Fixed height", spec.heightFixed, c.isHeightFixed());
            assertEquals("Rotations", spec.rotationsAllowed, c.areRotationsAllowed());
            if (spec.heightFixed) {
                assertEquals("Height", (int) Math.min(spec.height, Integer.MAX_VALUE), c.getHeight());
            } else {
                try {
                    c.getHeight();
                    fail("getHeight did not throw an exception on free sized case");
                } catch (Error e) {
                    System.out.println("getHeight on free sized case threw error: " + e.getMessage());
                }
            }
            assertArrayEquals(new IndexedRectangle[]{
                    new IndexedRectangle(0, 420, 69),
                    new IndexedRectangle(1, 42, 42),
                    new IndexedRectangle(2, 9, 11)
            }, c.getRectangles().toArray());
        }
    }
}

package solver;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * Created by berrietrippe on 04/05/2017.
 */
public class MasterPackerTest extends PackerTest {
    @Override
    public Packer newPacker() {
        return new MasterPacker();
    }

    @Test
    public void testHeight() {
        super.testHeight();
    }

    @Test
    public void testVerySmallInput() {
        super.testVerySmallInput();
    }

    @Test
    public void testOutputLengthSameSizes() {
        super.testOutputLengthSameSizes();
    }

    @Test
    public void testOutputLengthRandom() {
        super.testOutputLengthRandom();
    }

    @Test
    public void testOutputLengthEmpty() {
        super.testOutputLengthEmpty();
    }

    @Test
    public void testGreedyToNFDH() {
        PackingSolver.startTime = System.currentTimeMillis() + 269000;
        try {
            Stream<Path> paths = Files.walk(Paths.get("testcases"));
            paths.forEach(path -> {
                if (Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".txt") && path.getFileName().toString().matches("25_01_h19_ry.txt")) {
                    System.out.println("Solving " + path.toString());
                    Case c = null;
                    try {
                        c = new Case(new FileInputStream(path.toFile()));
                    } catch (Exception e) {
                        System.out.printf("Failed to parse case: %s", e.getMessage());
                        return;
                    }

                    Collection<IndexedRectangle> rectangles = null;
                    Solution s = new Solution(c, newPacker());
                    rectangles = s.getRectangles();
                    System.out.println("Finished packing at: " + (System.currentTimeMillis() - PackingSolver.startTime));
                    assertTrue(String.format("Input size: %d, got %d", c.getRectangles().size(), rectangles.size()), c.getRectangles().size() == rectangles.size());
                    assertHeightLimitRespected(c, rectangles);
                    //assertNoOverlap(rectangles);
                }
            });
            paths.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

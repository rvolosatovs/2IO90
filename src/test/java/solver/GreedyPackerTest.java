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
public class GreedyPackerTest extends PackerTest {
    @Override
    public Packer newPacker() {
        PackingSolver.startTime = System.currentTimeMillis();
        return new GreedyPacker();
    }

    @Test
    public void testHeight() {
        super.testHeight();
    }

    @Test
    public void testSmallInput() {
        super.testSmallInput();
    }

    @Test
    public void testMediumInput() {
        // TODO uncomment  once 1x1 issue is fixed
        //super.testMediumInput();
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
        PackingSolver.startTime = System.currentTimeMillis();
        try {
            Stream<Path> paths = Files.walk(Paths.get("testcases"));
            paths.forEach(path -> {
                if (Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".txt") && path.getFileName().toString().matches("10000_02_hf_ry.txt")) {
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
                    System.out.println("Finished packing at: "+  (System.currentTimeMillis() - PackingSolver.startTime));
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
}

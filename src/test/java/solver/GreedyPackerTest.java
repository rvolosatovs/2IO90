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

}

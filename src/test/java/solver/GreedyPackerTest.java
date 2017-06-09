package solver;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by berrietrippe on 04/05/2017.
 */
public class GreedyPackerTest extends PackerTest {
    @Override
    public Packer newPacker() {
        return new GreedyPacker();
    }

    @Test
    public void testHeight() {
        super.testHeight();
    }

    @Test
    public void testOverlap() {
        super.testOverlap();
    }

    @Test
    public void testSmallInput() {
        super.testSmallInput();
    }

    @Test
    public void testOutputLengthSameSizes() {
        super.testOutputLengthSameSizes();
    }

    @Test
    public void testOutputLengthRandom() { super.testOutputLengthRandom();
    }

    @Test
    public void testOutputLengthEmpty() { super.testOutputLengthEmpty();
    }
}

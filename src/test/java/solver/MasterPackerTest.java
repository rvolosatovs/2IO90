package solver;

import org.junit.Test;

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
    public void testSmallInput() {
        super.testSmallInput();
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

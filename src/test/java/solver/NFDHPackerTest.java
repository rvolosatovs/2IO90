package solver;

import org.junit.Test;

public class NFDHPackerTest extends PackerTest {
    @Override
    public Packer newPacker() { return new NFDHPacker(); }

    @Test
    public void testOutputLengthRandom() {
        super.testOutputLengthRandom();
    }

    @Test
    public void testOutputLengthSameSizes() {
        super.testOutputLengthSameSizes();
    }

    @Test
    public void testOutputLengthEmpty() {
        super.testOutputLengthEmpty();
    }
}

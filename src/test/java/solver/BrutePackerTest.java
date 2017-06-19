package solver; /**
 * Created by berrietrippe on 23/05/2017.
 */
import org.junit.Test;

public class BrutePackerTest extends PackerTest {
    @Override
    public Packer newPacker() {
        return new BrutePacker();
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
}

/**
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
    public void testOverlap() {
        super.testOverlap();
    }

    @Test
    public void testSmallInput() {
        super.testSmallInput();
    }

    @Test
    public void testSensibleOutput() {
        super.testSensibleOutput();
    }
}

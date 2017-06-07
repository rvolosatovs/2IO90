package solver;
import org.junit.Test;

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
    public void test25() {
        super.test25();
    }

    @Test
    public void testSensibleOutput() {
        super.testSensibleOutput();
    }

    @Test
    public void testHELLO() {
        System.out.println("FUCK FUCK FUCK");
    }
}

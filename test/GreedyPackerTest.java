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
    public void checkHeight() {
        super.checkHeight();
    }

    @Test
    public void checkOverlap() {
        super.checkOverlap();
    }

    @Test
    public void testSmallInput() {
        super.testSmallInput();
    }
}

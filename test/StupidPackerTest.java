import org.junit.Test;

/**
 * Created by berrietrippe on 04/05/2017.
 */
public class StupidPackerTest extends PackerTest {
    @Override
    public Packer newPacker() {
        return new StupidPacker();
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
}
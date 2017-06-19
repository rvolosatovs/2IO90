package solver;

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
    public void testSmallInput() {
        super.testSmallInput();
    }

    @Test
    public void testMediumInput() {
        super.testMediumInput();
    }
}

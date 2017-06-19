package solver;

import org.junit.Test;

/**
 * Created by berrietrippe on 04/05/2017.
 */
public class NFDHPackerTest extends PackerTest {
    @Override
    public Packer newPacker() {
        return new NFDHPacker();
    }

    @Test
    public void testHeight() {
        super.testHeight();
    }

    @Test
    public void testFixedHeightInput() {
        super.testFixedHeightInput();
    }
}
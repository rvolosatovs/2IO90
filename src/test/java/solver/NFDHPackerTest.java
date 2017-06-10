package solver;

import org.junit.Test;

public class NFDHPackerTest extends PackerTest {
    @Override
    public Packer newPacker() { return new NFDHPacker(); }

    @Test
    public void testHeight() {
        super.testHeight();
    }
}

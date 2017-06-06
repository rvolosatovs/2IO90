import org.junit.Test;

public class NFDHPackerTest extends PackerTest {
    @Override
    public Packer newPacker() { return new NFDHPacker(); }

    @Test
    public void testOverlap() {
        super.testOverlap();
    }
}

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;

/**
 * Created by berrietrippe on 08/05/2017.
 */
public abstract class PackerTest {
    public abstract Packer newPacker();

    public void checkHeight() {
        Dimension[] dimensions = new Dimension[3];
        dimensions[0] = new Dimension(2, 3);
        dimensions[1] = new Dimension(5, 7);
        dimensions[2] = new Dimension(2, 4);
        int containerHeight = 6;

        Case c = new Case(containerHeight, true, dimensions);

        int height = newPacker().Pack(c).getHeight();

        assertFalse("Packer respects container height", height > containerHeight);
    }

}

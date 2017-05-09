import org.junit.jupiter.api.Test;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Created by berrietrippe on 08/05/2017.
 */
public abstract class PackerTest  {
    abstract Packer newPacker();

    @Test
    public void checkHeight(){
        Dimension[] dimensions = new Dimension[3];
        dimensions[0] = new Dimension(2,3);
        dimensions[1] = new Dimension(5,7);
        dimensions[2] = new Dimension(2,4);
        int containerheight = 6;

        Case c = new Case(containerheight, true, dimensions);

        int height = newPacker().Pack(c).getHeight();

        assertFalse(height > containerheight,"Packer respects container height" );
    }

}

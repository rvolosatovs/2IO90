import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        if (height > containerheight){
            assertFalse(true, "Packer exceeds allowed container height");
        } else {
            assertTrue(true,"Packer does not exceed allowed container height");
        }
    }

}

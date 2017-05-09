import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Collection;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by berrietrippe on 04/05/2017.
 */
class StupidPackerTest extends PackerTest{

    @Override
    Packer newPacker() {
        return new StupidPacker();
    }
}
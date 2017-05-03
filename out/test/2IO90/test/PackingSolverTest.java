import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by s154563 on 3-5-2017.
 */
class PackingSolverTest {

    @Test
    void main() {
        Case c;
        System.out.println(System.getProperty("user.dir"));
        try {
            c = new Case(new FileInputStream("test/cases/03_01_h20_rn.txt"));
        } catch (FileNotFoundException e) {
            assertFalse(true, e.toString());
            e.printStackTrace();
        }
        assertTrue(true, "File was read");
    }

}
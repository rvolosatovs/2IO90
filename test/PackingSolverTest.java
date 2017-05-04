import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by s154563 on 3-5-2017.
 */
class PackingSolverTest {

    @Test
    void readAllFilesTest() {
        try (Stream<Path> paths = Files.walk(Paths.get("test/cases"))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        Case c = new Case(new FileInputStream(String.valueOf(filePath)));
                    } catch (FileNotFoundException e) {
                        assertFalse(true, e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            assertFalse(true, e.getMessage());
        }
        assertTrue(true, "All files were correctly read and opened as a Case.");
    }

}
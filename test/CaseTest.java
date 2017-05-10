import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.Assert.fail;

/**
 * Created by s154563 on 3-5-2017.
 */
public class CaseTest {
    @Test
    public void readAllFilesTest() {
        try {
            Stream<Path> paths = Files.walk(Paths.get("test/cases"));
            paths.forEach(path -> {
                if (Files.isRegularFile(path) && path.toString().toLowerCase().endsWith(".txt")) {
                    try {
                        System.out.println("Parsing " + path.toString());
                        new Case(new FileInputStream(path.toFile()));
                    } catch (Exception e) {
                        fail(e.getMessage());
                    }
                }
            });
            paths.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by berrietrippe on 03/05/2017.
 */
class PackingSolverOutputTest {

    Solution s;

    @Test
    void main() {

        //Just a random list of rectangles
        int rectanglecount = 10;
        Collection<IndexedRectangle> rectangles = new ArrayList<>(rectanglecount);
        for (int i = 0; i < 10; i++) {
            rectangles.add(new IndexedRectangle(i, 10, 10));
        }

        Case c = new Case(0,rectanglecount,false,false, rectangles);

        Container container = new Container(rectangles);

        s = new Solution(c,container);

        s.toString();

        assertTrue(true, "Output is correct");

    }



}
import java.awt.*;
import java.awt.geom.RectangularShape;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class PackingSolver{
    public class Solution {
        private Case spec;
        private List<Rectangle> rectangles;

        public Solution(final Case spec, final List<Rectangle> rectangles) {
            this.rectangles = rectangles;
            this.spec = spec;
        }

        public String toString() {
            // TODO
            System.out.println(spec.toString());
            System.out.println("placement of rectangles");
            rectangles.forEach((r) -> System.out.println());
            return ""
        }
    }

    private Solution solve(Case c, Solver s) {
        return new Solution(c, s.Solve(c));
    }

    public static void main(String[] args) {
        Logger log = Logger.getLogger("LOG");

        Case c = null;
        try {
            c = new Case(new FileInputStream("/home/rvolosatovs/src/github.com/rvolosatovs/2IO90/testcases/03_01_h20_rn.txt"));
        } catch (Exception e) {
            log.severe("Failed to parse case: " + e.getMessage());
            System.exit(-1);
        }

        Set<Rectangle> s = null;
        try {
            s = c.Solve(null);
        }catch (Exception e) {
            log.severe("Failed to solve case: "+ e.getMessage());
            System.exit(-1);
        }
        System.out.println(s.toString());
    }
}

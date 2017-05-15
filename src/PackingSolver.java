import java.io.FileInputStream;
import java.util.logging.Logger;

public class PackingSolver {
    private static Solution solve(Case c, Packer p) {
        return new Solution(c, p.Pack(c));
    }

    public static void main(String[] args) {
        Logger log = Logger.getLogger("LOG");

        Case c = null;
        try {
            c = new Case(System.in);
        } catch (Exception e) {
            log.severe("Failed to parse case: " + e.getMessage());
            System.exit(-1);
        }

        Solution s = null;
        try {
            s = solve(c, new StupidPacker());
        } catch (Exception e) {
            log.severe("Failed to solve case: " + e.getMessage());
            System.exit(-1);
        }
        System.out.println(s);
    }
}

import java.util.logging.Logger;

public class PackingSolver {
    private static Solution solve(Case c, Packer p) throws Exception {
        return new Solution(c, p.Pack(c));
    }

    public static void main(String[] args) {
        long timeStamp = System.currentTimeMillis();
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
            s = solve(c, new GreedyPacker());
        } catch (Exception e) {
            log.severe("Failed to solve case: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Running time: " + (System.currentTimeMillis() - timeStamp) + "ms");
        System.out.println(s);
    }
}

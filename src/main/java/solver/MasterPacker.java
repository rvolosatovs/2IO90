package solver;

/**
 * Created by berrietrippe on 07/06/2017.
 */
public class MasterPacker implements Packer {

    public Container Pack(Case c) throws InterruptedException {
        int caseSize = c.getSize();
        switch (caseSize) {
            case 3:
                return (new BrutePacker().Pack(c));
            case 5:
                return (new BrutePacker().Pack(c));
            case 10:
                return (new GreedyPacker().Pack(c));
            case 25:
                return (new GreedyPacker().Pack(c));
            case 10000:
                return (new GreedyPacker().Pack(c));
            default:
                return (new GreedyPacker().Pack(c));
        }
    }
}

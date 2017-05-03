import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by rvolosatovs on 5/1/17.
 */
public class Case {
    private int containerHeight;
    private final int rectangleCount;
    private final boolean rotationsAllowed;
    private final boolean sizeFixed;
    private final List<Rectangle> rectangles;

    public Case(final int containerHeight, final int rectangleCount, final boolean sizeFixed, final boolean rotationsAllowed, final List<Rectangle> rectangles) {
        this.containerHeight = containerHeight;
        this.rectangleCount = rectangleCount;
        this.rotationsAllowed = rotationsAllowed;
        this.sizeFixed = sizeFixed;
        this.rectangles = rectangles;
    }

    public Case(final InputStream s) {
        Scanner sc = new Scanner(s);

        sizeFixed = sc.skip("container height:").next().equals("fixed");
        if (sizeFixed) {
            containerHeight = sc.nextInt();
        }
        rotationsAllowed = sc.skip("\\Rrotations allowed:").next().equals("yes");
        rectangleCount = sc.skip("\\Rnumber of rectangles:").nextInt();

        rectangles = new ArrayList<>(rectangleCount);
        for (int i = 0; sc.hasNext(); i++) {
            rectangles.add(new IndexedRectangle(i, sc.skip("\\R").nextInt(), sc.nextInt()));
        }
        sc.close();
    }

    public Solution Solve(Packer p){
        return new Solution(this, p.Pack(this));
    }

    public List<Rectangle> getRectangles() {
        return new ArrayList<>(rectangles);
    }

    public int getRectangleCount() {
        return rectangleCount;
    }

    public boolean isSizeFixed() {
        return sizeFixed;
    }

    public boolean areRotationsAllowed() {
        return rotationsAllowed;
    }

    public int getSize() throws Exception {
        if (isSizeFixed()) {
           return containerHeight;
        }
        throw new Exception("container is free sized");
    }
}

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

        sizeFixed = sc.skip("container height:").next() == "fixed";
        if (sizeFixed) {
            containerHeight = sc.nextInt();
        }
        rotationsAllowed = sc.skip("\\nrotations allowed:").next() == "yes";
        rectangleCount = sc.skip("\\nnumber of rectangles:").nextInt();

        rectangles = new ArrayList<>(rectangleCount);
        for (int i = 0; sc.hasNext(); i++) {
            rectangles.add(new Rectangle(i, sc.skip("\\n").nextInt(), sc.nextInt()));
        }
        sc.close();
    }

    public List<Rectangle> Solve(Solver s){
        return new Solution(this, s.Solve(this));
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

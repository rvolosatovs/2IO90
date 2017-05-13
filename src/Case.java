import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by rvolosatovs on 5/1/17.
 */
public class Case {
    private final boolean rotationsAllowed;
    private final Dimension[] dimensions;
    private int containerHeight;

    public Case(final boolean rotationsAllowed, final Dimension[] dimensions) {
        this.rotationsAllowed = rotationsAllowed;
        this.dimensions = dimensions;
    }

    public Case(final int containerHeight, final boolean rotationsAllowed, final Dimension[] dimensions) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.dimensions = dimensions;
    }

    public Case(final InputStream s) {
        Scanner sc = new Scanner(s);

        if (sc.skip("container height:").next().equals("fixed")) {
            containerHeight = sc.nextInt();
        }
        rotationsAllowed = sc.skip("\\Rrotations allowed:").next().equals("yes");

        dimensions = new Dimension[sc.skip("\\Rnumber of rectangles:").nextInt()];
        for (int i = 0; sc.hasNext(); i++) {
            dimensions[i] = new Dimension(sc.skip("\\R").nextInt(), sc.nextInt());
        }
        sc.close();
    }

    public Solution Solve(Packer p) {
        return new Solution(this, p.Pack(this));
    }

    public Collection<IndexedRectangle> getRectangles() {
        ArrayList<IndexedRectangle> rectangles = new ArrayList<>(dimensions.length);
        for (int i = 0; i < dimensions.length; i++) {
            rectangles.add(i, new IndexedRectangle(i, dimensions[i]));
        }
        return rectangles;
    }

    public boolean isSizeFixed() {
        return containerHeight > 0;
    }

    public boolean areRotationsAllowed() {
        return rotationsAllowed;
    }

    public int getSize() {
        if (!isSizeFixed()) {
            throw new Error("container is free sized");
        }
        return containerHeight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("container height: ");
        if (isSizeFixed()) {
            sb.append("fixed ")
                    .append(containerHeight);
        } else {
            sb.append("free");
        }

        sb.append("\n")
                .append("rotations allowed: ")
                .append(areRotationsAllowed() ? "yes" : "no")
                .append("\n")
                .append("number of rectangles: ")
                .append(dimensions.length);

        for (Dimension d : dimensions) {
            sb.append("\n")
                    .append(d.width)
                    .append(" ")
                    .append(d.height);
        }
        return sb.toString();
    }
}

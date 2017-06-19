package solver;

import java.awt.Dimension;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

    public List<IndexedRectangle> getRectangles() {
        ArrayList<IndexedRectangle> rectangles = new ArrayList<>(dimensions.length);
        for (int i = 0; i < dimensions.length; i++) {
            rectangles.add(i, new IndexedRectangle(i, dimensions[i]));
        }
        return rectangles;
    }

    public boolean isHeightFixed() {
        return containerHeight > 0;
    }

    public boolean areRotationsAllowed() {
        return rotationsAllowed;
    }

    public int getHeight() {
        if (!isHeightFixed()) {
            throw new Error("container is free sized");
        }
        return containerHeight;
    }

    public int getSize(){
        return dimensions.length;
    }

    public void setContainerHeight(int height) {
        containerHeight = height;
    }
}

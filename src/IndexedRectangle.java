import java.awt.Dimension;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class IndexedRectangle extends Rectangle {
    private final int index;
    private boolean rotated;

    public IndexedRectangle(final int index, final int width, final int height) {
        super(width, height);
        this.index = index;
    }

    public IndexedRectangle(final int index, int x, int y, final int width, final int height) {
        super(x, y, width, height);
        this.index = index;
    }

    public IndexedRectangle(final int index, final Rectangle rectangle) {
        super(rectangle);
        this.index = index;
    }

    public IndexedRectangle(final int index, final Dimension dimension) {
        super(dimension);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void rotate() {
        rotated = !rotated;
        setBounds(x, y, height, width);
    }

    public boolean wasRotated() {
        return rotated;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(x)
                .append(" ")
                .append(y)
                .toString();
    }
}

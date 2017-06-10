package solver;

import java.awt.Dimension;
import java.util.Objects;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class IndexedRectangle extends Rectangle {
    public final int index;

    public IndexedRectangle(final int index, final int width, final int height) {
        super(width, height);
        this.index = index;
    }

    public IndexedRectangle(final int index, int x, int y, final int width, final int height) {
        super(x, y, width, height);
        this.index = index;
    }

    public IndexedRectangle(final int index, final Dimension dimension) {
        super(dimension);
        this.index = index;
    }

    public IndexedRectangle(final int index, final Rectangle rectangle) {
        super(rectangle);
        this.index = index;
    }

    public IndexedRectangle(final IndexedRectangle rectangle) {
        super(rectangle);
        this.index = rectangle.index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IndexedRectangle && ((IndexedRectangle) o).index == this.index && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.index) + super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("i=%d %s", index, super.toString());
    }
}

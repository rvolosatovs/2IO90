import java.awt.*;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class IndexedRectangle extends Rectangle {
    private int index;

    public IndexedRectangle(final int index, final int width, final int height) {
        super(width, height);
        this.index = index;
    }

    public IndexedRectangle(final int index, Rectangle rectangle) {
        super(rectangle);
        this.index = index;
    }

    public IndexedRectangle(final int index, Dimension dimension) {
        super(dimension);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

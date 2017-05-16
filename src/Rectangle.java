import java.awt.*;

/**
 * Created by rvolosatovs on 5/16/17.
 */
public class Rectangle extends java.awt.Rectangle {
    public Rectangle(final int width, final int height) {
        super(width, height);
    }

    public Rectangle(final int x, final int y, final int width, final int height) {
        super(x, y, width, height);
    }

    public Rectangle(final Rectangle rectangle) {
        super(rectangle);
    }

    public Rectangle(final Dimension dimension) {
        super(dimension);
    }

    @Override
    public boolean contains(int x, int y) {
        return (x >= this.x && y >= this.y &&
                x <= this.x + this.width && y <= this.y + this.height);
    }
}

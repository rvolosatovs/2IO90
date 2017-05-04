import java.awt.*;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public abstract class Rectangle extends java.awt.Rectangle {
    private boolean rotated = false;

    public Rectangle(final int width, final int height) {
        super(width, height);
    }

    public Rectangle(Rectangle rectangle) {
        super(rectangle);
    }

    public Rectangle(Dimension dimension) {
        super(dimension);
    }

    public boolean wasRotated() {
        return rotated;
    }

    public void rotate() {
        rotated = !rotated;
        setBounds(x, y, width, height);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(x)
                .append(" ")
                .append(y)
                .append(" ");

        return sb.toString();
    }
}

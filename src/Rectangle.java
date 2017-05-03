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

    public boolean wasRotated() {
        return rotated;
    }

    public void rotate() {
        rotated = !rotated;
        // TODO shift coords
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(x)
                .append(" ")
                .append(y)
                .append(" ")
                .append(wasRotated() ? "yes" : "no");

        return sb.toString();
    }
}

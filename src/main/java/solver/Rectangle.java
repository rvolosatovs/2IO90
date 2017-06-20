package solver;

import java.awt.Dimension;

/**
 * Created by rvolosatovs on 5/16/17.
 */
public class Rectangle extends java.awt.Rectangle {
    private boolean rotated;

    public Rectangle(final int width, final int height) {
        super(width, height);
    }

    public Rectangle(final int x, final int y, final int width, final int height) {
        super(x, y, width, height);
    }

    public Rectangle(final Rectangle rectangle) {
        super(rectangle);
        rotated = rectangle.rotated;
    }

    public Rectangle(final Dimension dimension) {
        super(dimension);
    }

    public int getArea() {
        return Util.areaOf(this);
    }

    public void rotate() {
        rotated = !rotated;
        setBounds(x, y, height, width);
    }

    public void doubleRectangle() {
        setBounds(this.x * 2, this.y * 2, this.width * 2, this.height * 2);
    }

    public void halfRectangle() {
        setBounds(this.x / 2, this.y / 2, this.width / 2, this.height / 2);
    }

    public boolean wasRotated() {
        return rotated;
    }

    @Override
    public boolean contains(int x, int y) {
        return (x >= this.x && y >= this.y &&
                x <= this.x + this.width && y <= this.y + this.height);
    }

    @Deprecated
    @Override
    public boolean contains(java.awt.Rectangle r) {
        throw new Error("Fix your imports");
    }

    public Rectangle intersection(Rectangle r) {
        if (this.x <= r.x && r.x - this.x < this.width) {
            int width = this.x + this.width - r.x;

            if (this.y <= r.y && r.y - this.y < this.height) {
                return new Rectangle(r.x, r.y, width, this.y + this.height - r.y);
            } else if (this.y >= r.y && this.y - r.y <= r.height) {
                return new Rectangle(r.x, this.y, width, r.y + r.height - this.y);
            }
        } else if (this.x >= r.x && this.x - r.x < r.width) {
            int width = this.x + this.width - this.x;

            if (this.y <= r.y && r.y - this.y < this.height) {
                return new Rectangle(this.x, r.y, width, this.y + this.height - r.y);
            } else if (this.y >= r.y && this.y - r.y <= r.height) {
                return new Rectangle(this.x, this.y, width, r.y + r.height - this.y);
            }
        }
        return new Rectangle(0, 0);
    }

    @Deprecated
    @Override
    public java.awt.Rectangle intersection(java.awt.Rectangle r) {
        throw new Error("Fix your imports");
    }

    public boolean intersects(Rectangle r) {
        return !intersection(r).isEmpty();
    }

    @Deprecated
    @Override
    public boolean intersects(java.awt.Rectangle r) {
        throw new Error("Fix your imports");
    }

    @Override
    public String toString() {
        return String.format("[(%d,%d),(%d,%d),(%d,%d),(%d,%d)]", x, y, x, y + height, x + width, y + height, x + width, y);
    }
}

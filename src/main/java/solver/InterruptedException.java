package solver;

import java.util.List;

public class InterruptedException extends Exception {
    Container container;
    List<IndexedRectangle> rectangles;

    public InterruptedException(Container c, List<IndexedRectangle> l) {
        this.container = c;
        this.rectangles = l;
    }

    public Container getContainer() {
        return this.container;
    }

    public List<IndexedRectangle> getRectangles() {
        return this.rectangles;
    }
}

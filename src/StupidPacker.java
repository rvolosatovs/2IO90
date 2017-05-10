import java.util.Collection;

/**
 * Created by rvolosatovs on 5/3/17.
 */
public class StupidPacker implements Packer {
    public Container Pack(Case c) {
        Collection<IndexedRectangle> rectangles = c.getRectangles();

        int x = 0;
        if (c.isSizeFixed()) {
            for (IndexedRectangle r : rectangles) {
                if (r.getHeight() > c.getSize()) {
                    if (c.areRotationsAllowed()) {
                        r.rotate();
                    }
                }

                r.setLocation(x, 0);
                x += r.width;
            }
        } else {
            for (IndexedRectangle r : rectangles) {
                r.setLocation(x, 0);
                x += r.width;
            }
        }
        return new Container(rectangles);
    }
}

import java.util.Collection;
import java.util.*;
import java.awt.*;
import java.lang.*;

/**
 *  Next fit decreasing height packing
 *  Only use this when fixed height.
 *
 *  In sorted on width order; place rectangle with biggest height in left bottom
 *  then if fits, add next rectangle on top, if not, place it against new 'wall',
 *  the max x position of the biggest rectangle in previous row.
 *
 *
 */
public class NFDHPacker implements Packer {

    private ArrayList<IndexedRectangle> sortOnHeight(Collection<IndexedRectangle> rectangles, Case c) {
        ArrayList<IndexedRectangle> result = new ArrayList<>();

        for (IndexedRectangle rectangle: rectangles) {
            if (c.areRotationsAllowed()) {
                if (rectangle.height > rectangle.width) {
                    if (c.getHeight() > rectangle.height) {
                        rectangle.rotate();
                    }
                }
                if (rectangle.height > c.getHeight()){
                    rectangle.rotate();
                }
            }

            if (result.isEmpty()) {
                result.add(rectangle);
            } else {
                int width = rectangle.width;
                for (int i = 0; i < result.size(); i++) {
                    if (width >= result.get(i).width) {
                        result.add(i, rectangle);
                        break;
                    }
                }
                if (!result.contains(rectangle)) {
                    result.add(rectangle);
                }
            }
        }
        return result;
    }

    public Container Pack(Case c) {
        Collection<IndexedRectangle> originalRectangles = c.getRectangles();

        ArrayList<IndexedRectangle> sortedRectangles = sortOnHeight(originalRectangles, c);

        int y = 0;
        int potWall = 0;
        int wall = 0;

        if (c.isHeightFixed()) {
            for (IndexedRectangle r : sortedRectangles) {

                if(y + r.height > c.getHeight()){
                    wall = potWall;
                    y=0;
                    potWall = wall + r.width;
                }

                r.setLocation(wall, y);
                y += r.getHeight();
                potWall = Math.max((int)r.getWidth(), potWall);

            }
        }
        return new Container(sortedRectangles);

    }
}

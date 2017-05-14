import java.util.Collection;
import java.util.*;
import java.awt.*;
import java.lang.*;

/**
 *  Next fit decreasing height packing
 *  Only use this when fixed height.
 *
 *  In sorted on height order; place rectangle with biggest height in left bottom
 *  then if fits, add next rectangle on top, if not, place it against new 'wall',
 *  the max x position of the biggest regtangle in previous row.
 *
 */
public class NFDHPacker implements Packer {

    /**
     * credits to Mr. Roman
     * Sorts a Collection of IndexedRectangles on their height.
     * @param rectangles
     * @return Sorted descending ArrayList on height with the rectangles
     */
    private ArrayList<IndexedRectangle> sortOnHeight(Collection<IndexedRectangle> rectangles) {
        ArrayList<IndexedRectangle> result = new ArrayList<>();

        for (IndexedRectangle rectangle: rectangles) {
            if (result.isEmpty()) {
                result.add(rectangle);
            } else {
                int height = rectangle.height;
                for (int i = 0; i < result.size(); i++) {
                    if (height >= result.get(i).height) {
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

        ArrayList<IndexedRectangle> sortedRectangles = sortOnHeight(originalRectangles);

        int y = 0;
        int pot_wall = 0;
        int wall = 0;

        if (c.isHeightFixed()) {
            for (IndexedRectangle r : sortedRectangles) {

                if(y + r.getHeight() > c.getHeight()){
                    wall = pot_wall;
                    y=0;
                }

                r.setLocation(wall, y);
                y += r.getHeight();
                pot_wall = Math.max((int)r.getWidth(), pot_wall);

            }
        }
        return new Container(sortedRectangles);

    }
}

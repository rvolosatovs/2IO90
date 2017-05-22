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
     * credits to Mr. Jolan
     * Sorts a Collection of IndexedRectangles on their height.
     * @param rectangles
     * @return Sorted descending ArrayList on height with the rectangles
     */
    private ArrayList<IndexedRectangle> sortOnHeight(Collection<IndexedRectangle> rectangles, Case c) {
        ArrayList<IndexedRectangle> result = new ArrayList<>();

        for (IndexedRectangle rectangle: rectangles) {
            if (c.areRotationsAllowed()) {
                System.out.println("\n");
                System.out.println("width: "+ rectangle.width);
                if (rectangle.height > rectangle.width) {
                    if (c.getHeight() > rectangle.height) {
                        rectangle.rotate();
                    }
                }
                if (rectangle.height > c.getHeight()){
                    System.out.println("swapped"+ rectangle.height +" "+rectangle.width);
                    rectangle.rotate();
                }
                System.out.println("width: "+ rectangle.width);
            }

            if (result.isEmpty()) {
                System.out.println("vvvv");
                result.add(rectangle);
            } else {
                int height = rectangle.height;
                for (int i = 0; i < result.size(); i++) {
                    if (height >= result.get(i).height) {
                        result.add(i, rectangle);
                        System.out.println("vvvfvffvvffvfvvv");
                        break;
                    }
                }
                if (!result.contains(rectangle)) {
                    System.out.println("vzzzzzzz");
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

                if(y + r.getHeight() > c.getHeight()){
                    wall = potWall;
                    y=0;
                }

                r.setLocation(wall, y);
                y += r.getHeight();
                potWall = Math.max((int)r.getWidth(), potWall);

            }
        }
        return new Container(sortedRectangles);

    }
}

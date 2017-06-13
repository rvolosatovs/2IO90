package solver;

import java.util.Collection;
import java.util.*;
import java.awt.*;
import java.lang.*;
import java.util.List;

/**
 *  Next fit decreasing height packing
 *  Only use this when fixed height.
 *
 *  In sorted on width order; place rectangle with biggest height in left bottom
 *  then if fits, add next rectangle on top, if not, place it against new 'wall',
 *  the max x position of the biggest rectangle in previous row.
 *
 */
public class NFDHPacker implements Packer {

    public Container Pack(Case c) {
        List<IndexedRectangle> rectangles = c.getRectangles();

        if(c.areRotationsAllowed()){
            Util.sortByLongestWidth(rectangles, c);
        } else{
            Util.sortByWidth(rectangles);
        }

        int y = 0;
        int potWall = 0;
        int wall = 0;

        if (c.isHeightFixed()) {
            for (IndexedRectangle r : rectangles) {
                if(y + r.height > c.getHeight()){
                    wall = potWall;
                    y = 0;
                    potWall = wall + r.width;
                }

                r.setLocation(wall, y);
                y += r.height;
                potWall = Math.max(r.width, potWall);
            }
        }
        return new Container(rectangles);
    }

    public Container Pack(Container c, List<IndexedRectangle> rectangles) {

        int y = 0;
        int potWall = c.getWidth();
        int wall = 0;


        for (IndexedRectangle r : rectangles) {
            if (y + r.height > c.getHeight()){
                wall = potWall;
                y = 0;
                potWall = wall + r.width;
            }

            r.setLocation(wall, y);
            y += r.height;
            potWall = Math.max(r.width, potWall);
        }

        rectangles.addAll(c);
        return new Container(rectangles);
    }
}

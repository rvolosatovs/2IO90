

import java.util.Collection;

/**
 * Created by rvolosatovs on 5/3/17.
 */
public class StupidPacker implements Packer {
    public Container Pack(Case c) {

        Collection<IndexedRectangle> collection = c.getRectangles();

        Container container = new Container(collection);

        int placement = 0;

        if (c.isSizeFixed()){
            for (IndexedRectangle rec : collection){
                try {
                    if (rec.getHeight() > c.getSize()) {
                        if (c.areRotationsAllowed()){
                            rotateRectangle(rec);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Exception: " + e.getMessage());
                }

                rec.setLocation(placement, 0);
                placement += rec.getWidth();
            }
        } else {
            for (IndexedRectangle rec : collection){
                rec.setLocation(placement, 0);
                placement += rec.getWidth();
            }
        }

        return container;
    }

    public void rotateRectangle(Rectangle rectangle){
        //Make double and int comparable
        Double width = rectangle.getWidth();
        Double height = rectangle.getHeight();
        int w = width.intValue();
        int h = height.intValue();

        rectangle.height = w;
        rectangle.width = h;
    }

}



import java.util.Collection;

/**
 * Created by rvolosatovs on 5/3/17.
 */
public class StupidPacker implements Packer {
    public Container Pack(Case c) {

        Collection<IndexedRectangle> collection = c.getRectangles();

        Container container = new Container(collection);

        int placement = 0;

        for (IndexedRectangle rec : collection){
            rec.setLocation(placement, 0);
            placement += rec.getWidth();
        }

        return container;
    }
}

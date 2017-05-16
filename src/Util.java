import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by rvolosatovs on 5/15/17.
 */
class Util {
    static int areaOf(Rectangle r) {
        return r.width * r.height;
    }

    /**
     * Sorts a Collection of IndexedRectangles by their area in decreasing order
     *
     * @param rectangles
     * @return Sorted ArrayList containing sorted rectangles
     */
    static ArrayList<IndexedRectangle> sortByArea(Collection<? extends IndexedRectangle> rectangles) {
        ArrayList<IndexedRectangle> result = new ArrayList<>(rectangles);

        Collections.sort(result, (r1, r2) -> {
            int area1 = areaOf(r1);
            int area2 = areaOf(r2);

            if (area1 > area2) {
                return 1;
            }
            if (area1 < area2) {
                return -1;
            }
            return 0;
        });
        return result;
    }
}

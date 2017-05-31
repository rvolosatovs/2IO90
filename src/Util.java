import java.util.List;


/**
 * Created by rvolosatovs on 5/15/17.
 */
public class Util {
    public static int areaOf(Rectangle r) {
        return r.width * r.height;
    }

    /**
     * Sorts a Collection of objects extending Dimension by their area in decreasing order
     *
     * @param dimensions
     */
    public static void sortByArea(List<? extends Rectangle> dimensions) {
        dimensions.sort((d1, d2) -> {
            int area1 = (areaOf(d1));
            int area2 = (areaOf(d2));

            if (area1 < area2) {
                return 1;
            }
            if (area1 > area2) {
                return -1;
            }
            return 0;
        });
    }

    public static void sortByIndex(List<? extends IndexedRectangle> rectangles) {
        rectangles.sort((r1, r2) -> {
            int index1 = r1.getIndex();
            int index2 = r2.getIndex();

            if (index1 < index2) {
                return -1;
            }
            if (index1 > index2) {
                return 1;
            }
            return 0;
        });
    }

    public static void sortByWidth(List<? extends Rectangle> dimensions, Case c) {
        dimensions.forEach((r)->{
            if(c.areRotationsAllowed() && r.height > r.width && c.getHeight() > r.height){
                r.rotate();
            }});

        dimensions.sort((d1, d2) -> {

            int width1 = (d1.width);
            int width2 = (d2.width);

            if (width1 < width2) {
                return 1;
            }
            if (width1 > width2) {
                return -1;
            }
            return 0;
        });
    }
}

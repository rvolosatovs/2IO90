package solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Solution {
    private Case spec;
    private Collection<IndexedRectangle> rectangles;

    public Solution(final Case spec, final Collection<IndexedRectangle> rectangles) {
        this.rectangles = rectangles;
        this.spec = spec;
    }

    public Solution(final Case spec, final Packer p) {
        this.spec = spec;
        this.rectangles = p.Pack(spec);
        Logger.getGlobal().info(this.rectangles.toString());
    }

    public String toString() {
        List<IndexedRectangle> rectangles = new ArrayList<>(this.rectangles);
        Util.sortByIndex(rectangles);

        StringBuilder sb = new StringBuilder();

        sb.append("container height: ").append(spec.isHeightFixed() ? String.format("fixed %d", spec.getHeight()) : "free");

        sb.append("\n")
                .append("rotations allowed: ").append(spec.areRotationsAllowed() ? "yes" : "no").append("\n")
                .append("number of rectangles: ").append(rectangles.size());

        rectangles.forEach(r -> sb.append("\n")
                .append(r.width).append(" ").append(r.height));

        sb.append("\n")
                .append("placement of rectangles");

        rectangles.forEach(r -> {
            sb.append("\n");
            if (spec.areRotationsAllowed()) sb.append(r.wasRotated() ? "yes " : "no ");
            sb.append(r.x).append(" ").append(r.y);
        });
        return sb.toString();
    }
}
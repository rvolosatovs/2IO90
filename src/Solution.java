import java.util.ArrayList;
import java.util.List;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Solution {
    private Case spec;
    private Container container;

    public Solution(final Case spec, final Container container) {
        this.container = container;
        this.spec = spec;
    }

    public String toString() {
        boolean rotationsAllowed = spec.areRotationsAllowed();

        List<IndexedRectangle> rectangles = new ArrayList(container);
        Util.sortByIndex(rectangles);

        StringBuilder sb = new StringBuilder();
        sb.append(spec.toString())
                .append("\n")
                .append("placement of rectangles");
        rectangles.forEach((r) -> {
            sb.append("\n");
            if (rotationsAllowed) {
                sb.append(r.wasRotated() ? "yes " : "no ");
            }
            sb.append(r.toString());
        });
        return sb.toString();
    }
}

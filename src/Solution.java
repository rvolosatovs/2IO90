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

        StringBuilder sb = new StringBuilder();
        sb.append(spec.toString())
                .append("\n")
                .append("placement of rectangles");
        container.getRectangles().forEach((r) -> {
            sb.append("\n");
            if (rotationsAllowed) {
                sb.append(r.wasRotated() ? "yes " : "no ");
            }
            sb.append(r.toString());
        });
        return sb.toString();
    }
}

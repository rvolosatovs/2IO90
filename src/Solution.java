import java.util.List;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public class Solution {
    private Case spec;
    private List<Rectangle> rectangles;

    public Solution(final Case spec, final List<Rectangle> rectangles) {
       this.rectangles = rectangles;
       this.spec = spec;
    }

    public String toString() {
        // TODO
        System.out.println(spec.toString());
        System.out.println("placement of rectangles");
        rectangles.forEach((r) -> System.out.println());
        return ""
    }
}

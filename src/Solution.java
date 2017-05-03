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
        return spec.toString() + container.toString();
    }
}



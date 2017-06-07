package solver;

import java.util.Collection;

/**
 * Created by rvolosatovs on 5/2/17.
 */
public interface Packer {
    Collection<IndexedRectangle> Pack(Case c);
}



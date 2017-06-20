package solver;

import java.util.ArrayList;

/**
 * Created by rvolosatovs on 6/20/17.
 */
class IntegerPlane extends Plane<Integer> {
    IntegerPlane() {
        super();
    }

    IntegerPlane(int height) {
        super(height);
    }

    void add(int x, int y) {
        ArrayList<Integer> row = getRow(y);

        int width = row.size();
        if (width > x) {
            Integer val = row.get(x);
            if (val  == null) {
                val = 1;
            } else {
                val++;
            }
            row.set(x, val);
        } else {
            row.ensureCapacity(x);
            for (int i = width; i < x; i++) {
                row.add(null);
            }
            row.add(1);
        }
    }

    void remove(int x, int y) {
        ArrayList<Integer> row = getRow(y);
        row.set(x, row.get(x) - 1);
    }

    boolean isOccupied(int x, int y) {
        Integer val = getValue(x, y);
        if (val == null || val == 0) return false;
        if (x == 0 || y == 0) return x == y || val == 2;
        return val == 4;
    }
}


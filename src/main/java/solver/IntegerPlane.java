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

    @Override
    boolean isZero(Integer val) {
        return super.isZero(val) || val == 0;
    }

    void add(int x, int y) {
        ArrayList<Integer> row = getRow(y);

        int width = row.size();
        if (width > x) {
            row.set(x, row.get(x) + 1);
        } else {
            row.ensureCapacity(x);
            for (int i = width; i < x; i++) {
                row.add(0);
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
        if (isZero(val)) return false;
        if (x == 0 || y == 0) return x == y || val == 2;
        return val == 4;
    }
}


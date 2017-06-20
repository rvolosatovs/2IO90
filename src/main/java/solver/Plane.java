package solver;

import java.util.ArrayList;

/**
 * Created by rvolosatovs on 6/20/17.
 */
class Plane<T> {
    private ArrayList<ArrayList<T>> rows;

    Plane() {
        rows = new ArrayList<>();
    }

    Plane(int height) {
        rows = new ArrayList<>(height);
    }

    int getHeight() {
        for (int i = rows.size() - 1; i >= 0; i--) {
            for (T v : rows.get(i)) {
                if (!isZero(v)) {
                    return i;
                }
            }
        }
        return 0;
    }

    int getWidth() {
        int maxWidth = 0;
        for (ArrayList<T> row : rows) {
            int width = row.size() - 1;
            if (width <= maxWidth) {
                continue;
            }
            for (; width != maxWidth; width--) {
                if (!isZero(row.get(width))) {
                    maxWidth = width;
                    break;
                }
            }
        }
        return maxWidth;
    }

    ArrayList<T> getRow(int y) {
        int height = rows.size();
        if (height > y) {
            return rows.get(y);
        }

        ArrayList<T> row = new ArrayList<>();
        rows.ensureCapacity(y);
        for (int i = height; i < y; i++) {
            rows.add(new ArrayList<>());
        }
        rows.add(row);
        return row;
    }

    T getValue(int x, int y) {
        ArrayList<T> row = getRow(y);
        if (x < row.size()) {
            return row.get(x);
        }
        return null;
    }

    void add(int x, int y, T v) {
        ArrayList<T> row = getRow(y);

        int width = row.size();
        if (width > x) {
            row.set(x, v);
        } else {
            row.ensureCapacity(x);
            for (int i = width; i < x; i++) {
                row.add(null);
            }
            row.add(v);
        }
    }

    void remove(int x, int y) {
        ArrayList<T> row = getRow(y);
        row.set(x, null);
    }

    boolean isZero(T val) {
        return val == null;
    }

    boolean contains(int x, int y) {
        return !isZero(getValue(x, y));
    }

    boolean hasEmptyNeighbour(int x, int y) {
        for (int dy = -1; dy < 2; dy++) {
            int newY = y + dy;
            ArrayList<T> row = getRow(newY);
            for (int dx = -1; dx < 2; dx++) {
                int newX = x + dx;
                if (newX == x && newY == y) {
                    continue;
                }
                if (row.size() <= newX || row.get(newX) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = getHeight(); y >= 0; y--) {
            sb.append(String.format("%d\t", y));
            for (int x = 0; x <= getWidth(); x++) {
                sb.append(getValue(x, y)).append("  ");
            }
            sb.append("\n");
        }
        sb.append("\t");
        for (int x = 0; x <= getWidth(); x++) {
            String format = "%d ";
            if (x < 10) {
                format += " ";
            }
            sb.append(String.format(format, x));
        }
        return sb.toString();
    }

}

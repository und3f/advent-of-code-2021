package name.zasenko.aoc21.day19;

import java.util.Arrays;
import java.util.List;

public class Coord implements Comparable<Coord> {
    private final int[] v;

    public Coord(List<Integer> vect) {
        this.v = new int[3];
        for (int i = 0; i < 3; i++)
            this.v[i] = vect.get(i);
    }

    public Coord(int[] vect) {
        this.v = vect;
    }

    public Coord(int x, int y, int z) {
        this.v = new int[]{x, y, z};
    }

    public int[] getCoord() {
        return v;
    }

    public int distanceTo(Coord source) {
        int distance = 0;
        for (int i = 0; i < v.length; i++) {
            distance += Math.abs(source.v[i] - this.v[i]);
        }
        return distance;
    }

    @Override
    public String toString() {
        return Arrays.toString(v);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        for (int j : v) result = j + result * prime;

        return result;
    }

    @Override
    public boolean equals(Object coord2) {
        if (coord2 == this) return true;

        if (!(coord2 instanceof Coord)) {
            return false;
        }

        return Arrays.equals(this.v, ((Coord) coord2).v);
    }

    @Override
    public int compareTo(Coord coord) {
        return Arrays.compare(this.v, coord.v);
    }

    public Coord vector(Coord destination) {
        int[] v = new int[3];
        for (int i = 0; i < v.length; i++) {
            v[i] = this.v[i] - destination.v[i];
        }
        return new Coord(v);
    }
}

package name.zasenko.aoc21.day19;

import java.util.Iterator;
import java.util.function.Function;

class CoordRotationsIterator implements Iterator<Coord> {
    final int[] v;


    int i = 0;
    public CoordRotationsIterator(Coord c) {
        this.v = c.getCoord();
    }

    @Override
    public boolean hasNext() {
        return i < rotations.length;
    }

    @Override
    public Coord next() {
        Coord c = new Coord(rotations[i].apply(v));
        i++;
        return c;
    }

    private interface Rotation extends Function<int[], int[]> {}

    // Found at https://preview.redd.it/55v2qywirk681.png?width=942&format=png&auto=webp&s=d3609a802ace1199c6f62616e5b02cc78663a69e
    private final static Rotation[] rotations = new Rotation[]{
            v -> v,
            v -> new int[]{-v[0], -v[1], v[2]},
            v -> new int[]{-v[0], v[1], -v[2]},
            v -> new int[]{v[0], -v[1], -v[2]},

            v -> new int[]{-v[0], v[2], v[1]},
            v -> new int[]{v[0], v[2], -v[1]},
            v -> new int[]{v[0], -v[2], v[1]},
            v -> new int[]{-v[0], -v[2], -v[1]},

            v -> new int[]{v[1], -v[0], v[2]},
            v -> new int[]{-v[1], v[0], v[2]},
            v -> new int[]{v[1], v[0], -v[2]},
            v -> new int[]{-v[1], -v[0], -v[2]},

            v -> new int[]{v[2], v[0], v[1]},
            v -> new int[]{v[2], -v[0], -v[1]},
            v -> new int[]{-v[2], -v[0], v[1]},
            v -> new int[]{-v[2], v[0], -v[1]},

            v -> new int[]{v[1], v[2], v[0]},
            v -> new int[]{-v[1], v[2], -v[0]},
            v -> new int[]{v[1], -v[2], -v[0]},
            v -> new int[]{-v[1], -v[2], v[0]},

            v -> new int[]{v[2], v[1], -v[0]},
            v -> new int[]{v[2], -v[1], v[0]},
            v -> new int[]{-v[2], v[1], v[0]},
            v -> new int[]{-v[2], -v[1], -v[0]},
    };
}

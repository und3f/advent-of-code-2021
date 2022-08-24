package name.zasenko.aoc21.day22;

import name.zasenko.aoc21.day19.Coord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cuboid {
    public static final int PLANE_Z = 2;
    private final Coord start, end;

    private final boolean status;

    public Cuboid(boolean status, Coord start, Coord end) {
        this.status = status;
        this.start = start;
        this.end = end;
    }

    final static Pattern cuboidPattern = Pattern.compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");
    public static Cuboid fromString(String cuboidString) {
        if (cuboidString == null)
            throw new IllegalArgumentException("String can not be null");

        Matcher m = cuboidPattern.matcher(cuboidString);
        if (!m.matches()) {
            throw new IllegalArgumentException("String doesn't match a cuboid pattern");
        }

        boolean status = m.group(1).equals("on");
        Coord start = new Coord(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(4)), Integer.parseInt(m.group(6)));
        Coord end = new Coord(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(5)), Integer.parseInt(m.group(7)));

        return new Cuboid(status, start, end);
    }

    @Override
    public String toString() {
        return String.format("%s x=%d..%d, y=%d..%d, z=%d..%d",
                status ? "on" : "off",
                start.getCoord()[0],
                end.getCoord()[0],
                start.getCoord()[1],
                end.getCoord()[1],
                start.getCoord()[2],
                end.getCoord()[2]
        );
    }

    public int[] getCoord(int i) {
        return new int[]{start.getCoord()[i], end.getCoord()[i]};
    }

    public boolean getStatus() {
        return status;
    }
}

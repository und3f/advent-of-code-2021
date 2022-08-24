package name.zasenko.aoc21.day22;

public class CuboidRange implements Range {
    final private Cuboid cuboid;
    final private int plane;

    public CuboidRange(Cuboid cuboid, int plane) {
        assert(cuboid != null);
        this.cuboid = cuboid;
        this.plane = plane;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    @Override
    public int getStart() {
        return cuboid.getCoord(plane)[0];
    }

    @Override
    public int getEnd() {
        return cuboid.getCoord(plane)[1];
    }

    @Override
    public boolean isEnabled() {
        return cuboid.getStatus();
    }

    @Override
    public String toString() {
        return "[" + getStart() + ":" + getEnd() + "]: " + isEnabled();
    }
}

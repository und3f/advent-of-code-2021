package name.zasenko.aoc21.day22;

import java.util.*;

import static name.zasenko.aoc21.day22.Cuboid.PLANE_Z;

public class CuboidIntersection {
    final Cuboid bounds;
     final ArrayList<Cuboid> cuboids = new ArrayList<>();

    public CuboidIntersection() {
        bounds = null;
    }

    public CuboidIntersection(Cuboid bounds) {
        this.bounds = bounds;
    }

    public long calculateCubes() {
        final int PLANE = 0;

        RangeIntersection<CuboidRange> intersection = createCuboidRangeIntersection(PLANE);
        for (Cuboid cuboid : cuboids) {
            intersection.addRange(new CuboidRange(cuboid, PLANE));
        }

        return intersection.countPoints();
    }

    private RangeIntersection<CuboidRange> createCuboidRangeIntersection(int plane) {
        RangeIntersection.RangeValueCalculator<CuboidRange> cuboidRangeCalculator = new CuboidRangeCalculator(plane);
        if (bounds != null) {
            cuboidRangeCalculator = new BoundsRangeValueCalculator<>(bounds.getCoord(plane)[0], bounds.getCoord(plane)[1], cuboidRangeCalculator);
        }

        RangeIntersection<CuboidRange> intersection = new RangeIntersection<>(cuboidRangeCalculator);
        return intersection;
    }

    public void add(Cuboid cuboid) {
        cuboids.add(cuboid);
    }

    private class CuboidRangeCalculator implements RangeIntersection.RangeValueCalculator<CuboidRange> {
        final int plane;
        public CuboidRangeCalculator(int plane) {
            this.plane = plane;
        }

        @Override public long calculateValue(SortedSet<CuboidRange> currentRanges, int start, int end) {
            RangeIntersection intersection;

            if (plane < PLANE_Z - 1) {
                intersection = createCuboidRangeIntersection(plane + 1);
            } else {
                var calculator = RangeIntersection.singleDimensionRangeCalculator;
                if (bounds != null)
                    calculator = new BoundsRangeValueCalculator<>(bounds.getCoord(PLANE_Z)[0], bounds.getCoord(PLANE_Z)[1], calculator);

                intersection = new RangeIntersection(calculator);
            }

//            System.out.println("\t".repeat(plane) + " [" + start + ":" + end + "]:" + currentRanges.toString());

            for (CuboidRange cuboid : currentRanges) {
                intersection.addRange(new CuboidRange(cuboid.getCuboid(), plane + 1));
            }

            long count = new SingleDimensionRange(start, end, true).length() * intersection.countPoints();
//            System.out.println("\t".repeat(plane) + count);
            return count;
        }
    }
}

package name.zasenko.aoc21.day22;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day22 {
    public static void main(String[] args) {
        ArrayList<Cuboid> cuboids = getCuboids();

        System.out.println("Part1: " + part1(cuboids));
        System.out.println("Part2: " + part2(cuboids));
    }

    static long part1(List<Cuboid> cuboids) {
        Cuboid bounds = Cuboid.fromString("on x=-50..50,y=-50..50,z=-50..50");
        CuboidIntersection intersection = createCuboidsIntersection(cuboids, bounds);

        return intersection.calculateCubes();
    }

    static long part2(List<Cuboid> cuboids) {
        return createCuboidsIntersection(cuboids, null).calculateCubes();
    }
    private static CuboidIntersection createCuboidsIntersection(List<Cuboid> cuboids, Cuboid bounds) {
        CuboidIntersection intersection = new CuboidIntersection(bounds);
        for (Cuboid c : cuboids) {
            intersection.add(c);
        }
        return intersection;
    }

    private static ArrayList<Cuboid> getCuboids() {
        ArrayList<Cuboid> cuboids = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            cuboids.add(Cuboid.fromString(sc.nextLine()));
        }
        return cuboids;
    }

}

package name.zasenko.aoc21.day22;

import name.zasenko.aoc21.day19.Coord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CuboidIntersectionTest {
    CuboidIntersection intersection;

    @BeforeEach
    void init() {
        intersection = new CuboidIntersection(Cuboid.fromString("on x=-50..50,y=-50..50,z=-50..50"));
    }

    @Test
    void emptyTest() {
        assertEquals(0, intersection.calculateCubes());
    }

    @Test
    void singleTest() {
        intersection.add(new Cuboid(true, new Coord(1, 1, 1), new Coord(3, 3, 3)));
        assertEquals(3 * 3 * 3, intersection.calculateCubes());
    }

    @Test
    void overlapTest() {
        intersection.add(new Cuboid(true, new Coord(1, 1, 1), new Coord(3, 3, 3)));
        intersection.add(new Cuboid(true, new Coord(2, 1, 1), new Coord(4, 3, 3)));
        assertEquals(4 * 3 * 3, intersection.calculateCubes());
    }

    @Test
    void overlapDisabledTest() {
        intersection.add(new Cuboid(true, new Coord(1, 1, 1), new Coord(3, 3, 3)));
        intersection.add(new Cuboid(true, new Coord(2, 1, 1), new Coord(4, 3, 3)));
        intersection.add(new Cuboid(false, new Coord(1, 1, 1), new Coord(3, 3, 3)));

        assertEquals(1 * 3 * 3, intersection.calculateCubes());
    }

    @Test
    void complexTest() {
        intersection.add(Cuboid.fromString("on x=10..12,y=10..12,z=10..12"));
        intersection.add(Cuboid.fromString("on x=11..13,y=11..13,z=11..13"));
        intersection.add(Cuboid.fromString("off x=9..11,y=9..11,z=9..11"));
        intersection.add(Cuboid.fromString("on x=10..10,y=10..10,z=10..10"));

        assertEquals(27 + 19 - 8 + 1, intersection.calculateCubes());
    }

    @Test
    void largerTest() {
        intersection.add(Cuboid.fromString("on x=-20..26,y=-36..17,z=-47..7"));
        intersection.add(Cuboid.fromString("on x=-20..33,y=-21..23,z=-26..28"));
        intersection.add(Cuboid.fromString("on x=-22..28,y=-29..23,z=-38..16"));
        intersection.add(Cuboid.fromString("on x=-46..7,y=-6..46,z=-50..-1"));
        intersection.add(Cuboid.fromString("on x=-49..1,y=-3..46,z=-24..28"));
        intersection.add(Cuboid.fromString("on x=2..47,y=-22..22,z=-23..27"));
        intersection.add(Cuboid.fromString("on x=-27..23,y=-28..26,z=-21..29"));
        intersection.add(Cuboid.fromString("on x=-39..5,y=-6..47,z=-3..44"));
        intersection.add(Cuboid.fromString("on x=-30..21,y=-8..43,z=-13..34"));
        intersection.add(Cuboid.fromString("on x=-22..26,y=-27..20,z=-29..19"));
        intersection.add(Cuboid.fromString("off x=-48..-32,y=26..41,z=-47..-37"));
        intersection.add(Cuboid.fromString("on x=-12..35,y=6..50,z=-50..-2"));
        intersection.add(Cuboid.fromString("off x=-48..-32,y=-32..-16,z=-15..-5"));
        intersection.add(Cuboid.fromString("on x=-18..26,y=-33..15,z=-7..46"));
        intersection.add(Cuboid.fromString("off x=-40..-22,y=-38..-28,z=23..41"));
        intersection.add(Cuboid.fromString("on x=-16..35,y=-41..10,z=-47..6"));
        intersection.add(Cuboid.fromString("off x=-32..-23,y=11..30,z=-14..3"));
        intersection.add(Cuboid.fromString("on x=-49..-5,y=-3..45,z=-29..18"));
        intersection.add(Cuboid.fromString("off x=18..30,y=-20..-8,z=-3..13"));
        intersection.add(Cuboid.fromString("on x=-41..9,y=-7..43,z=-33..15"));
        intersection.add(Cuboid.fromString("on x=-54112..-39298,y=-85059..-49293,z=-27449..7877"));
        intersection.add(Cuboid.fromString("on x=967..23432,y=45373..81175,z=27513..53682"));

        assertEquals(590784, intersection.calculateCubes());
    }

    @Test
    void theLargestTest() {
        CuboidIntersection fullIntersection = new CuboidIntersection();

        Scanner s = new Scanner(getClass().getClassLoader().getResourceAsStream("largest-cuboids.txt"));
        while (s.hasNextLine()) {
            Cuboid cuboid = Cuboid.fromString(s.nextLine());
            intersection.add(cuboid);
            fullIntersection.add(cuboid);
        }

        assertEquals(474140, intersection.calculateCubes());
        assertEquals(2758514936282235L, fullIntersection.calculateCubes());
    }
}

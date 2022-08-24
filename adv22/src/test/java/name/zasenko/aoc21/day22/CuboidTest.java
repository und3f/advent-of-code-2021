package name.zasenko.aoc21.day22;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuboidTest {
    @Test
    void fromStringTest() {
        Cuboid cuboid = Cuboid.fromString("off x=10..12,y=11..13,z=15..19");

        assertFalse(cuboid.getStatus());
        assertArrayEquals(new int[]{10, 12}, cuboid.getCoord(0));
        assertArrayEquals(new int[]{11, 13}, cuboid.getCoord(1));
        assertArrayEquals(new int[]{15, 19}, cuboid.getCoord(2));
    }

    @Test
    void fromStringNegativeValueTest() {
        Cuboid cuboid = Cuboid.fromString("off x=-10..12,y=-11..0,z=-15..-1");
        assertArrayEquals(new int[]{-10, 12}, cuboid.getCoord(0));
        assertArrayEquals(new int[]{-11, 0}, cuboid.getCoord(1));
        assertArrayEquals(new int[]{-15, -1}, cuboid.getCoord(2));
    }
}
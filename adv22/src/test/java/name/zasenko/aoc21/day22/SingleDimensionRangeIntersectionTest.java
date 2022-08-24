package name.zasenko.aoc21.day22;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleDimensionRangeIntersectionTest {
    RangeIntersection<SingleDimensionRange> ri;

    @BeforeEach
    void init() {
        ri = new RangeIntersection(RangeIntersection.singleDimensionRangeCalculator);
    }

    @Test
    void emptyTest() {
        assertEquals(0, ri.countPoints());
    }

    @Test
    void oneRangeTest() {
        ri.addRange(new SingleDimensionRange(1, 10, true));

        assertEquals(10, ri.countPoints());
    }

    @Test
    void intersectionRangeTest() {
        ri.addRange(new SingleDimensionRange(1, 10, true));
        ri.addRange(new SingleDimensionRange(8, 11, true));

        assertEquals(11, ri.countPoints());
    }

    @Test
    void intersectionRangeWithDisabledPointsTest() {
        ri.addRange(new SingleDimensionRange(1, 10, true));
        ri.addRange(new SingleDimensionRange(8, 11, true));
        ri.addRange(new SingleDimensionRange(9, 10, false));

        assertEquals(7 + 2, ri.countPoints());
    }


    @Test
    void disableRange() {
        ri.addRange(new SingleDimensionRange(1, 10, true));
        ri.addRange(new SingleDimensionRange(8, 11, false));

        assertEquals(7, ri.countPoints());
    }
    @Test
    void overwriteDisabledRange() {
        ri.addRange(new SingleDimensionRange(1, 10, true));
        ri.addRange(new SingleDimensionRange(7, 10, false));
        ri.addRange(new SingleDimensionRange(6, 10, true));

        assertEquals(10, ri.countPoints());
    }
}

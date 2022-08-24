package name.zasenko.aoc21.day22;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundsRangeValueCalculatorTest {
    public static final int RANGE_START = -50;
    public static final int RANGE_END = 50;
    RangeIntersection<Range> ri;

    @BeforeEach
    void init() {
        ri = new RangeIntersection<>(
                new BoundsRangeValueCalculator<>(
                        RANGE_START,
                        RANGE_END,
                        RangeIntersection.singleDimensionRangeCalculator
                )
        );
    }

    @Test
    void leftBoundTest() {
        ri.addRange(new SingleDimensionRange(RANGE_START - 1, RANGE_START + 3, true));

        assertEquals(4, ri.countPoints());
    }

    @Test
    void rightBoundTest() {
        ri.addRange(new SingleDimensionRange(RANGE_END - 3, RANGE_END + 3, true));

        assertEquals(4, ri.countPoints());
    }

}
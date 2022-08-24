package name.zasenko.aoc21.day22;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleDimensionRangeTest {

    public static final int START = 1;
    public static final int END = 10;

    @Test
    void simpleTest() {
        SingleDimensionRange singleDimensionRange = new SingleDimensionRange(START, END, true);
        assertEquals(START, singleDimensionRange.getStart());
        assertEquals(END, singleDimensionRange.getEnd());
        assertTrue(singleDimensionRange.isEnabled());
        assertEquals(10, singleDimensionRange.length());
    }
}
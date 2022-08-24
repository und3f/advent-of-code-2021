package name.zasenko.aoc21.day22;

import java.util.SortedSet;

public class BoundsRangeValueCalculator<R extends Range> implements RangeIntersection.RangeValueCalculator<R> {
    final int rangeStart, rangeEnd;
    final RangeIntersection.RangeValueCalculator<R> calculator;

    public BoundsRangeValueCalculator(int rangeStart, int rangeEnd, RangeIntersection.RangeValueCalculator<R> calculator) {
        assert rangeStart < rangeEnd;

        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.calculator = calculator;
    }

    @Override
    public long calculateValue(SortedSet<R> currentRanges, int start, int end) {
        if (end < rangeStart)
            return 0;

        if (start > rangeEnd)
            return 0;

        return calculator.calculateValue(currentRanges, Math.max(start, rangeStart), Math.min(end, rangeEnd));
    }
}

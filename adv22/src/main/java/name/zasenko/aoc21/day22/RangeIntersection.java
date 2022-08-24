package name.zasenko.aoc21.day22;

import java.util.*;

public class RangeIntersection<R extends Range> {
    private final HashMap<R, Integer> ranges = new HashMap<>();

    public static final RangeValueCalculator<Range> singleDimensionRangeCalculator = (currentRanges, start, end) -> {
        if (currentRanges.size() > 0)
            if (currentRanges.first().isEnabled())
                return new SingleDimensionRange(start, end, true).length();

        return 0;
    };

    public interface RangeValueCalculator<R extends Range> {
        long calculateValue(SortedSet<R> currentRanges, int start, int end);
    }

    final RangeValueCalculator<R> calculator;

    public RangeIntersection(RangeValueCalculator<R> calculator) {
        this.calculator = calculator;
    }

    public long countPoints() {
        long sum = 0;

        if (ranges.isEmpty())
            return sum;

        PriorityQueue<RangeEvent<R>> pq = new PriorityQueue<>(Comparator.comparingInt(RangeEvent::getPosition));
        ranges.forEach((range, ignored) -> {
            pq.offer(new RangeEvent<>(range.getStart(), RangeEvent.RangeEventType.EVENT_START, range));
            pq.offer(new RangeEvent<>(range.getEnd() + 1, RangeEvent.RangeEventType.EVENT_END, range));
        });

        SortedSet<R> currentRanges = new TreeSet<>(Comparator.comparingInt(ranges::get).reversed());

        assert pq.peek() != null;
        int position = pq.peek().getPosition();
        while (!pq.isEmpty()) {
            RangeEvent<R> event = pq.poll();
            int eventPosition = event.getPosition();

            if (position != eventPosition)
                sum += calculator.calculateValue(currentRanges, position, eventPosition - 1);

            processEvent(event, currentRanges);

            position = event.getPosition();
        }

        return sum;
    }

    private boolean isRangeEnabled(SortedSet<R> currentRanges) {
        if (currentRanges.size() > 0)
            return currentRanges.first().isEnabled();

        return false;
    }

    private void processEvent(RangeEvent<R> event, SortedSet<R> currentRanges) {
        switch (event.getEvent()) {
            case EVENT_START:
                currentRanges.add(event.getRangeObject());
                break;
            case EVENT_END:
                currentRanges.remove(event.getRangeObject());
                break;
        }
    }

    public void addRange(R range) {
        int nextPriority = ranges.size();
        ranges.put(range, nextPriority);
    }

    private static class RangeEvent<R extends Range> {
        private final int position;
        private final RangeEventType event;
        private final R range;
        public RangeEvent(int x, RangeEventType event, R range) {
            this.position = x;
            this.event = event;
            this.range = range;
        }

        public int getPosition() {
            return position;
        }

        public RangeEventType getEvent() {
            return event;
        }

        public R getRangeObject() {
            return range;
        }

        enum RangeEventType {
            EVENT_START,
            EVENT_END
        }
    }
}

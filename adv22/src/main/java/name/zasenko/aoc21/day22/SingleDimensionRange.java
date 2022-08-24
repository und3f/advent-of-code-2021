package name.zasenko.aoc21.day22;

public class SingleDimensionRange implements Range {
    final int start, end;
    final boolean enabled;
    public SingleDimensionRange(int start, int end, boolean enabled) {
        this.start = start;
        this.end = end;
        this.enabled = enabled;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getEnd() {
        return end;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public int length() {
        return end - start + 1;
    }
}

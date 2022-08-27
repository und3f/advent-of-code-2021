package name.zasenko.aoc21.day14;

public class JustPolymerIterator extends PolymerIterator {
    private final CompressedData polymer;

    public JustPolymerIterator(CompressedData polymer) {
        super(polymer.getBase());
        this.polymer = polymer;
        polymer.rewind();
    }

    @Override
    public boolean hasNext() {
        return polymer.hasRemaining();
    }

    @Override
    public Byte next() {
        return polymer.get();
    }
}

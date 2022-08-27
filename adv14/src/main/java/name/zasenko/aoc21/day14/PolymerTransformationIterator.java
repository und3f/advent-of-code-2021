package name.zasenko.aoc21.day14;

public class PolymerTransformationIterator extends PolymerIterator {
    final PolymerIterator polymerIterator;
    final PolymerTemplate template;
    byte lastItem;
    int buffered;

    public PolymerTransformationIterator(PolymerTemplate template, PolymerIterator polymerIterator) {
        super(polymerIterator.getBase());
        this.polymerIterator = polymerIterator;
        this.template = template;

        lastItem = polymerIterator.next();
        buffered = 1;
    }

    @Override
    public boolean hasNext() {
        return buffered > 0 || polymerIterator.hasNext();
    }

    @Override
    public Byte next() {
        if (buffered > 0) {
            buffered--;
            return lastItem;
        }

        byte right = polymerIterator.next();
        byte middle = template.getInsertionValue(lastItem, right, polymerIterator.getBase());

        lastItem = right;
        buffered = 1;
        return middle;
    }
}

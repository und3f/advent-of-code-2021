package name.zasenko.aoc21.day14;

public class PolymerLeapIterationIterator extends PolymerIterator {
    private final PolymerLeapTemplate template;
    PolymerIterator it, middleIt;
    byte right;
    boolean bufferedRight;
    public PolymerLeapIterationIterator(PolymerIterator it, int base, PolymerLeapTemplate template) {
        super(base);
        this.it = it;
        this.template = template;
        right = it.next();
        bufferedRight = true;
    }

    @Override
    public boolean hasNext() {
        return bufferedRight || middleIt.hasNext();
    }

    @Override
    public Byte next() {
        if (middleIt != null && middleIt.hasNext())
            return middleIt.next();

        if (bufferedRight) {
            byte left = right;

            if (it.hasNext()) {
                right = it.next();
                middleIt = template.getLeapTransformation(left, right).iterator();
            } else {
                bufferedRight = false;
            }
            return left;
        }

        return null;
    }
}

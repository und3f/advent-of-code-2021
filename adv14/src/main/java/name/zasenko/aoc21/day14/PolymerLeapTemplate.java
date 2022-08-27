package name.zasenko.aoc21.day14;

public class PolymerLeapTemplate {
    final int base;
    final CompressedData[] transformations;

    public PolymerLeapTemplate(int steps, int base, PolymerTemplate template) {
        this.base = base;
        transformations = generateLeapTransformations(steps, base, template);
    }

    public CompressedData getLeapTransformation(byte left, byte right) {
        int buffer = (left * base) | right;

        return transformations[buffer].duplicate();
    }

    public PolymerIterator iterationIterator(PolymerIterator it) {
        return new PolymerLeapIterationIterator(it, base, this);
    }

    private static CompressedData[] generateLeapTransformations(int steps, int base, PolymerTemplate template) {
        int size = base * base;
        CompressedData[] transformations = new CompressedData[size];

        for (int pair = 0; pair < size; pair++) {
            CompressedData p = new CompressedData(2, base);
            p.put((byte) (pair / base));
            p.put((byte) (pair & (base - 1)));
            p.closeWrite();

            for (int i = 0; i < steps; i ++) {
                p = template.iterate(p);
            }

            transformations[pair] = truncate(p);
        }

        return transformations;
    }

    private static CompressedData truncate(CompressedData p) {
        CompressedData n = new CompressedData(p.size() - 2, p.getBase());
        var it = p.iterator();

        it.next();
        byte b = it.next();
        while (it.hasNext()) {
            n.put(b);
            b = it.next();
        }
        n.closeWrite();

        return n;
    }

}

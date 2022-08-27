package name.zasenko.aoc21.day14;

public class TemplateIterationCounter {
    private final PolymerTemplate template;
    private final int base;
    private final byte first;
    private final int mask;
    private long[] pairs;

    public TemplateIterationCounter(PolymerIterator it, PolymerTemplate template) {
        this.template = template;
        this.base = it.getBase();
        this.mask = base - 1;

        this.first = it.next();
        initPairs(it);
    }

    private void initPairs(PolymerIterator it) {
        pairs = new long[base * base];

        byte left = first;
        while (it.hasNext()) {
            byte right = it.next();
            pairs[left * base | right]++;
            left = right;
        }
    }


    public void iterate() {
        long[] pairsIteration = new long[pairs.length];

        for (int i = 0; i < pairs.length; i++) {
            long count = pairs[i];

            int left = i & ~mask;
            int right = i & mask;
            int middle = template.getInsertionValue(i) & 0xff;

            pairsIteration[left | middle] += count;
            pairsIteration[(middle * base) | right] += count;
        }

        pairs = pairsIteration;
    }

    public long score() {
        long[] statistic = new long[base];

        statistic[first]++;
        for (int i = 0; i < pairs.length; i++) {
            int right = i & mask;

            statistic[right] += pairs[i];
        }

        long min = statistic[0];
        long max = statistic[0];
        for (int i = 1; i < statistic.length; i++) {
            long stat = statistic[i];

            if (stat == 0)
                break;

            if (stat < min) {
                min = stat;
            } else if (stat > max) {
                max = stat;
            }
        }

        return max - min;
    }
}

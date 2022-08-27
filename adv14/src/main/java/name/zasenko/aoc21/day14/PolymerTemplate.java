package name.zasenko.aoc21.day14;

import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolymerTemplate {
    private static final Pattern templatePattern = Pattern.compile("^(\\w+) -> (\\w)$");
    final byte[] transformations;
    final private Alphabet alphabet;

    public PolymerTemplate(Map<String, Character> templates) {
        this.alphabet = new Alphabet(generateCharset(templates));

        this.transformations = new byte[alphabet.getBase() * alphabet.getBase()];
        for (var entry : templates.entrySet()) {
            String key = entry.getKey();
            int transformationKey = alphabet.toIndex(key.charAt(0)) * alphabet.getBase() | alphabet.toIndex(key.charAt(1));

            transformations[transformationKey] = alphabet.toIndex(entry.getValue());
        }
    }

    private static HashSet<Character> generateCharset(Map<String, Character> templates) {
        HashSet<Character> characterSet = new HashSet<>();
        for (var entry : templates.entrySet()) {
            characterSet.add(entry.getValue());
            for (char c : entry.getKey().toCharArray())
                characterSet.add(c);
        }
        return characterSet;
    }

    public static void parseLine(String line, Map<String, Character> map) {
        Matcher m = templatePattern.matcher(line);

        if (!m.matches())
            return;

        map.put(m.group(1), m.group(2).charAt(0));
    }

    public static long polymerScore(PolymerIterator it) {
        long[] statistic = new long[it.getBase()];

        while (it.hasNext()) {
            int b = it.next();
            statistic[b]++;
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

    public CompressedData compress(String string) {
        return alphabet.compress(string);
    }

    public byte getInsertionValue(byte left, byte right, int base) {
        int buffer = (left * base) | right;

        return getInsertionValue(buffer);
    }

    public byte getInsertionValue(int pair) {
        return transformations[pair];
    }

    public CompressedData iterate(CompressedData polymer) {
        polymer.rewind();
        assert (polymer.size() >= 2);

        int prevSize = polymer.size();
        long size = Math.max(3L, 2L * prevSize - 1);
        CompressedData data = new CompressedData((int) size, polymer.getBase());

        byte lastByte = polymer.get();
        data.put(lastByte);
        while (polymer.hasRemaining()) {
            byte nextByte = polymer.get();
            data.put(getInsertionValue(lastByte, nextByte, polymer.getBase()));
            data.put(nextByte);

            lastByte = nextByte;
        }
        data.closeWrite();

        return data;
    }

    public String expand(CompressedData polymer) {
        polymer.rewind();
        return alphabet.expand(polymer);
    }

    public PolymerTransformationIterator iterationIterator(PolymerIterator polymerIterator) {
        return new PolymerTransformationIterator(this, polymerIterator);
    }

    public char toChar(byte index) {
        return alphabet.toChar(index);
    }

    public PolymerLeapTemplate leapTemplate(int steps) {
        return new PolymerLeapTemplate(steps, alphabet.getBase(), this);
    }
}

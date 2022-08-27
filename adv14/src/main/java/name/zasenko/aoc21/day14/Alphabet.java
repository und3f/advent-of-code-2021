package name.zasenko.aoc21.day14;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class Alphabet {
    int base;
    char[] indexToChar;
    HashMap<Character, Byte> charToIndex;

    public Alphabet(String characters) {
        Set<Character> charactersSet = characters.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
        generateAlphabet(charactersSet);
    }

    public Alphabet(Set<Character> characters) {
        generateAlphabet(characters);
    }

    private void generateAlphabet(Set<Character> characters) {
        byte i = 0;

        int alphabetSize = characters.size();
        indexToChar = new char[alphabetSize];
        charToIndex = new HashMap<>(characters.size());
        for (Character c : characters) {
            charToIndex.put(c, i);
            indexToChar[i] = c;

            i++;
        }

        base = 1;

        while (base < alphabetSize)
            base <<= 1;

        if (base > 0xff)
            throw (new RuntimeException("Alphabet is not suitable for byte"));
    }

    int getBase() {
        return base;
    }

    public byte toIndex(char c) {
        return charToIndex.get(c);
    }

    public char toChar(int index) {
        return indexToChar[index];
    }

    public CompressedData compress(String string) {
        CompressedData data = new CompressedData(string.length(), base);

        for (int i = 0; i < string.length(); i++) {
            data.put(toIndex(string.charAt(i)));
        }
        data.closeWrite();

        return data;
    }

    public String expand(CompressedData cd) {
        StringBuilder sb = new StringBuilder();
        while (cd.hasRemaining()) {
            sb.append(toChar(cd.get()));
        }

        return sb.substring(0, cd.size());
    }
}

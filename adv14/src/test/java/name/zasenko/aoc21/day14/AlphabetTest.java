package name.zasenko.aoc21.day14;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlphabetTest {

    public static final String Charset = "ABC";

    @Test
    void baseCalculationTest() {
        Alphabet alphabet = new Alphabet(Charset);

        assertEquals(4, alphabet.getBase());
    }

    @Test
    void encodeCharTest() {
        Alphabet alphabet = new Alphabet(Charset);

        for (char c : Charset.toCharArray()) {
            assertEquals(c, alphabet.toChar(alphabet.toIndex(c)));
        }
    }

    @Test
    void encodeTest() {
        Alphabet alphabet = new Alphabet(Charset);

        assertEquals(Charset, alphabet.expand(alphabet.compress(Charset)));
    }

    @Test
    void smallAlphabet() {
        final String smallAlphabetString = "ABABAABB";
        Alphabet alphabet = new Alphabet("AB");

        assertEquals(smallAlphabetString, alphabet.expand(alphabet.compress(smallAlphabetString)));
    }


    @Test
    void bigAlphabet() {
        final String bigAlphabet = "OOFNFCBHCKBBVNHBNVCP";
        Alphabet alphabet = new Alphabet(bigAlphabet);

        assertEquals(bigAlphabet, alphabet.expand(alphabet.compress(bigAlphabet)));
    }
}
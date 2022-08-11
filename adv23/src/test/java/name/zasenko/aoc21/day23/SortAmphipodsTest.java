package name.zasenko.aoc21.day23;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortAmphipodsTest {
    @Test
    public void sortedTest() throws Exception {
        var energy = initSorter("sorted-input.txt").sort();

        assertEquals(0, energy);
    }

    @Test
    public void moveTwoTest() throws Exception {
        var energy = initSorter("two-unsorted.txt").sort();

        assertEquals(6 * 10 + 4 * 100, energy);
    }

    @Disabled
    @Timeout(5)
    @Test
    public void complexTest() throws Exception {
        var energy = initSorter("sample.txt").sort();

        assertEquals(12521, energy);
    }

    private SortAmphipods initSorter(String filename) throws IOException, URISyntaxException {
        String input = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()), StandardCharsets.UTF_8);
        return new SortAmphipods(new Burrow(input));
    }
}
package name.zasenko.aoc21.day19;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static name.zasenko.aoc21.day19.Day19.part2;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day19Test {
    @Test
    public void part2Test() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("scanner1.txt");

        assertEquals(3621, part2(new Map(ScannerFactory.fromStream(in))));
    }
}
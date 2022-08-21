package name.zasenko.aoc21.day19;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScannerTest {
    @Test
    public void createScannerTest() {
        InputStream in = getClass().getClassLoader().getResourceAsStream("scanner0.txt");
        List<Scanner> scanners = ScannerFactory.fromStream(in);

        assertEquals(5, scanners.size());

        for (Scanner s : scanners)
            assertEquals(6, s.getCoords().size());
    }
}
